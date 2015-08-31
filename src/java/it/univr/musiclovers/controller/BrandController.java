package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.BrandModel;
import it.univr.musiclovers.model.beans.BrandBean;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
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
public class BrandController extends ControllerModel implements Serializable {

    private Part file;
    private BrandBean selectedBrand;
    private static final long serialVersionUID = 1L;
        
    public String getBrand(int brandID) {
        return addParam(normalizeUrl("brand"), "brandID", String.valueOf(brandID));
    }

    public List<BrandBean> getBrands() throws SQLException {
        return BrandModel.getBrands();
    }

    public void setSelectedBrand(int brandId) throws SQLException {
        this.selectedBrand = BrandModel.getBrand(brandId);
    }

    public BrandBean getSelectedBrand() {
        return selectedBrand;
    }

    public String processBrandForm() throws SQLException {
        if (selectedBrand.getId() > 0) {
            BrandModel.editBrand(selectedBrand);
        } else {
            BrandModel.insertBrand(selectedBrand);
        }
        return "index.xhtml";
    }

    public void removeBrand(int brandID) throws SQLException {
        BrandModel.removeBrand(brandID);
    }
    
    public String removeLogo(String logo) throws SQLException {
        selectedBrand.setLogo("img/image-not-found.png");
        return "pippo.xhtml";
    }
    
    public void processImage() {
        try (InputStream inputStream = file.getInputStream()) {
            String pathWebBuild = ((ServletContext) FacesContext.getCurrentInstance().getExternalContext().getContext()).getRealPath("");
            String pathWebSrc = pathWebBuild + "/img/brands/";
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
            selectedBrand.setLogo("img/brands/" + getFilename(file));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(ProductController.class.getName()).log(Level.SEVERE, null, ex);
        }
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

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }
    
    

}
