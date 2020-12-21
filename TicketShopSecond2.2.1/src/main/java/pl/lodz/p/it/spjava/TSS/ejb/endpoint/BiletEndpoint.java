package pl.lodz.p.it.spjava.TSS.ejb.endpoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.TSS.DTO.BiletDTO;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.BiletFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.KlientFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.KoncertFacade;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.exception.BiletException;
import pl.lodz.p.it.spjava.TSS.model.Bilet;
import pl.lodz.p.it.spjava.TSS.model.Klient;
import pl.lodz.p.it.spjava.TSS.model.Koncert;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class BiletEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private KlientFacade klientFacade;

    @EJB
    private KoncertFacade koncertFacade;

    @EJB
    private BiletFacade biletFacade;

    @Resource
    private SessionContext sessionContext;

    private Bilet biletStan;

    private Klient ladujBiezacegoKlienta() throws AppBaseException {
        String klientLogin = sessionContext.getCallerPrincipal().getName();
        Klient klient = klientFacade.znajdzKlienta(klientLogin);
        if (klient != null) {
            return klient;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    @RolesAllowed({"Klient"})
    public void rejestracjaBiletu(KoncertDTO koncertDTO, int ilosc) throws AppBaseException {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.HOUR, 24);
        if (koncertDTO.getData().after(c.getTime())) {
            Long iloscSprzedanych = biletFacade.pobierzIloscSprzedanychBiletow(koncertDTO.getId());
            Long iloscWolnych = koncertDTO.getPulaBiletow() - biletFacade.pobierzIloscSprzedanychBiletow(koncertDTO.getId());
            if (ilosc > 0) {
                if (iloscSprzedanych + ilosc <= koncertDTO.getPulaBiletow()) {
                    Klient klient = (klientFacade.znajdzKlienta(sessionContext.getCallerPrincipal().getName()));
                    Koncert koncert = (koncertFacade.find(koncertDTO.getId()));
                    for (int i = 0; i < ilosc; i++) {
                        Bilet bilet = new Bilet();
                        bilet.setKlient(klient);
                        bilet.setKoncert(koncert); //tu moze byc wyjatek, jesli ktos usunie koncert?!
                        biletFacade.create(bilet);
                    }
                } else {
                    throw BiletException.createExceptionSellOut(biletStan);
                }
            } else {
                throw BiletException.createNegativValue(biletStan);
            }
        } else {
            throw BiletException.createExceptionDay(biletStan);
        }
    }

    @RolesAllowed({"Klient"})
    public Long iloscWolnych(KoncertDTO koncertDTO) throws AppBaseException {
        return koncertDTO.getPulaBiletow() - biletFacade.pobierzIloscSprzedanychBiletow(koncertDTO.getId());
    }

    @RolesAllowed({"Pracownik"})
    public List<BiletDTO> listaBiletow() throws AppBaseException {
        List<Bilet> listaBiletow = biletFacade.findAll();
        List<BiletDTO> listaWszystkichBiletow = new ArrayList<>();
        for (Bilet bilet : listaBiletow) {
            KontoDTO kontoDTO = new KontoDTO(bilet.getKlient().getLogin());
            KoncertDTO koncertDTO = new KoncertDTO(bilet.getKoncert().getWykonawca(), bilet.getKoncert().getData(), bilet.getKoncert().getMiejsce(), bilet.getKoncert().getCena());
            BiletDTO biletDTO = new BiletDTO(bilet.getId(), kontoDTO, koncertDTO);
            listaWszystkichBiletow.add(biletDTO);

        }
        return listaWszystkichBiletow;
    }

    @RolesAllowed({"Pracownik"})
    public List<BiletDTO> listaBiletowNowa(String wykonawca) throws AppBaseException {
        List<Bilet> listaBiletowPoWykonawcy = biletFacade.znajdzWykonawce(wykonawca);
        if (listaBiletowPoWykonawcy.isEmpty() == false) {
            List<BiletDTO> listaWszystkichBiletow = new ArrayList<>();
            for (int lp = 0; lp < listaBiletowPoWykonawcy.size(); lp++) {
                Bilet bilet = listaBiletowPoWykonawcy.get(lp);
//            for (Bilet bilet : listaBiletowPoWykonawcy) {
                //        for (int i = 0; i < lp; i++) {
                KontoDTO kontoDTO = new KontoDTO(bilet.getKlient().getLogin());
                KoncertDTO koncertDTO = new KoncertDTO(bilet.getKoncert().getWykonawca(), bilet.getKoncert().getData(), bilet.getKoncert().getMiejsce(), bilet.getKoncert().getCena());
                BiletDTO biletDTO = new BiletDTO(bilet.getId(), kontoDTO, koncertDTO, lp + 1);
                listaWszystkichBiletow.add(biletDTO);
//        }
            }
            return listaWszystkichBiletow;
        } else {
            throw BiletException.createNotSold(biletStan);
        }
    }

    @RolesAllowed({"Klient"})
    public List<BiletDTO> listaBiletowKlient() throws AppBaseException {
        List<Bilet> listaBiletowKlient = biletFacade.pobierzListeBiletowKlient(ladujBiezacegoKlienta());
        List<BiletDTO> listaWszystkichBiletowKlient = new ArrayList<>();
        for (Bilet bilet : listaBiletowKlient) {
            KoncertDTO koncertDTO = new KoncertDTO(bilet.getKoncert().getWykonawca(), bilet.getKoncert().getData(), bilet.getKoncert().getMiejsce(), bilet.getKoncert().getCena());
            BiletDTO biletDTO = new BiletDTO(bilet.getId(), koncertDTO);
            listaWszystkichBiletowKlient.add(biletDTO);
        }
        return listaWszystkichBiletowKlient;
    }

    @RolesAllowed({"Klient"})
    public void zwrocBilet(BiletDTO biletDTO) throws AppBaseException {
        Bilet bilet = biletFacade.find(biletDTO.getId());
        biletFacade.remove(bilet);
    }
}
