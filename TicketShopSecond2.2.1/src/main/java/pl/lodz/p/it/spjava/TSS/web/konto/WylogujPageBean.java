package pl.lodz.p.it.spjava.TSS.web.konto;

import javax.inject.Named;
import javax.enterprise.context.RequestScoped;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "wylogujPageBean")
@RequestScoped
public class WylogujPageBean {

    public String wyglogujAkcja() {
        ContextUtils.invalidateSession();
        return "main";
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }

    public WylogujPageBean() {
    }
}
