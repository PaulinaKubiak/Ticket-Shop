package pl.lodz.p.it.spjava.TSS.web.konto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KontoEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "usunKontoPageBean")
@RequestScoped
public class UsunKontoPageBean {

    @EJB
    private KontoEndpoint kontoEndpoint;

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private KontoDTO kontoDTO;

    public UsunKontoPageBean() {
    }

    public KontoDTO getKontoDTO() {
        return kontoDTO;
    }

    public void setKontoDTO(KontoDTO kontoDTO) {
        this.kontoDTO = kontoDTO;
    }

    @PostConstruct
    private void init() {
        kontoDTO = rejestracjaKontaControllerBean.getSelectedKontoDTO();
    }

    public String usunKontoAction() {
        try {
            rejestracjaKontaControllerBean.usunKonto(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(UsunKontoPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listaRejestracjiKonta";
    }
}
