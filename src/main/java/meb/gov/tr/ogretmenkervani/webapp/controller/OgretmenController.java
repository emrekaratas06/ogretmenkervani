package meb.gov.tr.ogretmenkervani.webapp.controller;

import meb.gov.tr.ogretmenkervani.webapp.entity.Icerik;
import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.service.IcerikService;
import meb.gov.tr.ogretmenkervani.webapp.service.OgretmenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class OgretmenController {
    @Autowired
    private OgretmenService ogretmenService;

    @Autowired
    private IcerikService icerikService;

    @GetMapping("/profil")
    public String profilSayfasi(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        Ogretmen aktifOgretmen = ogretmenService.getOgretmenByTcKimlikNo(Long.parseLong(username));

        List<Icerik> kullaniciIcerikleri = icerikService.getIceriklerByOgretmen(aktifOgretmen);
        model.addAttribute("kullaniciIcerikleri", kullaniciIcerikleri);

        // Burada eksik olan kısım; 'ogretmen' model özniteliğini ekliyoruz.
        model.addAttribute("ogretmen", aktifOgretmen);

        return "ogretmen/profil";
    }

}
