package pl.lodz.p.it.spjava.TSS.ejb.Facade;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.model.NoweKonto;

@Stateless
@TransactionAttribute(TransactionAttributeType.MANDATORY)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Administrator")
public class NoweKontoFacade extends AbstractFacade<NoweKonto> {

    @PersistenceContext(unitName = "TicketShopSecondPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public NoweKontoFacade() {
        super(NoweKonto.class);
    }
}
