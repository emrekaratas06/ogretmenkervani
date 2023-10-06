package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.entity.Onay;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OnayRepository extends JpaRepository<Onay, Long> {
    List<Onay> findByIcerikIdAndOnayDurumuTrue(Long icerikId);
    Optional<Onay> findByIcerikIdAndKomisyonUye(Long icerikId, Ogretmen komisyonUye);

}
