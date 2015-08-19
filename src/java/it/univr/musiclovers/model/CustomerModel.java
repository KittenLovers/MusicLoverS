package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.ProfessionalBean;
import it.univr.musiclovers.types.CodiceFiscale;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marian Solomon
 */
public class CustomerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException {
        AccountBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_professional AS P"
                + "JOIN " + getTablePrefix() + "_account AS A ON P.account_id = A.id"
                + "JOIN " + getTablePrefix() + "_customer AS C ON P.customer_id = C.id"
                + " WHERE A.username = ? AND A.password = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalAccountBean(resultSet);
                }
            }
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }
        return result;
    }

    protected static AccountBean makeProfessionalAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("A.id"));
        accountBean.setUsername(resultSet.getString("A.username"));
        accountBean.setPerson(makeProfessionalBean(resultSet));
        return accountBean;
    }

    private static CustomerBean makeCustomerBean(ResultSet resultSet) throws SQLException {
        CustomerBean customerBean = new CustomerBean();
        customerBean.setId(resultSet.getInt("id"));
        try {
            customerBean.setCode(new CodiceFiscale(resultSet.getString("code")));
        } catch (CodiceFiscale.MalformedCodiceFiscale ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        customerBean.setName(resultSet.getString("name"));
        customerBean.setCity(resultSet.getString("surname"));
        customerBean.setTelephone(resultSet.getString("telephone"));
        customerBean.setMobile(resultSet.getString("mobile"));
        customerBean.setEmail(resultSet.getString("email"));
        return customerBean;
    }

    private static ProfessionalBean makeProfessionalBean(ResultSet resultSet) throws SQLException {
        ProfessionalBean professionalBean = new ProfessionalBean();
        professionalBean.setId(resultSet.getInt("C.id"));
        try {
            professionalBean.setCode(new CodiceFiscale(resultSet.getString("C.code")));
        } catch (CodiceFiscale.MalformedCodiceFiscale ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        professionalBean.setName(resultSet.getString("C.name"));
        professionalBean.setCity(resultSet.getString("C.surname"));
        professionalBean.setTelephone(resultSet.getString("C.telephone"));
        professionalBean.setMobile(resultSet.getString("C.mobile"));
        professionalBean.setEmail(resultSet.getString("C.email"));
        professionalBean.setRole(resultSet.getString("P.role"));
        professionalBean.setReduction(resultSet.getString("P.reduction"));

        return professionalBean;
    }

}
