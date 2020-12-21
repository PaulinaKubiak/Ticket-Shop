package pl.lodz.p.it.spjava.TSS.ejb.Facade;

import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.model.Koncert;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)

public class KoncertFacade extends AbstractFacade<Koncert> {

    @PersistenceContext(unitName = "TicketShopSecondPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KoncertFacade() {
        super(Koncert.class);
    }

    public List<Koncert> znajdzZarejestrowanyKoncert() throws AppBaseException {
        try {
            TypedQuery<Koncert> tq = em.createNamedQuery("Koncert.findAll", Koncert.class);
            return tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
    }

    public List<Koncert> znajdzWykonawce(String wykonawca) throws AppBaseException {
        List<Koncert> koncerty;
        try {
            TypedQuery<Koncert> tq = em.createNamedQuery("Koncert.findByWykonawca", Koncert.class);
            tq.setParameter("wk", wykonawca);
            koncerty = tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
        return koncerty;
    }

    @RolesAllowed({"Pracownik"})
    @Override
    public void edit(Koncert entity) throws AppBaseException {
        try {
            super.edit(entity);
        } catch (DatabaseException e) {
            if (e.getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        } catch (OptimisticLockException e) {
            throw AppBaseException.createExceptionOptimisticLock(e);
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            throw AppBaseException.createExceptionDatabaseQueryProblem(cause);
        }
    }

    @RolesAllowed({"Pracownik"})
    @Override
    public void remove(Koncert entity) throws AppBaseException {
        try {
            super.remove(entity);
        } catch (NullPointerException e) {
            throw AppBaseException.createExceptionOptimisticLock(e);
        } catch (OptimisticLockException e) {
            throw AppBaseException.createExceptionOptimisticLock(e);
        } catch (DatabaseException e) {
            if (e.getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            throw AppBaseException.createExceptionDatabaseQueryProblem(cause);
        }
    }
}
