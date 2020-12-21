package pl.lodz.p.it.spjava.TSS.web.konto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "resetHaslaPageBean")
@RequestScoped
public class ResetHaslaPageBean {

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private KontoDTO kontoDTO = new KontoDTO();

    public ResetHaslaPageBean() {
    }

    public KontoDTO getKontoDTO() {
        return kontoDTO;
    }

    public void setKontoDTO(KontoDTO kontoDTO) {
        this.kontoDTO = kontoDTO;
    }

    public String wybierzKontoDoResetuHaslaAction() {
        try {
            rejestracjaKontaControllerBean.wybierzKontoDoZmiany(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ResetHaslaPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        rejestracjaKontaControllerBean.getSelectedKontoDTO().setOdpowiedz("");
        return "resetHasla2";
    }
}
