package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(PoziomDostepu.KluczePoziomuDostepu.PRACOWNIK_KEY)

@NamedQueries({
    @NamedQuery(name = "Pracownik.findAll", query = "SELECT p FROM Pracownik p")
    , @NamedQuery(name = "Pracownik.findByLogin", query = "SELECT p FROM Pracownik p WHERE p.login = :lg")})

public class Pracownik extends Konto implements Serializable {

    public Pracownik() {
    }

    public Pracownik(Long id) {
        super(id);
    }

    public Pracownik(Long id, String imie, String nazwisko, String email, String login, String haslo, String pytaniekontrolne, String odpowiedzkontrolna, boolean aktywne, Date dataModyfikacji, Administrator modyfikowanePrzez, boolean autoryzowane) {
        super(id, imie, nazwisko, email, login, haslo, pytaniekontrolne, odpowiedzkontrolna, aktywne, dataModyfikacji, modyfikowanePrzez, autoryzowane);
    }

    public Pracownik(Konto konto) {
        super(konto.getId(),
                konto.getImie(),
                konto.getNazwisko(),
                konto.getEmail(),
                konto.getLogin(),
                konto.getHaslo(),
                konto.getPytaniekontrolne(),
                konto.getOdpowiedzkontrolna(),
                konto.isAktywne(),
                konto.getDataModyfikacji(),
                konto.getModyfikowanePrzez(),
                konto.isAutoryzowane());
    }
}
