package pl.lodz.p.it.spjava.TSS.web.koncert;

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

@Named(value = "usunKoncertPageBean")
@RequestScoped
public class UsunKoncertPageBean {

    @EJB
    private KoncertEndpoint koncertEndpoint;

    @Inject
    private KoncertControllerBean koncertControllerBean;

    private KoncertDTO koncertDTO;

    public UsunKoncertPageBean() {
    }

    public KoncertDTO getKoncertDTO() {
        return koncertDTO;
    }

    public void setKoncertDTO(KoncertDTO koncertDTO) {
        this.koncertDTO = koncertDTO;
    }

    @PostConstruct
    private void init() {
        koncertDTO = koncertControllerBean.getSelectedKoncertDTO();
    }

    public String usunKoncertAction() {
        try {
            koncertControllerBean.usunKoncert();
        } catch (AppBaseException ex) {
            Logger.getLogger(UsunKoncertPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listaKoncertow";
    }
}
