package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.YayinEkKom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YayinEkKomRepository extends JpaRepository<YayinEkKom, Long> {
    // Özel sorgular buraya eklenebilir
}
