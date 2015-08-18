package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.*;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marian Solomon
 */
public class BrandModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public BrandBean getBrand(int id) throws SQLException {
        BrandBean result = new BrandBean();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_BRAND "
                + " WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeBrandBean(resultSet);
                }
            }
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
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

}
