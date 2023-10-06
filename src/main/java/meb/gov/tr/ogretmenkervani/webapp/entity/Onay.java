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
@Table(name = "OGRT_ONAY")
public class Onay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_ONAY_ID", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_ICERIK_ID", referencedColumnName = "PK_ICERIK_ID", nullable = false)
    private Icerik icerik;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_KOM_UYE_ID", referencedColumnName = "PK_TC_KIMLIK_NO", nullable = false)
    private Ogretmen komisyonUye;

    @Column(name = "ONY_DUR", nullable = false)
    private Boolean onayDurumu;
}

