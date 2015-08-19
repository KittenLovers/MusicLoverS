package it.univr.musiclovers.model.beans;

import it.univr.musiclovers.types.CodiceFiscale;
import javax.mail.Address;

/**
 *
 * @author blasco991
 */
public class CustomerBean {

    private int id;
    private CodiceFiscale code;
    private String name;
    private String surname;
    private String city;
    private String telephone;
    private String mobile;
    private String email;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public CodiceFiscale getCode() {
        return code;
    }

    public void setCode(CodiceFiscale code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

}
