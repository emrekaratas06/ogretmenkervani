package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.Yorum;
import org.springframework.data.jpa.repository.JpaRepository;

public interface YorumRepository extends JpaRepository<Yorum, Long> {
    // Özel sorgular buraya eklenebilir
}
