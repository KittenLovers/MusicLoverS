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
public class EmployerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException {
        AccountBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_employer AS E "
                + "JOIN " + getTablePrefix() + "_account AS A ON E.account_id = A.id "
                + "WHERE A.username = ? AND A.password = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeEmployerAccountBean(resultSet);
                }
            }
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
    }

    private static AccountBean makeEmployerAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("A.id"));
        accountBean.setUsername(resultSet.getString("A.username"));
        accountBean.setPerson(makeEmployerBean(resultSet));
        return accountBean;
    }
    
       private static EmployerBean makeEmployerBean(ResultSet resultSet) throws SQLException {
           EmployerBean employerBean = new EmployerBean();
           employerBean.setId(resultSet.getInt("E.id"));
           employerBean.setName(resultSet.getString("E.name"));
           employerBean.setSurname(resultSet.getString("E.surname"));
           employerBean.setCode(resultSet.getString("E.code"));
           employerBean.setBirthDate(resultSet.getDate("E.birthdate"));
           return employerBean;
       }
}
