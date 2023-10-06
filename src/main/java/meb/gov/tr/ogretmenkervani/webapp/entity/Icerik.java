package meb.gov.tr.ogretmenkervani.webapp.entity;

import javax.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OGRT_ICERIK")
public class Icerik {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ICERIK_ID", unique = true, nullable = false)
    private Long id;

    @Column(name = "BASLIK", nullable = false)
    private String baslik;

    @Lob
    @Column(name = "METIN", nullable = false)
    private String metin;

    @Column(name = "FOTO_YOL")
    private String fotografYolu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_OGRT_ID", referencedColumnName = "PK_TC_KIMLIK_NO", nullable = false)
    private Ogretmen ogretmen;

    @Enumerated(EnumType.STRING)
    @Column(name = "ICRK_TIP_ID", nullable = false)
    private IcerikTipi icerikTipiId;

    @Column(name = "MUV_DUR")
    private Boolean muvaffakatDurumu;

    @Column(name = "ONY_DUR")
    private Boolean onayDurumu;
    @Column(name = "YAYIN_DUR")
    private Boolean yayinDurumu;

    @OneToMany(mappedBy = "icerik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Yorum> yorumlar;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ONINC_EK_ID", referencedColumnName = "PK_ONINC_EK_ID", nullable = true)
    private OnIncelemeKom onIncelemeKom;

    @OneToMany(mappedBy = "icerik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Medya> medyalar;

    @OneToMany(mappedBy = "icerik", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Onay> onaylar;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_YAY_EK_ID", referencedColumnName = "PK_YAYIN_EK_ID")
    private YayinEkKom yayinEkKom;

}