package pl.lodz.p.it.spjava.TSS.ejb.Facade;

import java.sql.SQLNonTransientConnectionException;
import java.util.List;
import javax.annotation.security.PermitAll;
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
import pl.lodz.p.it.spjava.TSS.model.Bilet;
import pl.lodz.p.it.spjava.TSS.model.Klient;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
public class BiletFacade extends AbstractFacade<Bilet> {

    @PersistenceContext(unitName = "TicketShopSecondPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public BiletFacade() {
        super(Bilet.class);
    }

    @PermitAll
    public Long pobierzIloscSprzedanychBiletow(Long idKoncertu) throws AppBaseException {
        try {
            TypedQuery<Long> tq = em.createNamedQuery("Bilet.iloscSprzedanychNaKoncert", Long.class);
            tq.setParameter("idKoncertu", idKoncertu);
            return (Long) tq.getSingleResult();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
    }

    @RolesAllowed({"Klient"})
    public List<Bilet> pobierzListeBiletowKlient(Klient idKlienta) throws AppBaseException {
        try {
            TypedQuery<Bilet> tq = em.createNamedQuery("Bilet.listaBiletowKlient", Bilet.class);
            tq.setParameter("idKlienta", idKlienta);
            return tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
    }

    public List<Bilet> znajdzBilet() throws AppBaseException {
        try {
            TypedQuery<Bilet> tq = em.createNamedQuery("Bilet.findAll", Bilet.class);
            return tq.getResultList();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
    }

    public Bilet pobierzSprzedanyBilet(Long idBiletu) throws AppBaseException {
        try {
            TypedQuery<Bilet> tq = em.createNamedQuery("Bilet.findById", Bilet.class);
            tq.setParameter("id", idBiletu);
            return tq.getSingleResult();
        } catch (PersistenceException e) {
            if (e.getCause() instanceof DatabaseException && e.getCause().getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(e);
            }
        }
    }

    public List<Bilet> znajdzWykonawce(String wykonawca) throws AppBaseException {
        List<Bilet> koncerty;
        try {
            TypedQuery<Bilet> tq = em.createNamedQuery("Bilet.findByWykonawca", Bilet.class);
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

    @RolesAllowed({"Klient", "Pracownik"})
    @Override
    public void remove(Bilet entity) throws AppBaseException {
        try {
            super.remove(entity);
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
}
