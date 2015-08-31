package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.ProductModel;
import it.univr.musiclovers.model.beans.ProductBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import javax.servlet.http.Part;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class ProductController extends ControllerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    private Part file;
    private ProductBean selectedProduct;

    private static String getFilename(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf('/') + 1).substring(filename.lastIndexOf('\\') + 1); // MSIE fix.
            }
        }
        return null;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
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

    public void processImage() {
        try (InputStream inputStream = file.getInputStream()) {
            String pathWebBuild = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("");
            String pathWebSrc = pathWebBuild + "/img/products/";
            File outputFile = new File(pathWebSrc + File.separator + File.separator + getFilename(file));
            FileOutputStream outputStream = new FileOutputStream(outputFile);
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
            selectedProduct.getProductImages().add("img/products/" + getFilename(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public String processProductForm() throws SQLException {
        if (selectedProduct.getId() > 0) {
            ProductModel.editProduct(selectedProduct);
        } else {
            ProductModel.insertProduct(selectedProduct);
        }
        return redirectString("index.xhtml");
    }

    public void removeImage(String image) throws SQLException {
        selectedProduct.getProductImages().remove(image);
        //ProductModel.editProduct(selectedProduct);
    }

    public void removeProduct(int productID) throws SQLException {
        ProductModel.removeProduct(productID);
    }

}
