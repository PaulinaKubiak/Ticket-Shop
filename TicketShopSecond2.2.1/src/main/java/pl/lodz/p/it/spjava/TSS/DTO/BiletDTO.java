package pl.lodz.p.it.spjava.TSS.DTO;

import java.util.Date;
import java.util.Objects;

public class BiletDTO {

    private long id;
    private String wykonawca;
    private Date data;
    private String miejsce;
    private int cena;
    private int iloscBiletow;
    private Date data_utworzenia;
    private KontoDTO klient;
    private KoncertDTO koncert;
    private int lp;
    private int iloscWolnych;

    public BiletDTO() {
    }

    public BiletDTO(long id, KontoDTO klient, KoncertDTO koncert) {
        this.id = id;
        this.klient = klient;
        this.koncert = koncert;
    }

    public BiletDTO(long id, KontoDTO klient, KoncertDTO koncert, int lp) {
        this.id = id;
        this.klient = klient;
        this.koncert = koncert;
        this.lp = lp;
    }

    public BiletDTO(long id, KoncertDTO koncert) {
        this.id = id;
        this.koncert = koncert;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getWykonawca() {
        return wykonawca;
    }

    public void setWykonawca(String wykonawca) {
        this.wykonawca = wykonawca;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(String miejsce) {
        this.miejsce = miejsce;
    }

    public int getCena() {
        return cena;
    }

    public void setCena(int cena) {
        this.cena = cena;
    }

    public int getIloscBiletow() {
        return iloscBiletow;
    }

    public void setIloscBiletow(int iloscBiletow) {
        this.iloscBiletow = iloscBiletow;
    }

    public Date getData_utworzenia() {
        return data_utworzenia;
    }

    public void setData_utworzenia(Date data_utworzenia) {
        this.data_utworzenia = data_utworzenia;
    }

    public KontoDTO getKlient() {
        return klient;
    }

    public void setKlient(KontoDTO klient) {
        this.klient = klient;
    }

    public KoncertDTO getKoncert() {
        return koncert;
    }

    public void setKoncert(KoncertDTO koncert) {
        this.koncert = koncert;
    }

    public int getLp() {
        return lp;
    }

    public void setLp(int lp) {
        this.lp = lp;
    }

    public int getIloscWolnych() {
        return iloscWolnych;
    }

    public void setIloscWolnych(int iloscWolnych) {
        this.iloscWolnych = iloscWolnych;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + (int) (this.id ^ (this.id >>> 32));
        hash = 59 * hash + Objects.hashCode(this.wykonawca);
        hash = 59 * hash + Objects.hashCode(this.data);
        hash = 59 * hash + Objects.hashCode(this.miejsce);
        hash = 59 * hash + this.cena;
        hash = 59 * hash + this.iloscBiletow;
        hash = 59 * hash + Objects.hashCode(this.data_utworzenia);
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
        final BiletDTO other = (BiletDTO) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.cena != other.cena) {
            return false;
        }
        if (this.iloscBiletow != other.iloscBiletow) {
            return false;
        }
        if (!Objects.equals(this.wykonawca, other.wykonawca)) {
            return false;
        }
        if (!Objects.equals(this.miejsce, other.miejsce)) {
            return false;
        }
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        if (!Objects.equals(this.data_utworzenia, other.data_utworzenia)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "BiletDTO{" + "id=" + id + ", wykonawca=" + wykonawca + ", data=" + data + ", miejsce=" + miejsce + ", cena=" + cena + ", iloscBiletow=" + iloscBiletow + ", data_utworzenia=" + data_utworzenia + ", klient=" + klient + ", koncert=" + koncert + ", lp=" + lp + '}';
    }
}
