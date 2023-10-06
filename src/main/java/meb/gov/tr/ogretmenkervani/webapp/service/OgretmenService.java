package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Rol;
import meb.gov.tr.ogretmenkervani.webapp.repository.OgretmenRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class OgretmenService {
    @Autowired
    private OgretmenRepository ogretmenRepo;
    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void rolEkle(Long tcKimlikNo, String rolAdi) {
        // Kullanıcının rol ekleyebilmesi için izin verilen roller
        List<String> izinVerilenRoller = Arrays.asList("BAKANLIKTEMSILCISI", "ONINCKOMUYE", "YAYINEKKOMUYE", "ILMEMTEMS");
        if (!izinVerilenRoller.contains(rolAdi)) {
            throw new IllegalArgumentException("Bu rolü eklemek için yetkiniz yok.");
        }
        Ogretmen ogretmen = ogretmenRepo.findById(tcKimlikNo)
                .orElseGet(() -> {
                    Ogretmen yeniOgretmen = new Ogretmen();
                    yeniOgretmen.setTcKimlikNo(tcKimlikNo);
                    yeniOgretmen.setAd("Şadiye");
                    yeniOgretmen.setSoyad("Başsan");
                    yeniOgretmen.setPassword(passwordEncoder.encode("password"));
                    // diğer gerekli öğretmen bilgileri burada set edilebilir
                    ogretmenRepo.save(yeniOgretmen);
                    return yeniOgretmen;
                });

        Rol rol = rolRepository.findByAd(rolAdi)
                .orElseGet(() -> {
                    Rol yeniRol = new Rol();
                    yeniRol.setAd(rolAdi);
                    rolRepository.save(yeniRol);
                    return yeniRol;
                });

        ogretmen.getRoller().add(rol);
        addRoleToOgretmen(ogretmen);
        ogretmenRepo.save(ogretmen);
    }


    public void rolKaldır(Long tcKimlikNo, String rolAdi) {
        Ogretmen ogretmen = ogretmenRepo.findById(tcKimlikNo).orElseThrow(() -> new RuntimeException("Öğretmen bulunamadı"));
        Rol rol = rolRepository.findByAd(rolAdi).orElseThrow(() -> new RuntimeException("Rol bulunamadı"));
        ogretmen.getRoller().remove(rol);
        ogretmenRepo.save(ogretmen);
    }

    public void addRoleToOgretmen(Ogretmen ogretmen) {
        Optional<Rol> optionalOgretmenRol = rolRepository.findByAd("OGRETMEN");
        Rol ogretmenRol = null;

        if (optionalOgretmenRol.isPresent()) {
            ogretmenRol = optionalOgretmenRol.get();
        } else {
            ogretmenRol = new Rol("OGRETMEN");
            rolRepository.save(ogretmenRol);
        }

        if (!ogretmen.getRoller().contains(ogretmenRol)) {
            ogretmen.getRoller().add(ogretmenRol);
            ogretmenRepo.save(ogretmen);
        }
    }

    public List<Ogretmen> findAllByRoles(String... rolAdlari) {
        return ogretmenRepo.findAllByRolAdlari(Arrays.asList(rolAdlari));
    }
    public void sil(Long tcKimlikNo) {
        ogretmenRepo.deleteById(tcKimlikNo);
    }

    public Ogretmen getOgretmenByTcKimlikNo(Long tcKimlikNo) {
        Ogretmen ogretmen = ogretmenRepo.findByTcKimlikNo(tcKimlikNo);
        if (ogretmen == null) {
            throw new RuntimeException("TC Kimlik Numarası ile öğretmen bulunamadı: " + tcKimlikNo);
        }
        return ogretmen;
    }
    public Ogretmen saveOgretmen(Ogretmen ogretmen) {
        return ogretmenRepo.save(ogretmen);
    }
}

