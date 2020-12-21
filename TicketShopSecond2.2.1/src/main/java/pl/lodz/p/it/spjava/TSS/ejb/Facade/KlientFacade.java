package pl.lodz.p.it.spjava.TSS.ejb.Facade;

import java.sql.SQLNonTransientConnectionException;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;
import org.eclipse.persistence.exceptions.DatabaseException;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.model.Klient;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Administrator")
public class KlientFacade extends AbstractFacade<Klient> {

    @PersistenceContext(unitName = "TicketShopSecondPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public KlientFacade() {
        super(Klient.class);
    }

    @RolesAllowed({"Administrator", "Klient"})
    public Klient znajdzKlienta(String login) throws AppBaseException {
        try {
            TypedQuery<Klient> tq = em.createNamedQuery("Klient.findByLogin", Klient.class);
            tq.setParameter("lg", login);
            return tq.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (PersistenceException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof DatabaseException && cause.getCause() instanceof SQLNonTransientConnectionException) {
                throw AppBaseException.createExceptionDatabaseConnectionProblem(e);
            } else {
                throw AppBaseException.createExceptionDatabaseQueryProblem(cause);
            }
        }
    }
}
