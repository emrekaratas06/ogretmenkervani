package meb.gov.tr.ogretmenkervani.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OGRT_YORUM")
public class Yorum {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_YORUM_ID", unique = true, nullable = false)
    private Long id;

    @Lob
    @Column(name = "METIN", nullable = false)
    private String metin;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ICERIK_ID", referencedColumnName = "PK_ICERIK_ID")
    private Icerik icerik;

    // farklı Hibernate oturumlarında veya aynı oturum içinde yüklenen nesnelerin doğru bir şekilde eşleştirilmesi için önemlidir
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Yorum yorum = (Yorum) o;
        return id != null && id.equals(yorum.id);
    }

    @Override
    public int hashCode() {
        return 31;
    }
}

