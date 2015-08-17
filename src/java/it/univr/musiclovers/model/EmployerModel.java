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
public class EmployerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmployerBean getEmployer(int id) throws SQLException {
        EmployerBean result = new EmployerBean();
        String query = "SELECT * FROM " + ConnectionModel.getInstance().getTablePrefix() + "_EMPLOYER "
                + " WHERE id = ?";
        Connection connection = ConnectionModel.getInstance().getConnection();
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                result = makeEmployerBean(resultSet);
            }
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
    }

    public static EmployerBean makeEmployerBean(ResultSet resultSet) throws SQLException {
        EmployerBean employerBean = new EmployerBean();
        employerBean.setId(resultSet.getInt("id"));
        employerBean.setName(resultSet.getString("name"));
        employerBean.setSurname(resultSet.getString("surname"));
        employerBean.setCode(resultSet.getString("code"));
        employerBean.setBirthDate(resultSet.getDate("birthdate"));
        employerBean.setAccount(null);
        return employerBean;
    }

}
