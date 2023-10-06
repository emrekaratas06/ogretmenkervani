package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.Icerik;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface IcerikRepository extends JpaRepository<Icerik, Long> {
    List<Icerik> findByOgretmenTcKimlikNo(Long tcKimlikNo);

    List<Icerik> findByOnayDurumu(Boolean onayDurumu);

    // Eğer Icerik sınıfında rejected adında bir alanınız yoksa bu satırı da düzeltmelisiniz.
    List<Icerik> findByYayinDurumu(Boolean yayinDurumu);  // Eğer yayın durumu reddedilmeyi temsil ediyorsa bu şekilde. Değilse uygun bir isimlendirme yapmanız gerekecek.

}