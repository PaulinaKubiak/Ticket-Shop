package pl.lodz.p.it.spjava.TSS.web.konto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "zmianaHaslaPageBean")
@RequestScoped
public class ZmianaHaslaPageBean {

    @Inject
    RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private KontoDTO kontoDTO;

    private String powtorzNoweHaslo;

    public ZmianaHaslaPageBean() {
    }

    public KontoDTO getKontoDTO() {
        return kontoDTO;
    }

    public void setKontoDTO(KontoDTO kontoDTO) {
        this.kontoDTO = kontoDTO;
    }

    public String getPowtorzNoweHaslo() {
        return powtorzNoweHaslo;
    }

    public void setPowtorzNoweHaslo(String powtorzNoweHaslo) {
        this.powtorzNoweHaslo = powtorzNoweHaslo;
    }

    @PostConstruct
    public void init() {
        kontoDTO = rejestracjaKontaControllerBean.getSelectedKontoDTO();
    }

    public String zapiszZmianeHaslaActionn() throws AppBaseException {
        if (powtorzNoweHaslo.equals(kontoDTO.getHaslo())) {
            try {
                rejestracjaKontaControllerBean.zmienHaslo(kontoDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ZmianaHaslaPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("ChangePasswordForm:passwordRepeat", "passwords.not.matching");
            return null;
        }
        return "listaKontAktywnych";
    }
}
