package meb.gov.tr.ogretmenkervani.webapp.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "OGRT_BAK_TEMS")
public class BakanlikTemsilcisi extends Ogretmen {
    @OneToMany(mappedBy = "bakanlikTemsilcisi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OnIncelemeKom> onIncelemeKomList;
    @OneToMany(mappedBy = "bakanlikTemsilcisi", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<YayinEkKom> yayinEkKomList;
}