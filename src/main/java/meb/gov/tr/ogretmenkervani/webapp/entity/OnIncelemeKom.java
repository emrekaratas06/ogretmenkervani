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
@Table(name = "OGRT_ON_INC_KOM")
public class OnIncelemeKom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ONINC_EK_ID", unique = true, nullable = false)
    private Long id;

    private String name; // Komisyonun adı

    private boolean isActive; // Komisyonun aktiflik durumu

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
}

