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
import pl.lodz.p.it.spjava.TSS.web.koncert.UsunKoncertPageBean;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKupionychBiletowKlient")
@RequestScoped
public class ListaKupionychBiletowKlientPageBean {

    @EJB
    private BiletEndpoint biletEndpoint;

    @Inject
    private BiletControllerPageBean biletControllerPageBean;

    private DataModel<BiletDTO> dataModelBilet;
    private List<BiletDTO> listaBiletow;
    private BiletDTO biletDTO;

    public ListaKupionychBiletowKlientPageBean() {
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
    public void initListaBiletowKlient() {
        try {
            listaBiletow = biletEndpoint.listaBiletowKlient();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKupionychBiletowKlientPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelBilet = new ListDataModel<>(listaBiletow);
    }

    public String zwrocBilet(BiletDTO biletDTO) throws AppBaseException {
        try {
            biletControllerPageBean.zwrocBilet(biletDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(UsunKoncertPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listaBiletowKlient";
    }
}
