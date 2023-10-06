package meb.gov.tr.ogretmenkervani.webapp.service;

import meb.gov.tr.ogretmenkervani.webapp.entity.Ogretmen;
import meb.gov.tr.ogretmenkervani.webapp.repository.OgretmenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private OgretmenService ogretmenService;

    @Override
    public UserDetails loadUserByUsername(String tcKimlikNo) throws UsernameNotFoundException {
        Ogretmen ogretmen = ogretmenService.getOgretmenByTcKimlikNo(Long.valueOf(tcKimlikNo));
        if (ogretmen == null) {
            throw new UsernameNotFoundException("Kullanıcı bulunamadı.");
        }
        return ogretmen;
    }

    public Ogretmen getAktifOgretmen() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String username = auth.getName();
        return ogretmenService.getOgretmenByTcKimlikNo(Long.parseLong(username));
    }
}


