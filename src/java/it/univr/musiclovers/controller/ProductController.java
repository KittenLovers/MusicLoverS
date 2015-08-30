package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class ProductController extends ControllerModel implements Serializable {

    private ProductBean selectedProduct;
    private static final long serialVersionUID = 1L;

    public void editProduct() throws SQLException {
        selectedProduct.isValid();
        //FacesContext.getCurrentInstance().addMessage("product-form:productName", new FacesMessage("Il nome del prodotto è obbligatorio!"));
        ProductModel.editProduct(selectedProduct);
    }

    public String getProduct(int productID) {
        return addParam(normalizeUrl("product"), "productID", String.valueOf(productID));
    }

    public ProductBean getSelectedProduct() {
        return selectedProduct;
    }

    public void setSelectedProduct(int productId) throws SQLException {
        this.selectedProduct = ProductModel.getProduct(productId);
    }

    public void removeProduct(int productID) throws SQLException {
        ProductModel.removeProduct(productID);
    }

}
