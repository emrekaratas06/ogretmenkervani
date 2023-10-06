package meb.gov.tr.ogretmenkervani.webapp.controller;

import meb.gov.tr.ogretmenkervani.webapp.entity.*;
import meb.gov.tr.ogretmenkervani.webapp.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/api")
public class IcerikController {

    @Autowired
    private IcerikService icerikService;

    @Autowired
    private OnayService onayService;

    @Autowired
    private OnIncelemeKomService onIncelemeKomService;

    @Autowired
    private OgretmenService ogretmenService;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    // 1. Öğretmen bir içerik yükler.
    // Create
    @PostMapping("/icerik")
    public ResponseEntity<Icerik> createIcerik(@RequestBody Icerik icerik) {
        Icerik savedIcerik = icerikService.saveIcerik(icerik);
        return new ResponseEntity<>(savedIcerik, HttpStatus.CREATED);
    }

    // 2. İçerik rastgele bir "ön inceleme havuzu"na atanır.
    // Bu işlemi içeriği yüklediğimizde otomatik olarak yapabiliriz
    // Ya da ayrı bir endpoint ile de yapabiliriz. Örneğin:
    @PostMapping("/icerik/{icerikId}/rastgele-ata")
    public ResponseEntity<Icerik> rastgeleKomisyonaAta(@PathVariable Long icerikId) {
        Icerik icerik = icerikService.getIcerikById(icerikId);
        if (icerik == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Icerik atananIcerik = icerikService.rastgeleKomisyonaAta(icerik);
        return new ResponseEntity<>(atananIcerik, HttpStatus.OK);
    }

    // 3. İçerik ön inceleme komisyon üyelerine eşit olarak dağıtılır.
    // Bu işlemi içerik rastgele komisyona atandığında otomatik olarak yapabiliriz
    // Ya da ayrı bir endpoint ile de yapabiliriz. Örneğin:
    @PostMapping("/icerik/{icerikId}/uyelere-dagit")
    public ResponseEntity<Void> icerikUyelereDagit(@PathVariable Long icerikId) {
        Icerik icerik = icerikService.getIcerikById(icerikId);
        if (icerik == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        OnIncelemeKom komisyon = icerik.getOnIncelemeKom();
        if (komisyon == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        onayService.esitDagitIcerikOnIncelemeKomisyonUyelerine(Arrays.asList(icerik), komisyon);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // 4. Üyeler içeriği onaylar.
    @PutMapping("/onay")
    public ResponseEntity<Onay> icerikOnayla(@RequestBody Onay onay) {
        Onay savedOnay = onayService.saveOnay(onay);
        return new ResponseEntity<>(savedOnay, HttpStatus.OK);
    }

    // 5 & 6. Eğer 3 üye de onayladıysa, yayın ekibi kararı alır ve içerik yayınlanır.
    @PostMapping("/icerik/{icerikId}/yayinla")
    public ResponseEntity<Icerik> icerikYayinla(@PathVariable Long icerikId) {
        Icerik icerik = icerikService.getIcerikById(icerikId);
        if (icerik == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Icerik yayinlananIcerik = icerikService.yayinla(icerik);
        return new ResponseEntity<>(yayinlananIcerik, HttpStatus.OK);
    }

    // Read All
    @GetMapping("/icerik")
    public ResponseEntity<List<Icerik>> getAllIcerikler() {
        List<Icerik> icerikler = icerikService.getAllIcerikler();
        return new ResponseEntity<>(icerikler, HttpStatus.OK);
    }

    // Read by ID
    @GetMapping("/icerik/{id}")
    public ResponseEntity<Icerik> getIcerikById(@PathVariable Long id) {
        Icerik icerik = icerikService.getIcerikById(id);
        if (icerik != null) {
            return new ResponseEntity<>(icerik, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //update
    @PutMapping("/icerik/{id}")
    public ResponseEntity<Icerik> updateIcerik(@PathVariable Long id, @RequestParam("foto") MultipartFile updateFoto,
                                               @RequestParam("hikaye") String updateHikaye,
                                               @RequestParam("adSoyad") String updateAdSoyad) {
        Icerik existingIcerik = icerikService.getIcerikById(id);
        Ogretmen aktifOgretmen = customUserDetailsService.getAktifOgretmen();

        if (existingIcerik != null) {
            existingIcerik.setOgretmen(aktifOgretmen);
            existingIcerik.setMetin(updateHikaye);
            existingIcerik.setFotografYolu(updateFoto.getOriginalFilename());  // Burada dosyanın kaydedilmesi gerekiyor

            Icerik savedIcerik = icerikService.saveIcerik(existingIcerik);
            return new ResponseEntity<>(savedIcerik, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    // Delete
    @DeleteMapping("/icerik/{id}")
    public ResponseEntity<Void> deleteIcerik(@PathVariable Long id) {
        Icerik existingIcerik = icerikService.getIcerikById(id);
        if (existingIcerik != null) {
            icerikService.deleteIcerik(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Öğretmene göre içerikleri getir
    @GetMapping("/icerik/ogretmen/{tcKimlikNo}")
    public ResponseEntity<List<Icerik>> getIcerikByOgretmen(@PathVariable Long tcKimlikNo, Model model) {
        List<Icerik> icerikler = icerikService.getIcerikByOgretmen(tcKimlikNo);
        model.addAttribute("icerikler", icerikService.getIcerikByOgretmen(tcKimlikNo));
        return new ResponseEntity<>(icerikler, HttpStatus.OK);
    }

    public ResponseEntity<List<Icerik>> getUnapprovedContents() {
        List<Icerik> unapprovedContents = icerikService.fetchUnapprovedContents();
        return ResponseEntity.ok(unapprovedContents);
    }

    @GetMapping("/icerik/rejected")
    public ResponseEntity<List<Icerik>> getRejectedContents() {
        List<Icerik> rejectedContents = icerikService.fetchRejectedContents();
        return ResponseEntity.ok(rejectedContents);
    }

    @GetMapping("/fotografSanati")
    public String getFotografSanatiForm(Model model) {
        // Eğer model içinde başka veriler eklenmek isteniyorsa buraya eklenebilir.
        return "icerik/fotografSanati";
    }

    @PostMapping("/fotografSanati/yukle")
    public String postFotografSanatiForm(@RequestParam("foto") MultipartFile foto,
                                         @RequestParam("hikaye") String hikaye,
                                         @RequestParam("adSoyad") String adSoyad,
                                         @RequestParam("muvaffakatname") Boolean muvaffakatDurumu,
                                         RedirectAttributes redirectAttributes) throws IOException {
        Icerik icerik = new Icerik();
        // Örnek olarak bazı değerleri doldurduk. Diğer alanları doldurmanız gerekebilir.
        icerik.setBaslik("Fotoğraf Sanatı");
        icerik.setMetin(hikaye);
        icerik.setFotografYolu(foto.getOriginalFilename());  // Bu sadece örnek. Dosya kaydedilmeli.
        icerik.setMuvaffakatDurumu(muvaffakatDurumu);

        Ogretmen aktifOgretmen = customUserDetailsService.getAktifOgretmen();
        icerik.setOgretmen(aktifOgretmen);
        icerik.setIcerikTipiId(IcerikTipi.FOTOGRAF_SANATI);
        icerikService.saveIcerik(icerik);

        redirectAttributes.addFlashAttribute("successMessage", "İçerik başarıyla kaydedildi!");

        return "redirect:/profil";  // Başarılı olursa profil sayfasına yönlendir.
    }

}