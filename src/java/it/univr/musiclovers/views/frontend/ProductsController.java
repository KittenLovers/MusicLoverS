package it.univr.musiclovers.views.frontend;

import it.univr.musiclovers.model.beans.FilterBean;
import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean(name = "productController")
@SessionScoped
public class ProductsController implements Serializable {

    private static final long serialVersionUID = 1L;
    private ProductModel model;
    private List<ProductBean> products;
    private ProductBean selectedProduct;

    @ManagedProperty(value = "#{filterBean}")
    private FilterBean filterBean;

    @PostConstruct
    public void initialize() {
        model = new ProductModel();
    }

    public List<ProductBean> getProducts() {
        getOnlineProducts();
        return Collections.unmodifiableList(products);
    }

    public String getOnlineProducts() {
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

        products = model.getProducts(filters);

        return "index";
    }

    public String getProduct(ProductBean productBean) {
        selectedProduct = productBean;
        return "product";
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
