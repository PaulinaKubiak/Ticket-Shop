package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;

@Entity
@DiscriminatorValue(PoziomDostepu.KluczePoziomuDostepu.ADMINISTRATOR_KEY)

@NamedQueries({
    @NamedQuery(name = "Administrator.findAll", query = "SELECT a FROM Administrator a")
    , @NamedQuery(name = "Administrator.findByLogin", query = "SELECT a FROM Administrator a WHERE a.login = :lg")})

public class Administrator extends Konto implements Serializable {

    public Administrator() {
    }

    public Administrator(Long id) {
        super(id);
    }

    public Administrator(Long id, String imie, String nazwisko, String email, String login, String haslo, String pytaniekontrolne, String odpowiedzkontrolna, boolean aktywne, Date dataModyfikacji, Administrator modyfikowanePrzez, boolean autoryzowane) {
        super(id, imie, nazwisko, email, login, haslo, pytaniekontrolne, odpowiedzkontrolna, aktywne, dataModyfikacji, modyfikowanePrzez, autoryzowane);
    }

    public Administrator(Konto konto) {
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
