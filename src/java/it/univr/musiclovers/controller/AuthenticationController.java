package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.AccountModel;
import it.univr.musiclovers.model.beans.AccountBean;
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

    private AccountBean accountBean = new AccountBean();
    private boolean logged = false;

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

    
    //validate login
    public String login() throws SQLException {
        accountBean = AccountModel.getAccount(accountBean.getUsername(), accountBean.getPassword());
        if (accountBean instanceof AccountBean) {
            logged = true;
            return "admin";
        } else {
            accountBean = null;
            FacesContext.getCurrentInstance().addMessage(
                    null,
                    new FacesMessage(FacesMessage.SEVERITY_WARN,
                            "Incorrect Username and Passowrd",
                            "Please enter correct username and Password"));
            return "login";
        }
    }

    //logout event, invalidate session
    public String logout() {
        logged = false;
        return "login";
    }

}
