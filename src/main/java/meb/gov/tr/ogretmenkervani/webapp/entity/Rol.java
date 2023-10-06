package meb.gov.tr.ogretmenkervani.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OGRT_ROL")
public class Rol {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ROL_ID", unique = true, nullable = false)
    private Long id;
    private String ad;
    private String aciklama; // Rolün açıklaması (Opsiyonel)

    @ManyToMany(mappedBy = "roller")
    private Set<Ogretmen> ogretmenler = new HashSet<>();

    public Rol(String ad) {
        this.ad = ad;
    }
    @Override
    public String toString() {
        return ad;  // Rolün adını döndürüyoruz.
    }
    // farklı Hibernate oturumlarında veya aynı oturum içinde yüklenen nesnelerin doğru bir şekilde eşleştirilmesi için önemlidir
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rol rol = (Rol) o;
        return id != null && id.equals(rol.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

