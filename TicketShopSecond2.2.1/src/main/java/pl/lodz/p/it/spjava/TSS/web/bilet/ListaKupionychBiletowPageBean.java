package pl.lodz.p.it.spjava.TSS.web.bilet;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.inject.Inject;
import javax.inject.Named;
import pl.lodz.p.it.spjava.TSS.DTO.BiletDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.BiletEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKupionychBiletow")
@RequestScoped
public class ListaKupionychBiletowPageBean {

    @EJB
    private BiletEndpoint biletEndpoint;

    @Inject
    private BiletControllerPageBean biletControllerPageBean;

    private DataModel<BiletDTO> dataModelBilet;
    private List<BiletDTO> listaBiletow;
    private BiletDTO biletDTO;

    public ListaKupionychBiletowPageBean() {
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

    @PostConstruct
    public void initListaBiletow() {
        try {
            listaBiletow = biletEndpoint.listaBiletow();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKupionychBiletowPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelBilet = new ListDataModel<>(listaBiletow);
    }

    public String zwrocBilet(BiletDTO biletDTO) {
        return null;
    }
}
