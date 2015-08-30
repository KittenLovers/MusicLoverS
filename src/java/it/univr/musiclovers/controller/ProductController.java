package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class ProductController extends ControllerModel implements Serializable {

    private ProductBean selectedProduct;
    private static final long serialVersionUID = 1L;

    public String getProduct(int productID) {
        return addParam(normalizeUrl("product"), "productID", String.valueOf(productID));
    }

    public ProductBean getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(int productId) throws SQLException {
        this.selectedProduct = ProductModel.getProduct(productId);
    }

    public String processProductForm() throws SQLException {
        if (selectedProduct.getId() > 0) {
            ProductModel.editProduct(selectedProduct);
        } else {
            ProductModel.insertProduct(selectedProduct);
        }
        return "index.xhtml";
    }

    public void removeProduct(int productID) throws SQLException {
        ProductModel.removeProduct(productID);
    }

}
