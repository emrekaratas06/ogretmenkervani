package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OgretmenRepository extends JpaRepository<Ogretmen, Long> {
    // Özel sorgular buraya eklenebilir
    Ogretmen findByTcKimlikNo(Long tcKimlikNo);

    @Query("SELECT o FROM Ogretmen o JOIN o.roller r WHERE r.ad IN :rolAdlari")
    List<Ogretmen> findAllByRolAdlari(@Param("rolAdlari") List<String> rolAdlari);


}
