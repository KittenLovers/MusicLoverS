package it.univr.musiclovers.model.beans;

import java.io.Serializable;

/**
 *
 * @author blasco991
 */
public class ProfessionalBean extends CustomerBean implements PersonBean, Serializable {

    private int accountID;

    private String reduction;
    private String role;
    private static final long serialVersionUID = 1L;

    public int getAccountID() {
        return accountID;
    }

    public void setAccountID(int accountID) {
        this.accountID = accountID;
    }

    public String getReduction() {
        return reduction;
    }

    public void setReduction(String reduction) {
        this.reduction = reduction;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
