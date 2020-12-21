package pl.lodz.p.it.spjava.TSS.web.bilet;

import java.io.Serializable;
import javax.ejb.EJB;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import pl.lodz.p.it.spjava.TSS.DTO.BiletDTO;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.ejb.endpoint.BiletEndpoint;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;

@Named(value = "biletController")
@SessionScoped
public class BiletControllerPageBean implements Serializable {

    @EJB
    private BiletEndpoint biletEndpoint;
    private int lastActionMethod = 0;
    private BiletDTO selectedBiletDTO;
    private KoncertDTO selectedKoncertDTO;

    public BiletControllerPageBean() {
    }

    public BiletDTO getSelectedBiletDTO() {
        return selectedBiletDTO;
    }

    public void setSelectedBiletDTO(BiletDTO selectedBiletDTO) {
        this.selectedBiletDTO = selectedBiletDTO;
    }

    public KoncertDTO getSelectedKoncertDTO() {
        return selectedKoncertDTO;
    }

    public void setSelectedKoncertDTO(KoncertDTO selectedKoncertDTO) {
        this.selectedKoncertDTO = selectedKoncertDTO;
    }

    public void rejestrujBilet(final int ilosc) throws AppBaseException {

        int endpointCallCounter = biletEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
        do {
            biletEndpoint.rejestracjaBiletu(selectedKoncertDTO, ilosc);
            endpointCallCounter--;
        } while (biletEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
        if (endpointCallCounter == 0) {
            throw new IllegalStateException();
        }

    }

    public void zwrocBilet(final BiletDTO biletDTO) throws AppBaseException {
        final int UNIQ_METHOD_ID = biletDTO.hashCode() + 2;
        if (lastActionMethod != UNIQ_METHOD_ID) {
            int endpointCallCounter = biletEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
            do {
                biletEndpoint.zwrocBilet(biletDTO);
                endpointCallCounter--;
            } while (biletEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
            if (endpointCallCounter == 0) {
                throw new IllegalStateException();
            }
        }
        lastActionMethod = UNIQ_METHOD_ID;
    }

    public Long iloscWolnych() throws AppBaseException {
        Long iloscWolnych;
        int endpointCallCounter = biletEndpoint.NB_ATEMPTS_FOR_METHOD_INVOCATION;
        do {
            iloscWolnych = biletEndpoint.iloscWolnych(selectedKoncertDTO);
            endpointCallCounter--;
        } while (biletEndpoint.isLastTransactionRollback() && endpointCallCounter > 0);
        if (endpointCallCounter == 0) {
            throw new IllegalStateException();
        }
        return iloscWolnych;
    }

}
