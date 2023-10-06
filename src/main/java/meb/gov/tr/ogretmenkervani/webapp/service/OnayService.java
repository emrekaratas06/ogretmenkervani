package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.Icerik;
import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.OnIncelemeKom;
import meb.gov.tr.ogretmenkervani.webapp.entity.Onay;
import meb.gov.tr.ogretmenkervani.webapp.repository.OnayRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class OnayService {
    @Autowired
    private OnayRepository onayRepository;
    @Autowired
    private EmailService emailService;
    @Autowired
    private IcerikService icerikService;

    public void onayla(Long icerikId, Ogretmen komisyonUye) {
        Optional<Onay> onayOpt = onayRepository.findByIcerikIdAndKomisyonUye(icerikId, komisyonUye);
        if(onayOpt.isPresent()) {
            Onay onay = onayOpt.get();
            onay.setOnayDurumu(true);
            onayRepository.save(onay);
            onayBildirimiGonder(icerikId);
        } else {
            throw new IllegalArgumentException("Belirtilen içerik veya komisyon üyesi için onay bulunamadı.");
        }
    }

    public void reddet(Long icerikId, Ogretmen komisyonUye) {
        Optional<Onay> onayOpt = onayRepository.findByIcerikIdAndKomisyonUye(icerikId, komisyonUye);
        if(onayOpt.isPresent()) {
            Onay onay = onayOpt.get();
            onay.setOnayDurumu(false);
            onayRepository.save(onay);
            reddetmeBildirimiGonder(icerikId);
        } else {
            throw new IllegalArgumentException("Belirtilen içerik veya komisyon üyesi için onay bulunamadı.");
        }
    }

    private void reddetmeBildirimiGonder(Long icerikId) {
        Icerik icerik = icerikService.getIcerikById(icerikId);
        if(icerik != null) {
            String to = icerik.getOgretmen().getEmail();
            String subject = "İçeriğiniz Reddedildi!";
            String body = "Maalesef, " + icerik.getOgretmen().getAd() + ", yüklediğiniz içerik reddedildi.";
            emailService.sendEmail(to, subject, body);
        }
    }
    // Create
    public Onay saveOnay(Onay onay) {
        return onayRepository.save(onay);
    }

    // Read
    public List<Onay> getAllOnaylar() {
        return onayRepository.findAll();
    }

    public Onay getOnayById(Long id) {
        return onayRepository.findById(id).orElse(null);
    }

    public List<Onay> getOnaylananIcerikler(Long icerikId) {
        return onayRepository.findByIcerikIdAndOnayDurumuTrue(icerikId);
    }

    // Update
    public Onay updateOnay(Onay onay) {
        return onayRepository.save(onay);
    }

    // Delete
    public void deleteOnay(Long id) {
        onayRepository.deleteById(id);
    }

    public void onayBildirimiGonder(Long icerikId) {
        if(kontrolEtUcOnayVarMi(icerikId)) {
            Icerik icerik = icerikService.getIcerikById(icerikId);
            if(icerik != null) {
                String to = icerik.getOgretmen().getEmail();
                String subject = "İçeriğiniz Onaylandı!";
                String body = "Merhaba, " + icerik.getOgretmen().getAd() + ". Yüklediğiniz içerik başarıyla onaylandı.";
                emailService.sendEmail(to, subject, body);
            }
        }
    }

    private boolean kontrolEtUcOnayVarMi(Long icerikId) {
        List<Onay> onaylar = onayRepository.findByIcerikIdAndOnayDurumuTrue(icerikId);
        return onaylar.size() >= 3;
    }

    public void esitDagitIcerikOnIncelemeKomisyonUyelerine(List<Icerik> icerikler, OnIncelemeKom komisyon) {
        List<Ogretmen> ogretmenler = Arrays.asList(komisyon.getUye1(), komisyon.getUye2(), komisyon.getUye3());
        int ogretmenSayisi = ogretmenler.size();
        int icerikPerOgretmen = icerikler.size() / ogretmenSayisi;

        int index = 0;
        for (Ogretmen ogretmen : ogretmenler) {
            List<Icerik> ogretmeneAtananIcerikler = icerikler.subList(index, index + icerikPerOgretmen);
            ogretmeneAtananIcerikler.forEach(icerik -> {
                Onay yeniOnay = new Onay();
                yeniOnay.setIcerik(icerik);
                yeniOnay.setKomisyonUye(ogretmen);
                yeniOnay.setOnayDurumu(false);
                onayRepository.save(yeniOnay);
            });
            index += icerikPerOgretmen;
        }
    }
}
