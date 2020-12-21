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
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.model.DataModel;
import javax.faces.model.ListDataModel;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KontoEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.model.PoziomDostepu;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "listaKontPoAutoryzacji")
@ViewScoped
public class ListaKontPoAutoryzacjiPageBean implements Serializable {

    @EJB
    private KontoEndpoint kontoEndpoint;
    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private List<KontoDTO> listaKont;
    private List<PoziomDostepu> listaPoziomowDostepu;

    public List<PoziomDostepu> getListaPoziomowDostepu() {
        return listaPoziomowDostepu;
    }
    private DataModel<KontoDTO> dataModelKonto;

    public ListaKontPoAutoryzacjiPageBean() {
    }

    public DataModel<KontoDTO> getDataModelKonto() {
        return dataModelKonto;
    }

    @PostConstruct
    public void initListaKontPoAutoryzacji() {
        ExternalContext externalCntx = FacesContext.getCurrentInstance().getExternalContext();
        try {
            listaKont = kontoEndpoint.listaKontPoAutoryzacji();
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
        }
        dataModelKonto = new ListDataModel<>(listaKont);

        PoziomDostepu[] listaWszystkichPoziomowDostepu = PoziomDostepu.values();

        for (PoziomDostepu poziomDostepu : listaWszystkichPoziomowDostepu) {
            poziomDostepu.setAccessLevelI18NValue(ContextUtils.getI18NMessage(poziomDostepu.getAccessLevelKey()));
        }

        listaPoziomowDostepu = new ArrayList<>(Arrays.asList(listaWszystkichPoziomowDostepu));
        listaPoziomowDostepu.remove(PoziomDostepu.KONTO);  //usuwa z list poziom dostępu KONTO aby nie można było go wybrać z listy rozwijanej poziomów dostępu
        listaPoziomowDostepu.remove(PoziomDostepu.NOWEKONTO);  //usuwa z list poziom dostępu NOWEKONTO aby nie można było go wybrać z listy rozwijanej poziomów dostępu
    }

    public String aktywujKontoAction(KontoDTO kontoDTO) {
        try {
            rejestracjaKontaControllerBean.aktywujKonto(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListaKontPoAutoryzacji();
        return null;
    }

    public String dezaktywujKontoAction(KontoDTO kontoDTO) {
        try {
            rejestracjaKontaControllerBean.dezaktywujKonto(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        initListaKontPoAutoryzacji();
        return null;
    }

    public String zmienHasloAction(KontoDTO kontoDTO) {
        try {
            rejestracjaKontaControllerBean.wybierzKontoDoZmiany(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "zmienHaslo";
    }

    public String edytujKontoAction(KontoDTO kontoDTO) {
        try {
            rejestracjaKontaControllerBean.wybierzKontoDoZmiany(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "edytujKonto";
    }

    public String zmienPoziomDostepuAction(KontoDTO kontoDTO) {
        if (kontoDTO.getPoziomDostepu() != null) {
            try {
                rejestracjaKontaControllerBean.zmienPoziomDostepuKonta(kontoDTO);
            } catch (AppBaseException ex) {
                Logger.getLogger(ListaKontPoAutoryzacjiPageBean.class.getName()).log(Level.SEVERE, null, ex);
                ContextUtils.emitI18NMessage(null, ex.getMessage());
                return null;
            }
            initListaKontPoAutoryzacji();
        }
        return null;
    }
}
