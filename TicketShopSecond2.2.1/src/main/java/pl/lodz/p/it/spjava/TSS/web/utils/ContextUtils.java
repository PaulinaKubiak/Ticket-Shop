package pl.lodz.p.it.spjava.TSS.web.utils;

import java.security.Principal;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

/**
 * Przykład sposobu uzyskiwania dostępu z poziomu ziarna zarządzanego przez JSF
 * do: - atrybutów żądania, sesji i aplikacji - parametrów konfiguracyjnych
 * zdefiniowanych w deskryptorze wdrożenia web.xml - zakończenia sesji (metoda
 * invalidate()) UWAGA! Nie jest to ziarno, lecz zwykła klasa narzędziowa.
 * Metody tej klasy muszą być jednak wywoływane w kontekście żądania
 * obsługiwanego przez JSF, inaczej nie uzyska ona kontekstu JSF (FacesContext).
 *
 */
public class ContextUtils {

    public ContextUtils() {
    }

    /**
     * Zwraca obiekt FacesContext - kontekst serwletu FacesServlet
     */
    public static ExternalContext getContext() {
        return FacesContext.getCurrentInstance().getExternalContext();
    }

    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście aplikacji
     */
    public static Object getApplicationAttribute(String attributeName) {
        return getContext().getApplicationMap().get(attributeName);
    }

    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście sesji
     */
    public static Object getSessionAttribute(String attributeName) {
        return getContext().getSessionMap().get(attributeName);
    }

    /**
     * Wyszukuje atrybut o zadanej nazwie w kontekście żądania
     */
    public static Object getRequestAttribute(String attributeName) {
        return getContext().getRequestMap().get(attributeName);
    }

    /**
     * Wyszukuje parametr inicjalizacyjny o zadanej nazwie
     */
    public static String getContextParameter(String paramName) {
        return getContext().getInitParameter(paramName);
    }

    /**
     * Dokonuje zamknięcia bieżącej sesji
     */
    public static void invalidateSession() {
        ((HttpSession) getContext().getSession(true)).invalidate();
    }

    /**
     * Zwraca identyfikator bieżącej sesji
     */
    public static String getSessionID() {
        HttpSession session = (HttpSession) getContext().getSession(true);
        return session.getId();
    }

    /**
     * Zwraca nazwę zalogowanego użytkownika
     */
    public static String getUserName() {
        Principal p = FacesContext.getCurrentInstance().getExternalContext().getUserPrincipal();
        return (null == p ? "!BRAK UWIERZYTELNIENIA!" : p.getName());
    }

    /**
     * Zwraca zasób (ResourceBundle) o ścieżce wskazywanej przez parametr
     * resourceBundle.path
     */
    public static ResourceBundle getDefaultBundle() {
        String bundlePath = getContextParameter("resourceBundle.path");
        if (null == bundlePath) {
            return null;
        } else {
            return ResourceBundle.getBundle(bundlePath, FacesContext.getCurrentInstance().getViewRoot().getLocale());
        }
    }

    public static boolean isI18NKeyExist(final String key) {
        try {
            return ContextUtils.getDefaultBundle().getString(key) != null && !"".equals(ContextUtils.getDefaultBundle().getString(key));
        } catch (MissingResourceException e) {
            return false;
        }
    }

    public static String getI18NMessage(final String key) {
        if (isI18NKeyExist(key)) {
            return ContextUtils.getDefaultBundle().getString(key);
        } else {
            return key;
        }
    }

    public static void emitI18NMessage(final String id, final String key) {
        FacesMessage msg = new FacesMessage(getI18NMessage(key));
        FacesContext.getCurrentInstance().addMessage(id, msg);

    }

}
