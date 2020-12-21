package pl.lodz.p.it.spjava.TSS.web.koncert;

import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KoncertEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "rejestracjaKoncertuPageBean")
@RequestScoped
public class RejestracjaKoncertuPageBean implements Serializable {

    @EJB
    private KoncertEndpoint koncertEndpoint;
    @Inject
    private KoncertControllerBean koncertControllerBean;

    private KoncertDTO koncertDTO;
    private String wykonawca;

    public RejestracjaKoncertuPageBean() {
    }

    public KoncertDTO getKoncertDTO() {
        return koncertDTO;
    }

    public void setKoncertDTO(KoncertDTO koncertDTO) {
        this.koncertDTO = koncertDTO;
    }

    public String getWykonawca() {
        return wykonawca;
    }

    public void setWykonawca(String wykonawca) {
        this.wykonawca = wykonawca;
    }

    @PostConstruct
    public void init() {
        koncertDTO = new KoncertDTO();
    }

    public String rejestracjaKoncertuAkcja() {
        if (koncertDTO.getCena() > 0 && koncertDTO.getPulaBiletow() > 0) {
            try {
                koncertControllerBean.rejestrujKoncert(koncertDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(RejestracjaKoncertuPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
        } else {
            ContextUtils.emitI18NMessage("rejestracjaKoncertu:wrongC", "blad.koncert.rejestracja.cena");
            return null;
        }
        return "stronaGlowna";
    }
}
