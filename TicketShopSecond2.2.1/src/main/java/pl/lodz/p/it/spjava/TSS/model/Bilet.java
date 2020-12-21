package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "BILET")
@NamedQueries({
    @NamedQuery(name = "Bilet.iloscSprzedanychNaKoncert", query = "SELECT COUNT(b) FROM Bilet b WHERE b.koncert.id=:idKoncertu")
    , 
    @NamedQuery(name = "Bilet.listaBiletowKlient", query = "SELECT b FROM Bilet b WHERE b.klient =:idKlienta")
    , 
    @NamedQuery(name = "Bilet.findAll", query = "SELECT b FROM Bilet b")
    ,
    @NamedQuery(name = "Bilet.findById", query = "SELECT b FROM Bilet b WHERE b.id = :id")
    ,
    @NamedQuery(name = "Bilet.findByWykonawca", query = "SELECT b FROM Bilet b where b.koncert.wykonawca = :wk")
})
@TableGenerator(name = "BiletGenerator", table = "BiletGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "BiletGen")

public class Bilet extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "BiletGenerator")
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 250)
    @JoinColumn(name = "KlientID", nullable = false)
    @ManyToOne
    private Klient klient;

    @JoinColumn(name = "IDKONCERTU", referencedColumnName = "ID")
    @ManyToOne(optional = false)
    private Koncert koncert;

    public Bilet() {
    }

    public Bilet(Long id) {
        this.id = id;
    }

    public Bilet(Long id, Klient klient, Koncert koncert) {
        this.id = id;
        this.klient = klient;
        this.koncert = koncert;
    }

    public Long getId() {
        return id;
    }

    public Klient getKlient() {
        return klient;
    }

    public void setKlient(Klient klient) {
        this.klient = klient;
    }

    public Koncert getKoncert() {
        return koncert;
    }

    public void setKoncert(Koncert koncert) {
        this.koncert = koncert;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.klient);
        hash = 97 * hash + Objects.hashCode(this.koncert);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bilet other = (Bilet) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.klient, other.klient)) {
            return false;
        }
        if (!Objects.equals(this.koncert, other.koncert)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Bilet{" + "id=" + id + ", klient=" + klient + ", koncert=" + koncert + '}';
    }

}
