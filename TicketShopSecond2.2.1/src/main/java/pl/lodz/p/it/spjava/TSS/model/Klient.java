package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(PoziomDostepu.KluczePoziomuDostepu.KLIENT_KEY)
@NamedQueries({
    @NamedQuery(name = "Klient.findAll", query = "SELECT k FROM Klient k")
    , 
    @NamedQuery(name = "Klient.findByLogin", query = "SELECT k FROM Klient k WHERE k.login = :lg")})

public class Klient extends Konto implements Serializable {

    public Klient() {
    }

    public Klient(Long id) {
        super(id);
    }

    public Klient(Long id, String imie, String nazwisko, String email, String login, String haslo, String pytaniekontrolne, String odpowiedzkontrolna, boolean aktywne, Date dataModyfikacji, Administrator modyfikowanePrzez, boolean autoryzowane) {
        super(id, imie, nazwisko, email, login, haslo, pytaniekontrolne, odpowiedzkontrolna, aktywne, dataModyfikacji, modyfikowanePrzez, autoryzowane);
    }

    public Klient(Konto konto) {
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
