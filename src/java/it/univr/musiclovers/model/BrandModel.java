package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.*;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marian Solomon
 */
public abstract class BrandModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void editBrand(BrandBean selectedBrand) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static BrandBean getBrand(int id) throws SQLException {
        BrandBean result = new BrandBean();
        String query = "SELECT * FROM " + getTablePrefix() + "_BRAND "
                + " WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeBrandBean(resultSet);
                }
            }
        }
        return result;
    }

    public static List<BrandBean> getBrands() throws SQLException {
        ArrayList<BrandBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_brand ORDER BY id ASC";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeBrandBean(resultSet));
                }
            }
        }
        return result;
    }

    public static void insertBrand(BrandBean selectedBrand) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public static BrandBean makeBrandBean(ResultSet resultSet) throws SQLException {
        BrandBean brandBean = new BrandBean();
        brandBean.setId(resultSet.getInt("id"));
        brandBean.setName(resultSet.getString("name"));
        brandBean.setDescription(resultSet.getString("description"));
        brandBean.setLink(resultSet.getString(4));
        brandBean.setLogo(resultSet.getString(5));
        return brandBean;
    }

    public static void removeBrand(int brandID) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
