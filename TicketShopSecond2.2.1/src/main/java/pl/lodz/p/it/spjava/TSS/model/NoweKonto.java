package pl.lodz.p.it.spjava.TSS.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(PoziomDostepu.KluczePoziomuDostepu.NOWEKONTO_KEY)

public class NoweKonto extends Konto implements Serializable {

    public NoweKonto() {
    }

    public NoweKonto(Long id) {
        super(id);
    }

    public NoweKonto(Long id, String imie, String nazwisko, String email, String login, String haslo, String pytaniekontrolne, String odpowiedzkontrolna, boolean aktywne, Date dataModyfikacji, Administrator modyfikowanePrzez, boolean autoryzowane) {
        super(id, imie, nazwisko, email, login, haslo, pytaniekontrolne, odpowiedzkontrolna, aktywne, dataModyfikacji, modyfikowanePrzez, autoryzowane);
    }

}
