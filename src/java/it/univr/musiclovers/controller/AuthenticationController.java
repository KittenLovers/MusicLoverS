package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.CustomerModel;
import it.univr.musiclovers.model.EmployerModel;
import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.PersonBean;
import it.univr.musiclovers.types.CodiceFiscale;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author blasco991
 */
@ManagedBean
@SessionScoped
public class AuthenticationController extends ControllerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private AccountBean accountBean = new AccountBean();
    private boolean logged = false;

    public void check() throws IOException {
        if (isLogged() && accountBean.isEmployer()) {
            redirect("employer/");
        } else if (isLogged() && accountBean.isProfessional()) {
            redirect("customer/");
        }
    }

    public void checkEmployer() throws IOException {
        if (!isLogged() || !accountBean.isEmployer()) {
            redirect("login");
        }
    }

    public void checkProfessional() throws IOException {
        if (!isLogged() || !accountBean.isProfessional()) {
            redirect("login");
        }
    }

    public String getPassword() {
        return accountBean.getPassword();
    }

    public void setPassword(String password) {
        accountBean.setPassword(password);
    }

    public String getUsername() {
        return accountBean.getUsername();
    }

    public void setUsername(String username) {
        accountBean.setUsername(username);
    }

    public boolean isLogged() {
        return logged;
    }

    public String login(String componendID, AccountBean account, String outcome) {
        if (account instanceof AccountBean && account.getPerson() instanceof PersonBean) {
            accountBean = account;
            logged = true;
            return redirectString(outcome);
        } else {
            FacesContext.getCurrentInstance().addMessage(componendID, new FacesMessage("Username o Password errati!"));
            return addExt("login");
        }
    }

    public String loginEmployer() {
        AccountBean account = null;
        try {
            account = EmployerModel.getAccount(accountBean.getUsername(), accountBean.getPassword());
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return login("loginEmployer", account, "employer/");
    }

    public String loginProfessional() throws CodiceFiscale.MalformedCodiceFiscale {
        AccountBean account = null;
        try {
            account = CustomerModel.getAccount(accountBean.getUsername(), accountBean.getPassword());
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return login("loginProfessional", account, "customer/");
    }

    public String logout() {
        accountBean = new AccountBean();
        logged = false;
        return redirectString("/homepage");
    }

}
