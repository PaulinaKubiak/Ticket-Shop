package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "KONCERT")
@TableGenerator(name = "KoncertGenerator", table = "TableGenerator", pkColumnName = "ID", valueColumnName = "value", pkColumnValue = "KoncertGen")

@NamedQueries({
    @NamedQuery(name = "Koncert.findAll", query = "SELECT k FROM Koncert k")
    , 
    @NamedQuery(name = "Koncert.findByWykonawca", query = "SELECT k FROM Koncert k WHERE k.wykonawca = :wk")
    , 
    @NamedQuery(name = "Koncert.findByMiejsce", query = "SELECT k FROM Koncert k WHERE k.miejsce = :miejsce"),})

public class Koncert extends AbstractEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "KoncertGenerator")
    @Column(name = "ID", nullable = false, unique = true, updatable = false)
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    @Column(name = "WYKONAWCA", nullable = false)
    private String wykonawca;

    @Basic(optional = false)
    @NotNull
    @Column(name = "DATA", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date data;

    @NotNull
    @Size(min = 1, max = 60)
    @Column(name = "MIEJSCE", nullable = false)
    private String miejsce;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "PULABILETOW", nullable = false)
    private int pulaBiletow;

    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "CENA", nullable = false)
    private int cena;

    @NotNull
    @JoinColumn(name = "WprowadzKoncert", nullable = true)
    @OneToOne
    private Pracownik wprowadzKoncert;

    @NotNull
    @JoinColumn(name = "MODYFIKOWANEPRZEZ", nullable = true)
    @OneToOne
    private Pracownik modyfikowanePrzez;

    public Koncert() {
    }

    public Koncert(Long id) {
        this.id = id;
    }

    public Koncert(Long id, String wykonawca, Date data, String miejsce, int pulaBiletow, int cena, Pracownik wprowadzKoncert, Pracownik modyfikowanePrzez) {
        this.id = id;
        this.wykonawca = wykonawca;
        this.data = data;
        this.miejsce = miejsce;
        this.pulaBiletow = pulaBiletow;
        this.cena = cena;
        this.wprowadzKoncert = wprowadzKoncert;
        this.modyfikowanePrzez = modyfikowanePrzez;
    }

    public Long getId() {
        return id;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getWykonawca() {
        return wykonawca;
    }

    public void setWykonawca(String wykonawca) {
        this.wykonawca = wykonawca;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(String miejsce) {
        this.miejsce = miejsce;
    }

    public int getPulaBiletow() {
        return pulaBiletow;
    }

    public void setPulaBiletow(int pulaBiletow) {
        this.pulaBiletow = pulaBiletow;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public Pracownik getWprowadzKoncert() {
        return wprowadzKoncert;
    }

    public void setWprowadzKoncert(Pracownik wprowadzKoncert) {
        this.wprowadzKoncert = wprowadzKoncert;
    }

    public Pracownik getModyfikowanePrzez() {
        return modyfikowanePrzez;
    }

    public void setModyfikowanePrzez(Pracownik modyfikowanePrzez) {
        this.modyfikowanePrzez = modyfikowanePrzez;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.id);
        hash = 97 * hash + Objects.hashCode(this.wykonawca);
        hash = 97 * hash + Objects.hashCode(this.data);
        hash = 97 * hash + Objects.hashCode(this.miejsce);
        hash = 97 * hash + this.pulaBiletow;
        hash = 97 * hash + this.cena;
        hash = 97 * hash + Objects.hashCode(this.wprowadzKoncert);
        hash = 97 * hash + Objects.hashCode(this.modyfikowanePrzez);
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
        final Koncert other = (Koncert) obj;
        if (this.pulaBiletow != other.pulaBiletow) {
            return false;
        }
        if (this.cena != other.cena) {
            return false;
        }
        if (!Objects.equals(this.wykonawca, other.wykonawca)) {
            return false;
        }
        if (!Objects.equals(this.miejsce, other.miejsce)) {
            return false;
        }
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.wprowadzKoncert, other.wprowadzKoncert)) {
            return false;
        }
        if (!Objects.equals(this.modyfikowanePrzez, other.modyfikowanePrzez)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Koncert{" + "id=" + id + ", wykonawca=" + wykonawca + ", data=" + data + ", miejsce=" + miejsce + ", pulaBiletow=" + pulaBiletow + ", cena=" + cena + ", wprowadzKoncert=" + wprowadzKoncert + ", modyfikowanePrzez=" + modyfikowanePrzez + '}';
    }
}
