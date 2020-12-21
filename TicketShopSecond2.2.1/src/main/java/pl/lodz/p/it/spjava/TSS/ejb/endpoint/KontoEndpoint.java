package pl.lodz.p.it.spjava.TSS.ejb.endpoint;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.EJBTransactionRolledbackException;
import javax.ejb.SessionContext;
import javax.ejb.SessionSynchronization;
import javax.ejb.Stateful;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.interceptor.Interceptors;
import pl.lodz.p.it.spjava.TSS.DTO.KontoDTO;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.AdministratorFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.KlientFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.KontoFacade;
import pl.lodz.p.it.spjava.TSS.ejb.Facade.PracownikFacade;
import pl.lodz.p.it.spjava.TSS.ejb.interceptor.LoggingInterceptor;
import pl.lodz.p.it.spjava.TSS.exception.AppBaseException;
import pl.lodz.p.it.spjava.TSS.exception.KontoException;
import pl.lodz.p.it.spjava.TSS.model.Administrator;
import pl.lodz.p.it.spjava.TSS.model.Klient;
import pl.lodz.p.it.spjava.TSS.model.Konto;
import pl.lodz.p.it.spjava.TSS.model.NoweKonto;
import pl.lodz.p.it.spjava.TSS.model.PoziomDostepu;
import pl.lodz.p.it.spjava.TSS.model.Pracownik;

@Stateful
@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
@Interceptors(LoggingInterceptor.class)
@RolesAllowed("Administrator")
public class KontoEndpoint extends AbstractEndpoint implements SessionSynchronization {

    @EJB
    private KlientFacade klientFacade;

    @EJB
    private PracownikFacade pracownikFacade;

    @EJB
    private AdministratorFacade administratorFacade;

    @Inject
    private KontoFacade kontoFacade;

    @Resource
    private SessionContext sessionContext;

    private Konto kontoStan;

    private Administrator ladujBiezacegoAdmin() throws AppBaseException {
        String adminLogin = sessionContext.getCallerPrincipal().getName();
        Administrator administrator = administratorFacade.findByLogin(adminLogin);
        if (administrator != null) {
            return administrator;
        } else {
            throw AppBaseException.createExceptionNotAuthorizedAction();
        }
    }

    @PermitAll
    public void rejestracjaKonta(KontoDTO kontoDTO) throws AppBaseException {
        Konto konto = new NoweKonto();
        konto.setImie(kontoDTO.getImie());
        konto.setNazwisko(kontoDTO.getNazwisko());
        konto.setEmail(kontoDTO.getEmail());
        konto.setLogin(kontoDTO.getLogin());
        konto.setHaslo(kontoDTO.getHaslo());
        konto.setPytaniekontrolne(kontoDTO.getPytanie());
        konto.setOdpowiedzkontrolna(kontoDTO.getOdpowiedz());
        konto.setAktywne(false);
        konto.setAutoryzowane(false);
        kontoFacade.create(konto);
    }

    public List<KontoDTO> listaNowychKont() throws AppBaseException {
        List<Konto> listaNowychKont = kontoFacade.znajdzAktywneKonto(); // Tworzymy listę obiektów klasy encyjnej i przypisujemy im wartość, którą, za pomocą wstrzykniętego obiektu fasady i wykonanej na nim metody fasady, wyszukujemy w bazie
        List<KontoDTO> listaNowychZarejestrowanychKont = new ArrayList<>(); // Tworzymy pustą listę DTO i przepisujemy wartości z listy obiektów klasy encyjnej dla każdego obiektu:
        for (Konto konto : listaNowychKont) {
            KontoDTO kontoDTO = new KontoDTO(konto.getLogin(), konto.getImie(), konto.getNazwisko(), konto.getEmail()); // tworzymy pusty obiekt accountDTO i przypisujemy mu wartości pól pobrane getterami tych encji, które wyświetlane będą w tabeli/na liście
            listaNowychZarejestrowanychKont.add(kontoDTO); // dodajemy obiekt, do którego przepisane zostały dane z bazy do utworzonej pustej ArrayListy
        }
        return listaNowychZarejestrowanychKont;
    }

    public void usunKonto(KontoDTO kontoDTO) throws AppBaseException {
        Konto konto = kontoFacade.znajdzPoLoginie(kontoDTO.getLogin());
        if (konto.isAktywne() == false) {
            kontoFacade.remove(konto);
        } else {
            throw KontoException.createExceptionDeleted(konto);
        }
    }

