package pl.lodz.p.it.spjava.TSS.web.koncert;

import java.util.Date;
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

@Named(value = "edytujKoncertPageBean")
@RequestScoped
public class EdycjaKoncertuPageBean {

    @EJB
    private KoncertEndpoint koncertEndpoint;

    @Inject
    private KoncertControllerBean koncertControllerBean;

    private KoncertDTO koncertDTO;

    private Date data;
    private Date godzina;
    private String miejsce;
    private int pulaBiletow;

    public EdycjaKoncertuPageBean() {
    }

    public KoncertEndpoint getKoncertEndpoint() {
        return koncertEndpoint;
    }

    public void setKoncertEndpoint(KoncertEndpoint koncertEndpoint) {
        this.koncertEndpoint = koncertEndpoint;
    }

    public KoncertControllerBean getKoncertControllerBean() {
        return koncertControllerBean;
    }

    public void setKoncertControllerBean(KoncertControllerBean koncertControllerBean) {
        this.koncertControllerBean = koncertControllerBean;
    }

    public KoncertDTO getKoncertDTO() {
        return koncertDTO;
    }

    public void setKoncertDTO(KoncertDTO koncertDTO) {
        this.koncertDTO = koncertDTO;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Date getGodzina() {
        return godzina;
    }

    public void setGodzina(Date godzina) {
        this.godzina = godzina;
    }

    public String getMiejsce() {
        return miejsce;
    }

    public void setMiejsce(String miejsce) {
        this.miejsce = miejsce;
    }

    public int getPulaBiletow() {
        return pulaBiletow;
    }

    public void setPulaBiletow(int pulaBiletow) {
        this.pulaBiletow = pulaBiletow;
    }

    @Override
    public String toString() {
        return "EdycjaKoncertuPageBean{" + "data=" + data + '}';
    }

    @PostConstruct
    private void init() {
        koncertDTO = koncertControllerBean.getSelectedKoncertDTO();
    }

    public String zapiszEdycjeKoncertuAction() {
        try {
            koncertControllerBean.edytujKoncert(koncertDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(EdycjaKoncertuPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listaKoncertow";
    }
}
