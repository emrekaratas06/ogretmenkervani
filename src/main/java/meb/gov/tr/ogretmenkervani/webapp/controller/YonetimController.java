package meb.gov.tr.ogretmenkervani.webapp.controller;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.service.OgretmenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/yonetim")
public class YonetimController {

    @Autowired
    private OgretmenService ogretmenService;

    //...
    @GetMapping
    @PreAuthorize("hasRole('ROLE_SUPERADMIN')")
    public String yonetimPaneliForSuperAdmin(Model model) {
        List<Ogretmen> ogretmenler = ogretmenService.findAllByRoles("BAKANLIKTEMSILCISI", "ONINCKOMUYE","YAYINEKKOMUYE", "ILMEMTEMS");
        model.addAttribute("ogretmenler", ogretmenler);

        // Burada isEditing dizisini başlatıyoruz
        List<Boolean> isEditing = new ArrayList<>();
        for (int i = 0; i < ogretmenler.size(); i++) {
            isEditing.add(false);
        }
        model.addAttribute("isEditing", isEditing);  // Model'e ekleyerek view'e gönderiyoruz

        return "yonetimPaneli";
    }


    @GetMapping("/bakanlik")
    @PreAuthorize("hasRole('ROLE_BAKANLIKTEMSILCISI')")
    public String yonetimPaneliForBakanlik(Model model) {
        List<Ogretmen> ogretmenler = ogretmenService.findAllByRoles("ONINCKOMUYE","YAYINEKKOMUYE", "ILMEMTEMS");
        model.addAttribute("ogretmenler", ogretmenler);
        return "yonetimPaneli";
    }


    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_BAKANLIKTEMSILCISI')")
    @PostMapping("/rolEkle")
    public String rolEkle(@RequestParam Long tcKimlikNo, @RequestParam String rolAdi) {
        ogretmenService.rolEkle(tcKimlikNo, rolAdi);
        return "redirect:/yonetim";
    }

    @PostMapping("/rolKaldır")
    public String rolKaldır(@RequestParam Long tcKimlikNo, @RequestParam String rolAdi) {
        ogretmenService.rolKaldır(tcKimlikNo, rolAdi);
        return "redirect:/yonetim";
    }

    @PreAuthorize("hasAnyRole('ROLE_SUPERADMIN', 'ROLE_BAKANLIKTEMSILCISI')")
    @GetMapping("/sil/{tcKimlikNo}")
    public String silOgretmen(@PathVariable Long tcKimlikNo) {
        ogretmenService.sil(tcKimlikNo);  // Öğretmeni sil
        return "redirect:/yonetim";  // Yönetim paneline yönlendir
    }

}