    public void ustawPoziomDostepu(KontoDTO kontoDTO) throws AppBaseException {
        // ustaw poziom dostępu
        Konto noweKontoZarejestrowane = kontoFacade.znajdzPoLoginie(kontoDTO.getLogin());
        Konto konto = null;
        switch (kontoDTO.getPoziomDostepu()) { // robimy switcha do listy rozwijanej jako wartości przyjmującego wartości enum
            case PRACOWNIK:
                konto = new Pracownik(noweKontoZarejestrowane);
                break;
            case ADMINISTRATOR:
                konto = new Administrator(noweKontoZarejestrowane);
                break;
            case KLIENT:
                konto = new Klient(noweKontoZarejestrowane);
                break;
        }
        if (konto != null) { // jeśli któryś z poziomów w switchu został przypisany...
            kontoFacade.remove(noweKontoZarejestrowane); // … usuwamy istniejący poziom nie zarejestrowany
//                kontoFacade.flush(); // … usuwamy istniejący poziom nie zarejestrowany
            konto.setAktywne(true);
            konto.setAutoryzowane(true);
            konto.setModyfikowanePrzez(ladujBiezacegoAdmin());
            kontoFacade.create(konto);
        } else {
            throw KontoException.createExceptionAccountAlreadyAuthorized(konto);
        }
    }

    public List<KontoDTO> listaKontPoAutoryzacji() throws AppBaseException {
        List<Konto> listaKontPoAutoryzacji = kontoFacade.znajdzPoAutoryzowane();
        List<KontoDTO> listaKont = new ArrayList<>();
        for (Konto konto : listaKontPoAutoryzacji) {
            KontoDTO kontoDTO = new KontoDTO(konto.getLogin(), konto.getImie(), konto.getNazwisko(), konto.getEmail(), konto.isAktywne());

            Administrator administrator = administratorFacade.findByLogin(konto.getLogin());
            Pracownik pracownik = pracownikFacade.findByLogin(konto.getLogin());
            Klient klient = klientFacade.znajdzKlienta(konto.getLogin());

            if (administrator != null) {
                kontoDTO.setPoziomDostepu(PoziomDostepu.ADMINISTRATOR);
                kontoDTO.setStaryPoziomDostepu(PoziomDostepu.ADMINISTRATOR);
            } else if (pracownik != null) {
                kontoDTO.setPoziomDostepu(PoziomDostepu.PRACOWNIK);
                kontoDTO.setStaryPoziomDostepu(PoziomDostepu.PRACOWNIK);
            } else if (klient != null) {
                kontoDTO.setPoziomDostepu(PoziomDostepu.KLIENT);
                kontoDTO.setStaryPoziomDostepu(PoziomDostepu.KLIENT);
            }
            listaKont.add(kontoDTO);
        }
        return listaKont;
    }

    public void aktywujKonto(String login) throws AppBaseException {
        kontoStan = kontoFacade.znajdzPoLoginie(login);
        if (!kontoStan.isAktywne()) {
            kontoStan.setAktywne(true);
            kontoStan.setModyfikowanePrzez(ladujBiezacegoAdmin());
        } else {
            throw KontoException.createExceptionActvivated(kontoStan);
        }
    }

    public void dezaktywujKonto(String login) throws AppBaseException {
        kontoStan = kontoFacade.znajdzPoLoginie(login);
        if (kontoStan.isAktywne()) {
            kontoStan.setAktywne(false);
            kontoStan.setModyfikowanePrzez(ladujBiezacegoAdmin());
        } else {
            throw KontoException.createExceptionDeactvivated(kontoStan);
        }
    }

    @PermitAll
    public KontoDTO zapamietajWybraneKontoWStanie(String login) throws AppBaseException {
        kontoStan = kontoFacade.znajdzPoLoginie(login);
        return new KontoDTO(kontoStan.getLogin(), kontoStan.getHaslo(), kontoStan.getImie(), kontoStan.getNazwisko(), kontoStan.getEmail(), kontoStan.getPytaniekontrolne(), kontoStan.getOdpowiedzkontrolna(), kontoStan.isAktywne());
    }

    @RolesAllowed({"Administrator", "Pracownik", "Klient"})
    public KontoDTO zapamietajMojeKontoWStanie() throws AppBaseException {
        kontoStan = kontoFacade.znajdzPoLoginie(sessionContext.getCallerPrincipal().getName());
        return new KontoDTO(kontoStan.getLogin(), kontoStan.getHaslo(), kontoStan.getImie(), kontoStan.getNazwisko(), kontoStan.getEmail(), kontoStan.getPytaniekontrolne(), kontoStan.getOdpowiedzkontrolna(), kontoStan.isAktywne());
    }

