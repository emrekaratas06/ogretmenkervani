package meb.gov.tr.ogretmenkervani.webapp.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor  // Boş bir constructor ekliyoruz
@Table(name = "OGRT_OGRETMEN")
public class Ogretmen implements UserDetails {
    @Id
    @Column(name = "PK_TC_KIMLIK_NO", unique = true, nullable = false)
    private Long tcKimlikNo;
    @Column(name = "AD")
    private String ad;
    @Column(name = "SOYAD")
    private String soyad;
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Column(name = "TEL_NO")
    private String telefonNumarasi;
    @Column(name = "ADRES")
    private String adres;
    @Column(name = "PASSWORD")
    private String password;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "FK_OGRETMEN_ROL",
            joinColumns = @JoinColumn(name = "PK_TC_KIMLIK_NO"),
            inverseJoinColumns = @JoinColumn(name = "PK_ROL_ID")
    )
    private Set<Rol> roller = new HashSet<>();

    public void addRol(Rol rol) {
        this.roller.add(rol);
        rol.getOgretmenler().add(this);
    }

    public Set<Rol> getRoller() {
        return roller;
    }

    @Override
    @Transactional
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = roller.stream()
                .map(rol -> new SimpleGrantedAuthority("ROLE_" + rol.getAd().toUpperCase()))
                .collect(Collectors.toList());

        System.out.println("Yetkiler: " + authorities); // bu satırı ekleyin
        return authorities;
    }

    @Override
    public String getUsername() {
        return tcKimlikNo.toString();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String getPassword() {
        return password;
    }

}
