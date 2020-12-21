package pl.lodz.p.it.spjava.TSS.exception;

import javax.persistence.NoResultException;
import pl.lodz.p.it.spjava.TSS.model.Konto;

public class KontoException extends AppBaseException {

    static final public String KEY_ACCOUNT_LOGIN_EXIST = "blad.konto.login.istnieje.problem";
    static final public String KEY_ACCOUNT_WRONG_STATE = "blad.konto.bledny.stan.problem";
    static final public String KEY_ACCOUNT_WRONG_PASSWORD = "blad.konto.bledne.haslo.problem";
    static final public String KEY_ACCOUNT_NOT_EXISTS = "blad.konto.nie.istnieje.problem";
    static final public String KEY_ACCOUNT_ACTIVATED = "blad.konto.aktywne.problem";
    static final public String KEY_ACCOUNT_DEACTIVATED = "blad.konto.nie.aktywne.problem";
    static final public String KEY_ACCOUNT_DELETE = "blad.konto.usuniecie.problem";
    static final public String KEY_ACCOUNT_CHANGE_ACCESS_LEVEL = "blad.konto.brak.mozliwosci.zmiany.poziomu.dostepu";
    static final public String KEY_ACCOUNT_ALREADY_AUTHORIZED = "blad.konto.autoryzowane.problem";

    private Konto konto;

    public Konto getKonto() {
        return konto;
    }

    public KontoException(String message, Konto konto) {
        super(message);
        this.konto = konto;
    }

    public KontoException(String message, Throwable cause, Konto konto) {
        super(message, cause);
        this.konto = konto;
    }

    public KontoException(String message) {
        super(message);
    }

    public KontoException(String message, Throwable cause) {
        super(message, cause);
    }

    static public KontoException createExceptionLoginExists(Throwable cause, Konto konto) {
        return new KontoException(KEY_ACCOUNT_LOGIN_EXIST, cause, konto);
    }

    static public KontoException createExceptionWrongState(Konto konto) {
        return new KontoException(KEY_ACCOUNT_WRONG_STATE, konto);
    }

    static public KontoException createExceptionWrongPassword(Throwable cause, Konto konto) {
        return new KontoException(KEY_ACCOUNT_WRONG_PASSWORD, cause, konto);
    }

    static public KontoException createExceptionNotExist(NoResultException e) {
        return new KontoException(KEY_ACCOUNT_NOT_EXISTS, e);
    }

    static public KontoException createExceptionActvivated(Konto konto) {
        return new KontoException(KEY_ACCOUNT_ACTIVATED, konto);
    }

    static public KontoException createExceptionDeactvivated(Konto konto) {
        return new KontoException(KEY_ACCOUNT_DEACTIVATED, konto);
    }

    static public KontoException createExceptionDeleted(Konto konto) {
        return new KontoException(KEY_ACCOUNT_DELETE, konto);
    }

    static public KontoException createExceptionForChangeAccessLevel(Throwable cause, Konto konto) {
        return new KontoException(KEY_ACCOUNT_CHANGE_ACCESS_LEVEL, cause, konto);
    }

    static public KontoException createExceptionAccountAlreadyAuthorized(Konto konto) {
        return new KontoException(KEY_ACCOUNT_ALREADY_AUTHORIZED, konto);
    }
}
