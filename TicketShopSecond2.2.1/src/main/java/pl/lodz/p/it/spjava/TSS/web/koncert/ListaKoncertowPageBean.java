package pl.lodz.p.it.spjava.TSS.web.koncert;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KoncertEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.bilet.BiletControllerPageBean;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKoncertowPageBean")
@ViewScoped
public class ListaKoncertowPageBean implements Serializable {

    @EJB
    private KoncertEndpoint koncertEndpoint;
    @Inject
    private KoncertControllerBean koncertControllerBean;
    @Inject
    private BiletControllerPageBean biletControllerBean;

    private DataModel<KoncertDTO> dataModelKoncert;
    private List<KoncertDTO> listaKoncertow;

    public ListaKoncertowPageBean() {
    }

    public DataModel<KoncertDTO> getDataModelKoncert() {
        return dataModelKoncert;
    }

    @PostConstruct
    public void initListKoncerty() {
        try {
            listaKoncertow = koncertEndpoint.listaKoncertow();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKoncertowPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelKoncert = new ListDataModel<>(listaKoncertow);
    }

    public String usunWybranyKoncertAction(KoncertDTO koncertDTO) {
        try {
            koncertControllerBean.wybierzKoncertDoZmiany(koncertDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKoncertowPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        initListKoncerty();
        return "usunKoncert";
    }

    public String edytujKoncertAction(KoncertDTO koncertDTO) throws AppBaseException {
        try {
            koncertControllerBean.wybierzKoncertDoZmiany(koncertDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKoncertowPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        return "edytujKoncert";
    }

    public String kupBiletAction(KoncertDTO koncertDTO) throws AppBaseException {
        biletControllerBean.setSelectedKoncertDTO(koncertDTO);
        return "kupBilet";
    }
}
