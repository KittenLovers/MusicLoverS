package it.univr.musiclovers.controller;

import static it.univr.musiclovers.controller.ControllerModel.exceptionHandler;
import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class FilterController implements Serializable {

    private boolean forChild;
    private boolean inexpensive;
    private boolean professional;
    private boolean used;
    private static final long serialVersionUID = 1L;

    public boolean getForChild() {
        return forChild;
    }

    public void setForChild(boolean forChild) {
        this.forChild = forChild;
    }

    public List<ProductBean> getOnlineProducts() throws IOException {
        Map<String, Boolean> filters = new LinkedHashMap<>();
        filters.put("online", true);
        return getProducts(filters);
    }

    public List<ProductBean> getProducts() throws IOException {
        return getProducts(new LinkedHashMap<>());
    }

    @PostConstruct
    public void initialize() {
        this.forChild = false;
        this.inexpensive = false;
        this.professional = false;
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

    private List<ProductBean> getProducts(Map<String, Boolean> filters) throws IOException {
        List<ProductBean> result = new ArrayList<>();

        if (getForChild()) {
            filters.put("for_child", true);
        }

        if (isInexpensive()) {
            filters.put("inexpensive", true);
        }

        if (isProfessional()) {
            filters.put("professional", true);
        }

        if (isUsed()) {
            filters.put("used", true);
        }

        try {
            result = ProductModel.getProducts(filters);
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
    }

}
