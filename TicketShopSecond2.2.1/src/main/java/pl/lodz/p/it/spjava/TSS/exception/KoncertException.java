package pl.lodz.p.it.spjava.TSS.exception;

import pl.lodz.p.it.spjava.TSS.model.Koncert;

public class KoncertException extends AppBaseException {

    static final public String KEY_ACCOUNT_WRONG_STATE = "blad.konto.bledny.stan.problem";
    static final public String KEY_CONCERT_DELETE_STATE = "blad.koncert.usuniecie.stan.problem";
    static final public String KEY_CONCERT_DELETE_EXISTING_TICKET = "blad.koncert.usuniecie.istnieje.bilet";

    private Koncert koncert;

    public Koncert getKoncert() {
        return koncert;
    }

    public KoncertException(String message, Koncert koncert) {
        super(message);
        this.koncert = koncert;
    }

    public KoncertException(String message) {
        super(message);
    }

    public KoncertException(String message, Throwable cause) {
        super(message, cause);
    }

    static public KoncertException createExceptionWrongState(Koncert koncert) {
        return new KoncertException(KEY_ACCOUNT_WRONG_STATE, koncert);
    }

    static public KoncertException createExceptionDelete(Koncert koncert) {
        return new KoncertException(KEY_CONCERT_DELETE_STATE, koncert);
    }

    static public KoncertException createExceptionDeleteWhenExistcTicket(Koncert koncert) {
        return new KoncertException(KEY_CONCERT_DELETE_EXISTING_TICKET, koncert);
    }
}
