package pl.lodz.p.it.spjava.TSS.web.konto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
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
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KontoEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.model.PoziomDostepu;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKontPageBean")
@ViewScoped
public class ListaKontPageBean implements Serializable {

    @EJB
    private KontoEndpoint kontoEndpoint;

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private DataModel<KontoDTO> dataModelAccounts;
    private List<PoziomDostepu> listaPoziomowDostepu;
    private List<KontoDTO> listaKont;

    public List<PoziomDostepu> getListaPoziomowDostepu() {
        return listaPoziomowDostepu;
    }

    public ListaKontPageBean() {
    }

    public DataModel<KontoDTO> getDataModelAccounts() {
        return dataModelAccounts;
    }

    public List<KontoDTO> getListaKont() {
        return listaKont;
    }

    public void setListaKont(List<KontoDTO> listaKont) {
        this.listaKont = listaKont;
    }

    @PostConstruct
    public void initListaNowychKont() {
        try {
            listaKont = kontoEndpoint.listaNowychKont();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelAccounts = new ListDataModel<>(listaKont);
        PoziomDostepu[] listAllAccessLevels = PoziomDostepu.values();
        for (PoziomDostepu poziomDostepu : listAllAccessLevels) {
            poziomDostepu.setAccessLevelI18NValue(ContextUtils.getI18NMessage(poziomDostepu.getAccessLevelKey()));
        }
        listaPoziomowDostepu = new ArrayList<>(Arrays.asList(listAllAccessLevels));
        listaPoziomowDostepu.remove(PoziomDostepu.KONTO);  //usuwa z listy poziom dostępu ACCOUNT aby nie można było go wybrać z listy rozwijanej poziomów dostępu    }
        listaPoziomowDostepu.remove(PoziomDostepu.NOWEKONTO);  //usuwa z listy poziom dostępu NOWEKONTO aby nie można było go wybrać z listy rozwijanej poziomów dostępu    }
    }

    public String usunWybraneKontoAction(KontoDTO kontoDTO) throws AppBaseException {
        try {
            rejestracjaKontaControllerBean.wybierzKontoDoZmiany(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListaNowychKont();
        return "usunKonto";
    }

    public String zmienPoziomDostepuKontoAction(KontoDTO kontoDTO) {
        if (kontoDTO.getPoziomDostepu() != null) {
            try {
                rejestracjaKontaControllerBean.ustawPoziomDostepu(kontoDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ListaKontPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
            }
            initListaNowychKont();
        }
        return null;
    }
}
