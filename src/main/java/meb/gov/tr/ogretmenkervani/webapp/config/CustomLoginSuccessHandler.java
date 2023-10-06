package meb.gov.tr.ogretmenkervani.webapp.config;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Rol;
import meb.gov.tr.ogretmenkervani.webapp.repository.OgretmenRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CustomLoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private OgretmenRepository ogretmenRepo;

    @Autowired
    private RolRepository rolRepository;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        System.out.println("onAuthenticationSuccess çalıştı.");

        Ogretmen ogretmen = ogretmenRepo.findById(Long.parseLong(authentication.getName())).orElse(null);
        if (ogretmen == null) {
            System.out.println("Ogretmen nesnesi null.");
            response.sendRedirect("/");
            return;
        }

        Rol ogretmenRol = rolRepository.findByAd("OGRETMEN")
                .orElseGet(() -> {
                    Rol yeniRol = new Rol();
                    yeniRol.setAd("OGRETMEN");
                    rolRepository.save(yeniRol);
                    return yeniRol;
                });

        if (!ogretmen.getRoller().contains(ogretmenRol)) {
            System.out.println("Ogretmen rolü ekleniyor.");
            ogretmen.getRoller().add(ogretmenRol);
            ogretmenRepo.save(ogretmen);
        } else {
            System.out.println("Ogretmen rolü zaten var.");
        }

        response.sendRedirect("/");
    }

}
