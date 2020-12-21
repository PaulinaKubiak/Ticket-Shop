package pl.lodz.p.it.spjava.TSS.web.konto;

import javax.inject.Named;
import javax.enterprise.context.ApplicationScoped;
import pl.lodz.p.it.spjava.TSS.web.utils.ContextUtils;

@Named(value = "applicationBean")
@ApplicationScoped
public class ApplicationBean {

    public ApplicationBean() {
    }

    public String getMyLogin() {
        return ContextUtils.getUserName();
    }
}
