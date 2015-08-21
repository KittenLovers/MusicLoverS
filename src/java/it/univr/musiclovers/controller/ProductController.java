package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.FilterBean;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
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

    @ManagedProperty(value = "#{filterBean}")
    private FilterBean filterBean;

    private ProductBean selectedProduct;

    private static final long serialVersionUID = 1L;

    public FilterBean getFilterBean() {
        return filterBean;
    }

    public void setFilterBean(FilterBean filter) {
        this.filterBean = filter;
    }

    public List<ProductBean> getOnlineProducts() throws IOException {
        Map<String, Boolean> filters = new LinkedHashMap<>();
        List<ProductBean> result = new ArrayList<>();
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
            result = ProductModel.getProducts(filters);
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
    }

    public String getProduct(int productID) {
        return addParam(redirectString("product"), "productId", String.valueOf(productID));
    }

    public List<ProductBean> getProducts() throws IOException {
        return getOnlineProducts();
    }

    public ProductBean getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(int productId) throws SQLException {
        this.selectedProduct = ProductModel.getProduct(productId);
    }

}
