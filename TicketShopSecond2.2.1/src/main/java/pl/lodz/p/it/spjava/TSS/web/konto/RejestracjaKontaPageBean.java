package pl.lodz.p.it.spjava.TSS.web.konto;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "rejestracjaKontaPageBean")
@RequestScoped
public class RejestracjaKontaPageBean implements Serializable {

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private KontoDTO kontoDTO;
    private String potworzHaslo;

    public RejestracjaKontaPageBean() {
    }

    public KontoDTO getKontoDTO() {
        return kontoDTO;
    }

    public void setKontoDTO(KontoDTO kontoDTO) {
        this.kontoDTO = kontoDTO;
    }

    public String getPotworzHaslo() {
        return potworzHaslo;
    }

    public void setPotworzHaslo(String potworzHaslo) {
        this.potworzHaslo = potworzHaslo;
    }

    @PostConstruct
    public void init() {
        kontoDTO = new KontoDTO();
    }

    public String rejestracjaKontaAction() {
        if (potworzHaslo != null && potworzHaslo.equals(kontoDTO.getHaslo())) {
            try {
                rejestracjaKontaControllerBean.rejestrujKonto(kontoDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(RejestracjaKontaPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:passwordRepeat", "konto.utworz.blad");
        }
        return "main";
    }
}
