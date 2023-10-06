package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.Medya;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedyaRepository extends JpaRepository<Medya, Long> {
    // Özel sorgular buraya eklenebilir
}
