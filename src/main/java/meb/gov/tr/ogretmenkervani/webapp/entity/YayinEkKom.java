package meb.gov.tr.ogretmenkervani.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OGRT_YAY_EK_KOM")
public class YayinEkKom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_YAYIN_EK_ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_UYE1_ID", referencedColumnName = "PK_TC_KIMLIK_NO")
    private Ogretmen uye1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_UYE2_ID", referencedColumnName = "PK_TC_KIMLIK_NO")
    private Ogretmen uye2;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_UYE3_ID", referencedColumnName = "PK_TC_KIMLIK_NO")
    private Ogretmen uye3;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_BAKANLIK_TEMSILCISI_ID", referencedColumnName = "PK_TC_KIMLIK_NO")
    private BakanlikTemsilcisi bakanlikTemsilcisi;

    @OneToMany(mappedBy = "yayinEkKom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Icerik> icerikler;
    // Diğer eklenmesi gereken özellikler, metodlar vb.
}
