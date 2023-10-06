package meb.gov.tr.ogretmenkervani.webapp.repository;

import meb.gov.tr.ogretmenkervani.webapp.entity.OnIncelemeKom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OnIncelemeKomRepository extends JpaRepository<OnIncelemeKom, Long> {
    List<OnIncelemeKom> findByUye1TcKimlikNoOrUye2TcKimlikNoOrUye3TcKimlikNo(Long uye1Tc, Long uye2Tc, Long uye3Tc);

    List<OnIncelemeKom> findByIsActiveTrue();
}
