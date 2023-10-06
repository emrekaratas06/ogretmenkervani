package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.Icerik;
import meb.gov.tr.ogretmenkervani.webapp.entity.IcerikTipi;
import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Rol;
import meb.gov.tr.ogretmenkervani.webapp.repository.IcerikRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.OgretmenRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.OnIncelemeKomRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.RolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class RolService {

    @Autowired
    private OgretmenRepository ogretmenRepository; // Bu repository'yi varsayımsal olarak ekledim.
    @Autowired
    private RolRepository rolRepository;           // Bu repository'yi varsayımsal olarak ekledim.

    public void ekleKomisyonUyesi(Ogretmen ogretmen) {
        // Öğretmeni komisyona eklemek için gereken işlemler.
        ogretmenRepository.save(ogretmen); // Öğretmeni direkt olarak kaydediyoruz.
    }

    public void yetkilendirKomisyonUyesi(Long tcKimlikNo, Rol rol) {
        Ogretmen ogretmen = ogretmenRepository.findByTcKimlikNo(tcKimlikNo);
        if (ogretmen == null) {
            throw new IllegalArgumentException("Öğretmen bulunamadı.");
        }
        ogretmen.addRol(rol); // Burada kullanıyoruz
        ogretmenRepository.save(ogretmen);
        rolRepository.save(rol);
    }

    public void ssoYetkiAl() {
        // MEB'den SSO yetkisi almak için gereken işlemler.
        // Bu kısım genellikle dış bir servise HTTP isteği göndermek gibi işlemleri içerir.
        // Ayrıntılı bilgi olmadan bu kısmı gerçekçi bir şekilde doldurmak zor.
    }
    public Rol saveRol(Rol rol) {
        return rolRepository.save(rol);
    }

    public Set<Ogretmen> fetchKomisyonUyeleri() {
        Optional<Rol> komisyonRol = rolRepository.findByAd("Komisyon Üyesi");
        return komisyonRol.map(Rol::getOgretmenler).orElse(Collections.emptySet());
    }

    // Öğretmenin sahip olduğu rolleri getirme
    public Set<Rol> getOgretmeninRolleriniGetir(Long tcKimlikNo) {
        Ogretmen ogretmen = ogretmenRepository.findByTcKimlikNo(tcKimlikNo);
        if (ogretmen == null) {
            throw new IllegalArgumentException("Öğretmen bulunamadı.");
        }
        return ogretmen.getRoller();
    }

    // Kullanılabilir tüm rolleri listeleme
    public List<Rol> getAllRoller() {
        return rolRepository.findAll();
    }
}
