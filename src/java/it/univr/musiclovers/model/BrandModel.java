package it.univr.musiclovers.model;

import java.sql.ResultSet;
import java.sql.Connection;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import it.univr.musiclovers.model.beans.*;

/**
 *
 * @author Marian Solomon
 */
public class BrandModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public BrandBean getBrand(int id) {
        BrandBean result = new BrandBean();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_BRAND "
                + " WHERE id = ?";
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = makeBrandBean(resultSet);
            }
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public static BrandBean makeBrandBean(ResultSet resultSet) {
        BrandBean brandBean = new BrandBean();
        try {
            brandBean.setId(resultSet.getInt("id"));
            brandBean.setName(resultSet.getString("name"));
            brandBean.setDescription(resultSet.getString("description"));
            brandBean.setLink(resultSet.getString(4));
            brandBean.setLogo(resultSet.getString(5));
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return brandBean;
    }

}
