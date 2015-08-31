package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.servlet.http.Part;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class ProductController extends ControllerModel implements Serializable {

    private Part file;
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

    public void processImage() throws IOException {
        try (InputStream inputStream = file.getInputStream();
                FileOutputStream outputStream = new FileOutputStream(getFilename(file))) {
            byte[] buffer = new byte[4096];
            int bytesRead = 0;
            while (true) {
                bytesRead = inputStream.read(buffer);
                if (bytesRead > 0) {
                    outputStream.write(buffer, 0, bytesRead);
                } else {
                    break;
                }
            }
            selectedProduct.getProductImages().add(getFilename(file));
        }
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

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.  
            }
        }
        return null;
    }

}
