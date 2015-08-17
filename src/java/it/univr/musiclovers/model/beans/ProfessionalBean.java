package it.univr.musiclovers.model.beans;

/**
 *
 * @author blasco991
 */
public class ProfessionalBean implements PersonBean {

    private CustomerBean customer;
    private AccountBean account;
    private String role;

    public CustomerBean getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerBean customer) {
        this.customer = customer;
    }

    public AccountBean getAccount() {
        return account;
    }

    public void setAccount(AccountBean account) {
        this.account = account;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

}
