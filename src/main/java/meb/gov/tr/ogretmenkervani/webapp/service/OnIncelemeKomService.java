package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.OnIncelemeKom;
import meb.gov.tr.ogretmenkervani.webapp.repository.OnIncelemeKomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OnIncelemeKomService {

    @Autowired
    private OnIncelemeKomRepository onIncelemeKomRepository;

    // Create
    public OnIncelemeKom saveOnIncelemeKom(OnIncelemeKom komisyon) {
        return onIncelemeKomRepository.save(komisyon);
    }

    // Read
    public List<OnIncelemeKom> getAllKomisyonlar() {
        return onIncelemeKomRepository.findAll();
    }

    public OnIncelemeKom getKomisyonById(Long id) {
        return onIncelemeKomRepository.findById(id).orElse(null);
    }

    public List<OnIncelemeKom> getOnIncelemeKomisyonByUye(Long tcKimlikNo) {
        return onIncelemeKomRepository.findByUye1TcKimlikNoOrUye2TcKimlikNoOrUye3TcKimlikNo(tcKimlikNo, tcKimlikNo, tcKimlikNo);
    }

    // Update
    public OnIncelemeKom updateOnIncelemeKom(OnIncelemeKom komisyon) {
        return onIncelemeKomRepository.save(komisyon);
    }

    // Delete
    public void deleteOnIncelemeKom(Long id) {
        onIncelemeKomRepository.deleteById(id);
    }

    public List<OnIncelemeKom> getActiveCommissions() {
        return onIncelemeKomRepository.findByIsActiveTrue();
    }

}