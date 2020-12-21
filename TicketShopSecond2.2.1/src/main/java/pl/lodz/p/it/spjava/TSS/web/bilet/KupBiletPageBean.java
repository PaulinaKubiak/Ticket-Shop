package pl.lodz.p.it.spjava.TSS.web.bilet;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "kupBiletPageBean")
@RequestScoped
public class KupBiletPageBean {

    @Inject
    private BiletControllerPageBean biletControlerPageBean;

    public KoncertDTO getKoncertDTO() {
        return biletControlerPageBean.getSelectedKoncertDTO();
    }

    public String kupBiletAction(final int ilosc) throws AppBaseException {
        try {
            biletControlerPageBean.rejestrujBilet(ilosc);
        } catch (AppBaseException ex) {
            Logger.getLogger(KupBiletPageBean.class.getName()).log(Level.SEVERE, null, ex);
            ContextUtils.emitI18NMessage(null, ex.getMessage());
            ContextUtils.emitI18NMessage(null, biletControlerPageBean.iloscWolnych().toString());
            return null;
        }
        return "listaBiletowKlient";
    }
}
