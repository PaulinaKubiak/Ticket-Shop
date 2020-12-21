package pl.lodz.p.it.spjava.TSS.web.konto;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.KontoEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "edycjaKontaPageBean")
@RequestScoped
public class EdycjaKontaPageBean {

    @EJB
    private KontoEndpoint kontoEndpoint;

    @Inject
    private RejestracjaKontaControllerBean rejestracjaKontaControllerBean;

    private KontoDTO kontoDTO;

    private String imie;
    private String nazwisko;
    private String email;
    private String login;

    public EdycjaKontaPageBean() {
    }

    public KontoDTO getKontoDTO() {
        return kontoDTO;
    }

    public void setKontoDTO(KontoDTO kontoDTO) {
        this.kontoDTO = kontoDTO;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    @PostConstruct
    private void init() {
        kontoDTO = rejestracjaKontaControllerBean.getSelectedKontoDTO();
    }

    public String zapiszEdycjeKontaAction() {
        try {
            rejestracjaKontaControllerBean.edytujKonto(kontoDTO);
        } catch (AppBaseException ex) {
            Logger.getLogger(EdycjaKontaPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            return null;
        }
        return "listaKontAktywnych";
    }
}
