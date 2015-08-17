package it.univr.musiclovers.model.beans;

import java.io.Serializable;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean(name="filterBean")
@SessionScoped
public class FilterBean implements Serializable {

    private static final long serialVersionUID = 1L;

    private boolean for_child;
    private boolean inexpensive;
    private boolean professional;
    private boolean used;

    @PostConstruct
    public void initialize() {
        this.for_child = false;
        this.inexpensive = false;
        this.professional = false;
    }

    public boolean isFor_child() {
        return for_child;
    }

    public void setFor_child(boolean for_child) {
        this.for_child = for_child;
    }

    public boolean isInexpensive() {
        return inexpensive;
    }

    public void setInexpensive(boolean inexpensive) {
        this.inexpensive = inexpensive;
    }

    public boolean isProfessional() {
        return professional;
    }

    public void setProfessional(boolean professional) {
        this.professional = professional;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

}
