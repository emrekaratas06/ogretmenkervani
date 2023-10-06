package meb.gov.tr.ogretmenkervani.webapp.controller;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Rol;
import meb.gov.tr.ogretmenkervani.webapp.service.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/rol")
public class RolController {

    @Autowired
    private RolService rolService;

    // Öğretmeni komisyona ekleme işlemi (POST metodu ile)
    @PostMapping("/ekleKomisyonUyesi")
    public ResponseEntity<Void> ekleKomisyonUyesi(@RequestBody Ogretmen ogretmen) {
        rolService.ekleKomisyonUyesi(ogretmen);
        return ResponseEntity.ok().build(); // Başarılı bir şekilde kaydedildiyse 200 OK döner.
    }

    // Öğretmeni belirli bir role göre yetkilendirme işlemi (PUT metodu ile)
    @PutMapping("/yetkilendirKomisyonUyesi")
    public ResponseEntity<Void> yetkilendirKomisyonUyesi(@RequestParam Long tcKimlikNo, @RequestBody Rol rol) {
        rolService.yetkilendirKomisyonUyesi(tcKimlikNo, rol);
        return ResponseEntity.ok().build(); // Başarılı bir şekilde güncellendiğinde 200 OK döner.
    }

    // MEB'den SSO yetkisi alma işlemi (GET metodu ile varsayımsal olarak ekledim)
    @GetMapping("/ssoYetkiAl")
    public ResponseEntity<Void> ssoYetkiAl() {
        rolService.ssoYetkiAl();
        return ResponseEntity.ok().build(); // Başarılı bir şekilde işlem yapıldığında 200 OK döner.
    }

    @GetMapping("/uye/list")
    public ResponseEntity<List<Ogretmen>> getKomisyonUyeleri() {
        Set<Ogretmen> komisyonUyeleriSet = rolService.fetchKomisyonUyeleri();
        List<Ogretmen> komisyonUyeleriList = new ArrayList<>(komisyonUyeleriSet);
        return ResponseEntity.ok(komisyonUyeleriList);
    }

    @GetMapping("/uye/{tcKimlikNo}/roller")
    public ResponseEntity<Set<Rol>> getOgretmeninRolleri(@PathVariable Long tcKimlikNo) {
        Set<Rol> roller = rolService.getOgretmeninRolleriniGetir(tcKimlikNo);
        return ResponseEntity.ok(roller);
    }

    // Kullanılabilir tüm rolleri listeleme
    @GetMapping("/uye/rol/all")
    public ResponseEntity<List<Rol>> getAllRoller() {
        List<Rol> roller = rolService.getAllRoller();
        return ResponseEntity.ok(roller);
    }
}

