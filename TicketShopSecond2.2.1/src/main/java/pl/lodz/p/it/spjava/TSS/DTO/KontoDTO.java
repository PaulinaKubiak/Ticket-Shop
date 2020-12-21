package pl.lodz.p.it.spjava.TSS.DTO;

import java.util.Objects;
import pl.lodz.p.it.spjava.TSS.model.PoziomDostepu;

public class KontoDTO {

    private String login;
    private String haslo;
    private String imie;
    private String nazwisko;
    private String email;
    private String pytanie;
    private String odpowiedz;
    private boolean aktywne;
    private String stareHaslo;
    private PoziomDostepu poziomDostepu;
    private PoziomDostepu staryPoziomDostepu;

    public KontoDTO() {
    }

    public KontoDTO(String login, String haslo, String imie, String nazwisko, String email, String pytanie, String odpowiedz, boolean aktywne) {
        this.login = login;
        this.haslo = haslo;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.pytanie = pytanie;
        this.odpowiedz = odpowiedz;
        this.aktywne = aktywne;
    }

    public KontoDTO(String login, String imie, String nazwisko, String email) {
        this.login = login;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
    }

    public KontoDTO(String login, String imie, String nazwisko, String email, boolean aktywne) {
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.email = email;
        this.login = login;
        this.aktywne = aktywne;
    }

    public KontoDTO(String login, String haslo, String stareHaslo) {
        this.login = login;
        this.haslo = haslo;
        this.stareHaslo = stareHaslo;
    }

    public KontoDTO(String login) {
        this.login = login;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPytanie() {
        return pytanie;
    }

    public void setPytanie(String pytanie) {
        this.pytanie = pytanie;
    }

    public String getOdpowiedz() {
        return odpowiedz;
    }

    public void setOdpowiedz(String odpowiedz) {
        this.odpowiedz = odpowiedz;
    }

    public boolean isAktywne() {
        return aktywne;
    }

    public void setAktywne(boolean aktywne) {
        this.aktywne = aktywne;
    }

    public PoziomDostepu getPoziomDostepu() {
        return poziomDostepu;
    }

    public void setPoziomDostepu(PoziomDostepu poziomDostepu) {
        this.poziomDostepu = poziomDostepu;
    }

    public String getStareHaslo() {
        return stareHaslo;
    }

    public void setStareHaslo(String stareHaslo) {
        this.stareHaslo = stareHaslo;
    }

    public PoziomDostepu getStaryPoziomDostepu() {
        return staryPoziomDostepu;
    }

    public void setStaryPoziomDostepu(PoziomDostepu staryPoziomDostepu) {
        this.staryPoziomDostepu = staryPoziomDostepu;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.login);
        hash = 37 * hash + Objects.hashCode(this.haslo);
        hash = 37 * hash + Objects.hashCode(this.imie);
        hash = 37 * hash + Objects.hashCode(this.nazwisko);
        hash = 37 * hash + Objects.hashCode(this.email);
        hash = 37 * hash + Objects.hashCode(this.pytanie);
        hash = 37 * hash + Objects.hashCode(this.odpowiedz);
        hash = 37 * hash + (this.aktywne ? 1 : 0);
        hash = 37 * hash + Objects.hashCode(this.poziomDostepu);
        hash = 37 * hash + Objects.hashCode(this.stareHaslo);
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
        final KontoDTO other = (KontoDTO) obj;
        return true;
    }

    @Override
    public String toString() {
        return "KontoDTO{" + "login=" + login + ", haslo=" + haslo + ", stareHaslo=" + stareHaslo + '}';
    }
}
