package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.BrandModel;
import it.univr.musiclovers.model.beans.BrandBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class BrandController extends ControllerModel implements Serializable {

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

    public String processProductForm() throws SQLException {
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

}
