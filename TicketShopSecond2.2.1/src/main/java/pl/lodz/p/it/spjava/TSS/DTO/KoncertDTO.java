package pl.lodz.p.it.spjava.TSS.DTO;

import java.util.Date;
import java.util.Objects;

public class KoncertDTO {

    private long Id;
    private String wykonawca;
    private Date data;
    private String miejsce;
    private int pulaBiletow;
    private int cena;
    private KontoDTO klientID;
    private boolean wyprzedane;

    public KoncertDTO() {
    }

    public KoncertDTO(long Id, String wykonawca, Date data, String miejsce, int pulaBiletow, int cena) {
        this.Id = Id;
        this.wykonawca = wykonawca;
        this.data = data;
        this.miejsce = miejsce;
        this.pulaBiletow = pulaBiletow;
        this.cena = cena;
    }

    public KoncertDTO(String wykonawca, Date data, String miejsce, int cena) {
        this.wykonawca = wykonawca;
        this.data = data;
        this.miejsce = miejsce;
        this.cena = cena;
    }

    public long getId() {
        return Id;
    }

    public void setId(long Id) {
        this.Id = Id;
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

    public String getDisplayLabel() {
        return wykonawca + " " + data;
    }

    public boolean isWyprzedane() {
        return wyprzedane;
    }

    public void setWyprzedane(boolean wyprzedane) {
        this.wyprzedane = wyprzedane;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 53 * hash + (int) (this.Id ^ (this.Id >>> 32));
        hash = 53 * hash + Objects.hashCode(this.wykonawca);
        hash = 53 * hash + Objects.hashCode(this.data);
        hash = 53 * hash + Objects.hashCode(this.miejsce);
        hash = 53 * hash + this.pulaBiletow;
        hash = 53 * hash + this.cena;
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
        final KoncertDTO other = (KoncertDTO) obj;
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
        if (!Objects.equals(this.data, other.data)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return String.valueOf(wykonawca);
    }

}
