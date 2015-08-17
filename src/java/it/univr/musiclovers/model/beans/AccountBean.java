package it.univr.musiclovers.model.beans;

/**
 *
 * @author blasco991
 */
public class AccountBean {

    private int id;
    private String username;
    private String password;
    private PersonBean person;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEmployer() {
        return person instanceof EmployerBean;
    }
    
    public boolean isProfessional() {
        return person instanceof ProfessionalBean;
    }

    public PersonBean getPerson() {
        return person;
    }

    public void setPerson(PersonBean person) {
        this.person = person;
    }

}
