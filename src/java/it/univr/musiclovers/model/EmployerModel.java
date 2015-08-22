package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.EmployerBean;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marian Solomon
 */
public abstract class EmployerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException {
        AccountBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_account "
                + "WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeEmployerAccountBean(resultSet);
                }
            }
        }
        return result;
    }

    private static EmployerBean getEmployer(int accountID) throws SQLException {
        EmployerBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_employer "
                + "WHERE account_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, accountID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeEmployerBean(resultSet);
                }
            }
        }
        return result;
    }

    private static AccountBean makeEmployerAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("id"));
        accountBean.setUsername(resultSet.getString("username"));
        accountBean.setPerson(getEmployer(accountBean.getId()));
        return accountBean;
    }

    private static EmployerBean makeEmployerBean(ResultSet resultSet) throws SQLException {
        EmployerBean employerBean = new EmployerBean();
        employerBean.setId(resultSet.getInt("id"));
        employerBean.setName(resultSet.getString("name"));
        employerBean.setSurname(resultSet.getString("surname"));
        employerBean.setCode(resultSet.getString("code"));
        employerBean.setBirthDate(resultSet.getDate("birthdate"));
        return employerBean;
    }
}
