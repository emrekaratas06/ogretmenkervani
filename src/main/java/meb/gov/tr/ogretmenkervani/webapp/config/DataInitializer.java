package meb.gov.tr.ogretmenkervani.webapp.config;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Rol;
import meb.gov.tr.ogretmenkervani.webapp.repository.OgretmenRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private OgretmenRepository ogretmenRepository;

    @Autowired
    private RolRepository rolRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if(ogretmenRepository.findByTcKimlikNo(52138373822L) == null) {
            Ogretmen superAdmin = new Ogretmen();
            superAdmin.setTcKimlikNo(52138373822L);
            superAdmin.setPassword(passwordEncoder.encode("password"));
            superAdmin.setAd("Super Admin");
            superAdmin.setSoyad("");

            Rol superAdminRol = new Rol();
            superAdminRol.setAd("SUPERADMIN");  // Buradaki 'superadmin' kelimesi tamamen büyük harfe dönüştürüldü.
            rolRepository.save(superAdminRol);

            superAdmin.addRol(superAdminRol);
            ogretmenRepository.save(superAdmin); // Superadmin ve rolü burada birlikte kaydediliyor
        }
    }

}
