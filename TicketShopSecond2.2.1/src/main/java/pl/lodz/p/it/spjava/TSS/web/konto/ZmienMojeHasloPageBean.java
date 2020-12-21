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

@Named(value = "zmienMojeHasloPageBean")
@RequestScoped
public class ZmienMojeHasloPageBean {

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaKontrolerBean;

    private KontoDTO kontoDTO;
    private String powtorzNoweHaslo;
    private String stareHaslo;

    public ZmienMojeHasloPageBean() {
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

    public String getStareHaslo() {
        return stareHaslo;
    }

    public void setStareHaslo(String stareHaslo) {
        this.stareHaslo = stareHaslo;
    }

    @PostConstruct
    public void init() {
        try {
            kontoDTO = rejestracjaKontaKontrolerBean.pobierzMojeKontoDTO();
        } catch (AppBaseException ex) {
            Logger.getLogger(ZmienMojeHasloPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
    }

    public String zapiszZmianeMojegoHaslaAction() {
        if (powtorzNoweHaslo.equals(kontoDTO.getHaslo())) {
            try {
                rejestracjaKontaKontrolerBean.zmienMojeHaslo(kontoDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ZmienMojeHasloPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("RegisterForm:passwordRepeat", "konto.utworz.blad");
        }
        return "main";
    }

    public String wyglogujAkcja() {
        ContextUtils.invalidateSession();
        return "cancelAction";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }
}
