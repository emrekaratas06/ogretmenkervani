package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.BakanlikTemsilcisi;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BakanlikTemsilcisiRepository extends JpaRepository<BakanlikTemsilcisi, Long> {
    // Özel sorgular buraya eklenebilir
}