    public void edytujKonto(KontoDTO kontoDTO) throws AppBaseException {
        if (kontoStan.getLogin().equals(kontoDTO.getLogin())) {
            kontoStan.setImie(kontoDTO.getImie());
            kontoStan.setNazwisko(kontoDTO.getNazwisko());
            kontoStan.setEmail(kontoDTO.getEmail());
            kontoStan.setModyfikowanePrzez(ladujBiezacegoAdmin());
            kontoFacade.edit(kontoStan);
        } else {
            throw KontoException.createExceptionWrongState(kontoStan);
        }
    }

    @RolesAllowed({"Administrator", "Pracownik", "Klient"})
    public void edytujMojeKonto(KontoDTO kontoDTO) throws AppBaseException {
        if (kontoStan.getLogin().equals(kontoDTO.getLogin())) {
            kontoStan.setImie(kontoDTO.getImie());
            kontoStan.setNazwisko(kontoDTO.getNazwisko());
            kontoStan.setEmail(kontoDTO.getEmail());
            kontoStan.setModyfikowanePrzez(kontoDTO.getPoziomDostepu() == PoziomDostepu.ADMINISTRATOR ? ladujBiezacegoAdmin() : null);
            kontoFacade.edit(kontoStan);
        } else {
            throw KontoException.createExceptionWrongState(kontoStan);
        }
    }

    public void zmienPoziomDostepu(KontoDTO kontoDTO) throws AppBaseException {
        switch (kontoDTO.getStaryPoziomDostepu()) {
            case ADMINISTRATOR:
                if (administratorFacade.findByLogin(kontoDTO.getLogin()) == null) {
                    throw KontoException.createExceptionAccountAlreadyAuthorized(kontoStan);
                }
                break;
            case PRACOWNIK:
                if (pracownikFacade.findByLogin(kontoDTO.getLogin()) == null) {
                    throw KontoException.createExceptionAccountAlreadyAuthorized(kontoStan);
                }
                break;
            case KLIENT:
                if (klientFacade.znajdzKlienta(kontoDTO.getLogin()) == null) {
                    throw KontoException.createExceptionAccountAlreadyAuthorized(kontoStan);
                }
                break;
        }
        Konto wybraneKonto = kontoFacade.znajdzPoLoginie(kontoDTO.getLogin());
        try {
            Konto konto = null;
            switch (kontoDTO.getPoziomDostepu()) {
                case ADMINISTRATOR:
                    konto = new Administrator(wybraneKonto);
                    break;
                case PRACOWNIK:
                    konto = new Pracownik(wybraneKonto);
                    break;
                case KLIENT:
                    konto = new Klient(wybraneKonto);
                    break;
            }
            if (konto != null) {
                kontoFacade.remove(wybraneKonto);
                konto.setAktywne(true);
                konto.setAutoryzowane(true);
                konto.setModyfikowanePrzez(ladujBiezacegoAdmin());
                kontoFacade.create(konto);
            }
        } catch (EJBTransactionRolledbackException e) {
            throw new AppBaseException("Poziom dostępu został już nadany");
        }
    }

    public void zmienHaslo(KontoDTO kontoDTO) throws AppBaseException {
        if (kontoStan.getLogin().equals(kontoDTO.getLogin())) {
            kontoStan.setHaslo(kontoDTO.getHaslo());
            kontoStan.setModyfikowanePrzez(ladujBiezacegoAdmin());
            kontoFacade.edit(kontoStan);
        } else {
            throw KontoException.createExceptionWrongState(kontoStan);
        }
    }

    @RolesAllowed({"Administrator", "Pracownik", "Klient"})
    public void zmienMojeHaslo(KontoDTO kontoDTO) throws AppBaseException {
        if (kontoStan.getLogin().equals(kontoDTO.getLogin())) {
            kontoStan = kontoFacade.znajdzPoLoginie(sessionContext.getCallerPrincipal().getName());
            Konto konto = new Konto();
            konto.setHaslo(kontoDTO.getStareHaslo());
            if ((kontoStan.getHaslo().equals(konto.getHaslo()))) {
                kontoStan.setHaslo(kontoDTO.getHaslo());
                kontoStan.setModyfikowanePrzez(null);
                kontoFacade.edit(kontoStan);
            } else {
                throw KontoException.createExceptionWrongState(kontoStan);
            }
        } else {
            throw KontoException.createExceptionWrongState(kontoStan);
        }
    }

    @PermitAll
    public void resetHasla(KontoDTO kontoDTO) throws AppBaseException {
        if (kontoStan.getLogin().equals(kontoDTO.getLogin()) && kontoStan.getOdpowiedzkontrolna().equals(kontoDTO.getOdpowiedz())) {
            kontoStan.setHaslo(kontoDTO.getHaslo());
            kontoFacade.edit(kontoStan);
        } else {
            throw KontoException.createExceptionWrongState(kontoStan);
        }
    }
}
