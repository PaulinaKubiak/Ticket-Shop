package pl.lodz.p.it.spjava.TSS.web.konto;

import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KontoEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "rejestracjaKontaController")
@SessionScoped
public class RejestracjaKontaControllerBean implements Serializable {

    @EJB
    private KontoEndpoint kontoEndpoint;

    private int lastActionMethod = 0;

    private KontoDTO selectedKontoDTO;
    private List<KontoDTO> listaKont;

    public RejestracjaKontaControllerBean() {
    }

    public KontoDTO getSelectedKontoDTO() {
        return selectedKontoDTO;
    }

    public List<KontoDTO> getListaKont() {
        if (listaKont == null) {
            try {
                listaKont = kontoEndpoint.listaNowychKont();
            } catch (AppBaseException ex) {
                Logger.getLogger(RejestracjaKontaControllerBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
            }
        }
        return listaKont;
    }

    public void setListaKont(List<KontoDTO> listaKont) {
        listaKont = null;
        this.listaKont = listaKont;
    }

    public void setSelectedKontoDTO(KontoDTO selectedKontoDTO) {
        this.selectedKontoDTO = selectedKontoDTO;
    }

    public void rejestrujKonto(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 1;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.rejestracjaKonta(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void usunKonto(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.usunKonto(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void ustawPoziomDostepu(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 3;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.ustawPoziomDostepu(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            lastActionMethod = UNIQ_METHOD_ID;
        }
    }

    public void edytujKonto(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 4;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.edytujKonto(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void aktywujKonto(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 5;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.aktywujKonto(kontoDTO.getLogin());
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void dezaktywujKonto(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 6;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.dezaktywujKonto(kontoDTO.getLogin());
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    void wybierzKontoDoZmiany(KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 7;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedKontoDTO = kontoEndpoint.zapamietajWybraneKontoWStanie(kontoDTO.getLogin());
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void zmienPoziomDostepuKonta(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 8;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.zmienPoziomDostepu(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            lastActionMethod = UNIQ_METHOD_ID;
        }
    }

    public void zmienHaslo(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 9;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.zmienHaslo(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public KontoDTO pobierzMojeKontoDTO() throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoEndpoint.hashCode() + 10;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                selectedKontoDTO = kontoEndpoint.zapamietajMojeKontoWStanie();
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
        return selectedKontoDTO;
    }

    public void zmienMojeHaslo(final KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 11;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.zmienMojeHaslo(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void resetHaslo(KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 12;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.resetHasla(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw AppBaseException.creatExceptionForRepeatedTransactionRollback();
            }
            ContextUtils.emitI18NMessage("RegisterForm:operationSuccess", "error.sukces");
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:repeatedAction", "error.powtorz.akcje");
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public void edytujMojeKonto(KontoDTO kontoDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = kontoDTO.hashCode() + 13;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = kontoEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                kontoEndpoint.edytujMojeKonto(kontoDTO);
                endpointCallCounter--;
            } while (kontoEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
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
