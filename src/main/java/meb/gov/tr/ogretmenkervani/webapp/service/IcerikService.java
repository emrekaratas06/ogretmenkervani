package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.Icerik;
import meb.gov.tr.ogretmenkervani.webapp.entity.IcerikTipi;
import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.OnIncelemeKom;
import meb.gov.tr.ogretmenkervani.webapp.repository.IcerikRepository;
import meb.gov.tr.ogretmenkervani.webapp.repository.OnIncelemeKomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;

@Service
public class IcerikService {

    @Autowired
    private IcerikRepository icerikRepository;

    @Autowired
    private OnIncelemeKomRepository onIncelemeKomRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private OgretmenService ogretmenService;
    // Create
    public Icerik saveIcerik(Icerik icerik) {
        Ogretmen ogretmen = icerik.getOgretmen();

        if (ogretmen.getTcKimlikNo() != null) {
            Ogretmen existingOgretmen = ogretmenService.getOgretmenByTcKimlikNo(ogretmen.getTcKimlikNo());
            System.out.println("Ogretmen TC.1 : "+ existingOgretmen.getTcKimlikNo());
            if (existingOgretmen != null) {
                System.out.println("Ogretmen TC.2 : "+ ogretmen.getTcKimlikNo());
                ogretmen = ogretmenService.getOgretmenByTcKimlikNo(ogretmen.getTcKimlikNo());
            }else {
                System.out.println("Ogretmen TC.3 : "+ ogretmen.getTcKimlikNo());
                ogretmen = existingOgretmen;  // Eğer Ogretmen zaten varsa, mevcut değerini kullan.
            }
        }
        System.out.println("Ogretmen TC.4 : "+ ogretmen.getTcKimlikNo());
        //icerik.setOgretmen(ogretmen);  // Burada, icerik nesnesine gerçekten kaydedilmiş veya var olan ogretmen nesnesini ayarlıyoruz.
        return icerikRepository.save(icerik);
    }
    // Read
    public List<Icerik> getAllIcerikler() {
        return icerikRepository.findAll();
    }

    public Icerik getIcerikById(Long id) {
        return icerikRepository.findById(id).orElse(null);
    }

    public List<Icerik> getIcerikByOgretmen(Long tcKimlikNo) {
        return icerikRepository.findByOgretmenTcKimlikNo(tcKimlikNo);
    }

    // Update - genelde save metodu ile aynıdır
    public Icerik updateIcerik(Icerik icerik) {
        return icerikRepository.save(icerik);
    }

    // Delete
    public void deleteIcerik(Long id) {
        Icerik icerik = getIcerikById(id);
        if(icerik != null) {
            icerikRepository.deleteById(id);
            String to = icerik.getOgretmen().getEmail();
            String subject = "M.E.B. Ogretmeniz Projesi kapsamında içerik bilgilendirmesi.";
            String body = "Merhaba Sn, " + icerik.getOgretmen().getAd() +" "+ icerik.getOgretmen().getSoyad()+" " +icerik.getBaslik()+" başlıklı içeriğiniz M.E.B. ilgili komisyonlarınca reddedildiğinden sistemden silindi. Teşekkür ederiz.";
            emailService.sendEmail(to, subject, body);
        }
    }

    public Icerik rastgeleKomisyonaAta(Icerik icerik) {
        OnIncelemeKom secilenKomisyon = rastgeleKomisyonSec();
        icerik.setOnIncelemeKom(secilenKomisyon);
        return icerikRepository.save(icerik);
    }

    private OnIncelemeKom rastgeleKomisyonSec() {
        List<OnIncelemeKom> tumKomisyonlar = onIncelemeKomRepository.findAll();
        Random rnd = new Random();
        return tumKomisyonlar.get(rnd.nextInt(tumKomisyonlar.size()));
    }

    public Icerik yayinla(Icerik icerik) {
        icerik.setYayinDurumu(true);
        Icerik kaydedilenIcerik = icerikRepository.save(icerik);

        // İçerik başarıyla kaydedildiğinde e-posta bildirimi gönderiyoruz.
        String to = icerik.getOgretmen().getEmail();
        String subject = "İçeriğiniz Yayınlandı!";
        String body = "Merhaba, " + icerik.getOgretmen().getAd() + ". Yüklediğiniz içerik başarıyla yayınlandı.";
        emailService.sendEmail(to, subject, body);

        return kaydedilenIcerik;
    }

    public void esitDagit() {
        List<Icerik> tumIcerikler = icerikRepository.findAll();
        List<OnIncelemeKom> tumKomisyonlar = onIncelemeKomRepository.findAll();

        int komisyonSayisi = tumKomisyonlar.size();
        int icerikSayisi = tumIcerikler.size();

        if(komisyonSayisi == 0) {
            throw new IllegalStateException("Komisyon bulunamadı!");
        }

        int icerikPerKomisyon = icerikSayisi / komisyonSayisi;

        int index = 0;
        for (OnIncelemeKom komisyon : tumKomisyonlar) {
            List<Icerik> komisyonaAtananIcerikler = tumIcerikler.subList(index, index + icerikPerKomisyon);
            komisyonaAtananIcerikler.forEach(icerik -> icerik.setOnIncelemeKom(komisyon));
            index += icerikPerKomisyon;
        }
        icerikRepository.saveAll(tumIcerikler);
    }
    // ilMemYetkilisi İşlemleri
    public void youtubeVideoEkle(Icerik videoIcerik) {
        videoIcerik.setIcerikTipiId(IcerikTipi.YOUTUBE_VIDEO); // Örneğin: IcerikTipi bir Enum olduğunda ve YOUTUBE_VIDEO bir değer olacak şekilde varsayıldı.
        icerikRepository.save(videoIcerik);
    }


    public List<Icerik> fetchUnapprovedContents() {
        return icerikRepository.findByOnayDurumu(false);
    }

    public List<Icerik> fetchRejectedContents() {
        return icerikRepository.findByYayinDurumu(true);
    }

    public List<Icerik> getIceriklerByOgretmen(Ogretmen ogretmen) {
        return icerikRepository.findByOgretmenTcKimlikNo(ogretmen.getTcKimlikNo());
    }

}
