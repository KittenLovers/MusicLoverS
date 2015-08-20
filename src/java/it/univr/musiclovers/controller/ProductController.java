package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.FilterBean;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@RequestScoped
public class ProductController extends ControllerModel implements Serializable {

    private static final long serialVersionUID = 1L;
    private List<ProductBean> products = new ArrayList<>();
    private ProductBean selectedProduct;

    @ManagedProperty(value = "#{filterBean}")
    private FilterBean filterBean;

    public List<ProductBean> getProducts() {
        getOnlineProducts();
        return Collections.unmodifiableList(products);
    }

    public void getOnlineProducts() {
        Map<String, Boolean> filters = new LinkedHashMap<>();
        filters.put("online", true);

        if (filterBean.isFor_child()) {
            filters.put("for_child", true);
        }

        if (filterBean.isInexpensive()) {
            filters.put("inexpensive", true);
        }

        if (filterBean.isProfessional()) {
            filters.put("professional", true);
        }

        if (filterBean.isUsed()) {
            filters.put("used", true);
        }

        try {
            products = ProductModel.getProducts(filters);
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
    }

    public String getProduct(ProductBean productBean) {
        selectedProduct = productBean;
        return retString("product");
    }

    public ProductBean getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(ProductBean selectedProduct) {
        this.selectedProduct = selectedProduct;
    }

    public FilterBean getFilterBean() {
        return filterBean;
    }

    public void setFilterBean(FilterBean filter) {
        this.filterBean = filter;
    }

}
