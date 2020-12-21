package pl.lodz.p.it.spjava.TSS.web.koncert;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import javax.ejb.EJB;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KoncertEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "koncertControllerBean")
@SessionScoped
public class KoncertControllerBean implements Serializable {

    @EJB
    private KoncertEndpoint koncertEndpoint;
    private int lastActionMethod = 0;
    private KoncertDTO rejestrujKoncert;
    private KoncertDTO selectedKoncertDTO;
    private KontoDTO klientID;

    public KoncertControllerBean() {
    }

    public KoncertDTO getSelectedKoncertDTO() {
        return selectedKoncertDTO;
    }

    public void setSelectedKoncertDTO(KoncertDTO selectedKoncertDTO) {
        this.selectedKoncertDTO = selectedKoncertDTO;
    }

    public void rejestrujKoncert(final KoncertDTO koncertDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = koncertDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = koncertEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                koncertEndpoint.utworzKoncert(koncertDTO);
                endpointCallCounter--;
            } while (koncertEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void usunKoncert() throws AppBaseException {
        final int UNIQ_METHOD_ID = selectedKoncertDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = koncertEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                koncertEndpoint.usunKoncert(selectedKoncertDTO);
                endpointCallCounter--;
            } while (koncertEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void edytujKoncert(final KoncertDTO koncertDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = koncertDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = koncertEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                koncertEndpoint.edytujKoncert(koncertDTO);
                endpointCallCounter--;
            } while (koncertEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void wybierzKoncertDoZmiany(KoncertDTO koncertDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = koncertDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = koncertEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedKoncertDTO = koncertEndpoint.zapamietajWybraneKontoWStanie(koncertDTO);
                endpointCallCounter--;
            } while (koncertEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void wybierzKoncertDoZmianyZakupBiletu(KoncertDTO koncertDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = koncertDTO.hashCode() + 5;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = koncertEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedKoncertDTO = koncertEndpoint.wybierzKoncertDoZmianyZakupBiletu(koncertDTO);
                endpointCallCounter--;
            } while (koncertEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }
}
