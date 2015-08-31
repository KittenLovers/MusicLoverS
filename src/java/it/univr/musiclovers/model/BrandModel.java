package it.univr.musiclovers.model;

import static it.univr.musiclovers.model.Model.getConnection;
import static it.univr.musiclovers.model.Model.getTablePrefix;
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

    public static void editBrand(BrandBean selectedBrand) throws SQLException {
        String query = "UPDATE " + getTablePrefix() + "_brand "
                + "SET name = ?, description = ?, link = ?, logo = ? "
                + "WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setString(1, selectedBrand.getName());
            prepareStatement.setString(2, selectedBrand.getDescription());
            prepareStatement.setString(3, selectedBrand.getLink());
            prepareStatement.setString(4, selectedBrand.getLogo());
            prepareStatement.setInt(5, selectedBrand.getId());
            prepareStatement.execute();
        }        
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

    public static void insertBrand(BrandBean selectedBrand) throws SQLException {
        String query = "INSERT INTO " + getTablePrefix() + "_brand "
                + "(name, description, link, logo) VALUES"
                + "(?,?,?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, selectedBrand.getName());
            prepareStatement.setString(2, selectedBrand.getDescription());
            prepareStatement.setString(3, selectedBrand.getLink());
            prepareStatement.setString(4, selectedBrand.getLogo());
            int affectedRows = prepareStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating brand failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    selectedBrand.setId(generatedKeys.getInt("id"));
                } else {
                    throw new SQLException("Creating brand failed, no ID obtained.");
                }
            }            
        }
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

    public static void removeBrand(int brandID) throws SQLException {        
        String query = "DELETE FROM " + getTablePrefix() + "_BRAND WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, brandID);
            prepareStatement.execute();
        }
    }

}
