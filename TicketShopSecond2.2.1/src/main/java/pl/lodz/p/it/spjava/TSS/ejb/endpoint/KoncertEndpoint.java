package pl.lodz.p.it.spjava.TSS.ejb.endpoint;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.TSS.DTO.KoncertDTO;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.AdministratorFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.BiletFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.KoncertFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.PracownikFacade;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.exception.KoncertException;
import pl.lodz.p.it.spjava.TSS.model.Koncert;
import pl.lodz.p.it.spjava.TSS.model.Pracownik;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
public class KoncertEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private KoncertFacade koncertFacade;

    @EJB
    private AdministratorFacade administratorFacade;

    @EJB
    private PracownikFacade pracownikFacade;
    @EJB
    private BiletFacade biletFacade;

    @Resource
    private SessionContext sessionContext;

    private Koncert koncertStan;

    private Pracownik ladujBiezacegoPracownika() throws AppBaseException {
        String pracownikLogin = sessionContext.getCallerPrincipal().getName();
        Pracownik pracownik = pracownikFacade.findByLogin(pracownikLogin);
        if (pracownik != null) {
            return pracownik;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    @RolesAllowed({"Pracownik"})
    public void utworzKoncert(KoncertDTO koncertDTO) throws AppBaseException {
        Koncert koncert = new Koncert();
        koncert.setWykonawca(koncertDTO.getWykonawca());
        koncert.setData(koncertDTO.getData());
        koncert.setMiejsce(koncertDTO.getMiejsce());
        koncert.setPulaBiletow(koncertDTO.getPulaBiletow());
        koncert.setCena(koncertDTO.getCena());
        koncert.setWprowadzKoncert(ladujBiezacegoPracownika());
        koncertFacade.create(koncert);
    }

    @PermitAll
    public List<KoncertDTO> listaKoncertow() throws AppBaseException {
        List<Koncert> listaWszystkichKoncertow = koncertFacade.findAll(); // Tworzymy listę obiektów klasy encyjnej i przypisujemy im wartość, którą, za pomocą wstrzykniętego obiektu fasady i wykonanej na nim metody fasady, wyszukujemy w bazie
        List<KoncertDTO> listaKoncertow = new ArrayList<>(); // Tworzymy pustą listę DTO i przepisujemy wartości z listy obiektów klasy encyjnej dla każdego obiektu:
        for (Koncert koncert : listaWszystkichKoncertow) {
            KoncertDTO koncertDTO = new KoncertDTO(koncert.getId(), koncert.getWykonawca(), koncert.getData(), koncert.getMiejsce(), koncert.getPulaBiletow(), koncert.getCena()); // tworzymy pusty obiekt accountDTO i przypisujemy mu wartości pól pobrane getterami tych encji, które wyświetlane będą w tabeli/na liście
            koncertDTO.setWyprzedane(biletFacade.pobierzIloscSprzedanychBiletow(koncertDTO.getId()) >= koncertDTO.getPulaBiletow());
            listaKoncertow.add(koncertDTO); // dodajemy obiekt, do którego przepisane zostały dane z bazy do utworzonej pustej ArrayListy
        }
        return listaKoncertow;
    }

    @PermitAll
    public List<KoncertDTO> listaWykonawcow(String wykonawca) throws AppBaseException {
        List<Koncert> listaWszystkichWykonawcow = koncertFacade.znajdzWykonawce(wykonawca); // Tworzymy listę obiektów klasy encyjnej i przypisujemy im wartość, którą, za pomocą wstrzykniętego obiektu fasady i wykonanej na nim metody fasady, wyszukujemy w bazie
        List<KoncertDTO> listaWykonawcow = new ArrayList<>(); // Tworzymy pustą listę DTO i przepisujemy wartości z listy obiektów klasy encyjnej dla każdego obiektu:
        for (Koncert koncert : listaWszystkichWykonawcow) {
            KoncertDTO koncertDTO = new KoncertDTO(koncert.getId(), koncert.getWykonawca(), koncert.getData(), koncert.getMiejsce(), koncert.getPulaBiletow(), koncert.getCena()); // tworzymy pusty obiekt accountDTO i przypisujemy mu wartości pól pobrane getterami tych encji, które wyświetlane będą w tabeli/na liście
            listaWykonawcow.add(koncertDTO); // dodajemy obiekt, do którego przepisane zostały dane z bazy do utworzonej pustej ArrayListy
        }
        return listaWykonawcow;
    }

    @RolesAllowed({"Pracownik"})
    public void usunKoncert(KoncertDTO koncertDTO) throws AppBaseException {
        Koncert koncert = koncertFacade.find(koncertDTO.getId());
        long iloscSprzedanych = biletFacade.pobierzIloscSprzedanychBiletow(koncert.getId());
        if (iloscSprzedanych > 0) {
            throw KoncertException.createExceptionDeleteWhenExistcTicket(koncertStan);
        }
        koncertFacade.remove(koncertStan);
    }

    @RolesAllowed({"Pracownik"})
    public void edytujKoncert(KoncertDTO koncertDTO) throws AppBaseException {
        if (koncertStan.getId().equals(koncertDTO.getId())) {
            koncertStan.setData(koncertDTO.getData());
            koncertStan.setMiejsce(koncertDTO.getMiejsce());
            koncertStan.setPulaBiletow(koncertDTO.getPulaBiletow());
            koncertStan.setWprowadzKoncert(ladujBiezacegoPracownika());
            koncertStan.setModyfikowanePrzez(ladujBiezacegoPracownika());
            koncertFacade.edit(koncertStan);
        } else {
            throw KoncertException.createExceptionWrongState(koncertStan);
        }
    }

    public KoncertDTO zapamietajWybraneKontoWStanie(KoncertDTO koncertDTO) throws AppBaseException {
        koncertStan = koncertFacade.find(koncertDTO.getId());
        return new KoncertDTO(koncertStan.getId(), koncertStan.getWykonawca(), koncertStan.getData(), koncertStan.getMiejsce(), koncertStan.getPulaBiletow(), koncertStan.getCena());
    }

    public KoncertDTO wybierzKoncertDoZmianyZakupBiletu(KoncertDTO koncertDTO) throws AppBaseException {
        koncertStan = koncertFacade.find(koncertDTO.getId());
        return new KoncertDTO(koncertStan.getWykonawca(), koncertStan.getData(), koncertStan.getMiejsce(), koncertStan.getCena());
    }
}
