package pl.lodz.p.it.spjava.TSS.exception;

import javax.ejb.ApplicationException;
import javax.persistence.OptimisticLockException;
import pl.lodz.p.it.spjava.TSS.model.AbstractEntity;

@ApplicationException(rollback = true)
public class AppBaseException extends Exception {

    static final public String KEY_CREATE_OBJECT = "nie.udalo.sie.utworzyc.obiektu";
    static final public String KEY_EDIT_OBJECT = "nie.udalo.sie.edytować.obiektu";
    static final public String KEY_DELETE_OBJECT = "nie.udalo.sie.usunac.obiektu";

    static final public String KEY_OPTIMISTIC_LOCK = "blad.blokada.optymistyczna.problem";
    static final public String KEY_REPEATED_TRANSACTION_ROLLBACK = "blad.powtorzenia.tranzakcji.problem";
    static final public String KEY_DATABASE_QUERY_PROBLEM = "blad.baza.danych.problem";
    static final public String KEY_DATABASE_CONNECTION_PROBLEM = "blad.baza.danych.polaczenie.problem";
    static final public String KEY_ACTION_NOT_AUTHORIZED = "blad.akcja.brak.autoryzacji.problem";

    private AbstractEntity pole;

    public AbstractEntity getPole() {
        return pole;
    }

    public AppBaseException(String message) {
        super(message);
    }

    public AppBaseException(AbstractEntity pole, String message) {
        super(message);
        this.pole = pole;
    }

    public AppBaseException(AbstractEntity pole, String komunikat, Throwable powod) {
        super(komunikat, powod);
        this.pole = pole;
    }

    protected AppBaseException(String message, Throwable cause) {
        super(message, cause);
    }

    static public AppBaseException creatExceptionForRepeatedTransactionRollback() {
        return new AppBaseException(KEY_REPEATED_TRANSACTION_ROLLBACK);
    }

    public static AppBaseException createExceptionDatabaseQueryProblem(Throwable e) {
        return new AppBaseException(KEY_DATABASE_QUERY_PROBLEM, e);
    }

    public static AppBaseException createExceptionDatabaseConnectionProblem(Throwable e) {
        return new AppBaseException(KEY_DATABASE_CONNECTION_PROBLEM, e);
    }

    public static AppBaseException createExceptionOptimisticLock(OptimisticLockException e) {
        return new AppBaseException(KEY_OPTIMISTIC_LOCK, e);
    }

    public static AppBaseException createExceptionOptimisticLock(NullPointerException e) {
        return new AppBaseException(KEY_OPTIMISTIC_LOCK, e);
    }

    public static AppBaseException createExceptionNotAuthorizedAction() {
        return new AppBaseException(KEY_ACTION_NOT_AUTHORIZED);
    }

    public static AppBaseException createCreateObject(Throwable e) {
        return new AppBaseException(KEY_CREATE_OBJECT, e);
    }

    public static AppBaseException createEditObject(Throwable e) {
        return new AppBaseException(KEY_EDIT_OBJECT, e);
    }

    public static AppBaseException createDeleteObject(Throwable e) {
        return new AppBaseException(KEY_DELETE_OBJECT, e);
    }
}
