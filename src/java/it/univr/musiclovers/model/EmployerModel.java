package it.univr.musiclovers.model;

import java.sql.ResultSet;
import java.sql.Connection;
import java.io.Serializable;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import it.univr.musiclovers.model.beans.*;
import java.sql.Date;

/**
 *
 * @author Marian Solomon
 */
public class EmployerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public EmployerBean getEmployer(int id) {
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
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return result;
    }

    public static EmployerBean makeEmployerBean(ResultSet resultSet) {
        EmployerBean employerBean = new EmployerBean();
        try {
            employerBean.setId(resultSet.getInt("id"));
            employerBean.setName(resultSet.getString("name"));
            employerBean.setSurname(resultSet.getString("surname"));
            employerBean.setCode(resultSet.getString("code"));
            employerBean.setBirthDate(resultSet.getDate("birthdate"));
            employerBean.setAccount(null);
        } catch (SQLException ex) {
            for (Throwable throwable : ex) {
                System.err.println(throwable);
            }
        }
        return employerBean;
    }

}
