package pl.lodz.p.it.spjava.TSS.web.bilet;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.TSS.DTO.BiletDTO;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.BiletEndpoint;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KoncertEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKupionychBiletowNowa")
@ViewScoped
public class ListaKupionychBiletowNowaPageBean implements Serializable {

    @EJB
    private BiletEndpoint biletEndpoint;

    @EJB
    private KoncertEndpoint koncertEndpoint;

    @Inject
    private BiletControllerPageBean biletControllerPageBean;

    private List<BiletDTO> listaBiletow;
    private DataModel<BiletDTO> dataModelBilet;

    private List<KoncertDTO> listaKoncertow;
    private DataModel<KoncertDTO> dataModelKoncert;

    private BiletDTO biletDTO;
    private KoncertDTO koncertDTO;

    public ListaKupionychBiletowNowaPageBean() {
    }

    public DataModel<BiletDTO> getDataModelBilet() {
        return dataModelBilet;
    }

    public BiletDTO getBiletDTO() {
        return biletDTO;
    }

    public void setBiletDTO(BiletDTO biletDTO) {
        this.biletDTO = biletDTO;
    }

    public KoncertDTO getKoncertDTO() {
        return koncertDTO;
    }

    public void setKoncertDTO(KoncertDTO koncertDTO) {
        this.koncertDTO = koncertDTO;
    }

    public List<KoncertDTO> getListaKoncertow() {
        return listaKoncertow;
    }

    public void setListaKoncertow(List<KoncertDTO> listaKoncertow) {
        this.listaKoncertow = listaKoncertow;
    }

    // lista rozwijanych koncertów (po wykonawcy?)
    @PostConstruct
    public void initListaKoncertow() {
        try {
            listaKoncertow = koncertEndpoint.listaKoncertow();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKupionychBiletowNowaPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelKoncert = new ListDataModel<>(listaKoncertow);
        biletDTO = new BiletDTO();
    }

    // lista stanów dla koncertów 
    public String pokazListeAction() {
        try {
            listaBiletow = biletEndpoint.listaBiletowNowa(biletDTO.getWykonawca());
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKupionychBiletowNowaPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            dataModelBilet = null; // Usunięcie danych z tabeli w przypadku braku stanu
        }
        dataModelBilet = new ListDataModel<>(listaBiletow);
        return null;
    }

}
