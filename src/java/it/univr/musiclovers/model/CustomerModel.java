package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.ProfessionalBean;
import it.univr.musiclovers.types.CodiceFiscale;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Marian Solomon
 */
public abstract class CustomerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        AccountBean result = null;
        String query = "SELECT * "
                + "FROM " + getTablePrefix() + "_account "
                + "WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalAccountBean(resultSet);
                }
            }
        }
        return result;
    }

    public static ProfessionalBean getProfessional(int professionalID) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        ProfessionalBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_professional "
                + "JOIN " + getTablePrefix() + "_customer  ON id = customer_id "
                + "WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, professionalID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalBean(resultSet);
                }
            }
        }
        return result;
    }

    protected static AccountBean makeProfessionalAccountBean(ResultSet resultSet) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("id"));
        accountBean.setUsername(resultSet.getString("username"));
        accountBean.setPerson(getProfessional(accountBean.getId()));
        return accountBean;
    }

    private static CustomerBean makeCustomerBean(ResultSet resultSet) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        CustomerBean customerBean = new CustomerBean();
        customerBean.setId(resultSet.getInt("id"));
        customerBean.setCode(new CodiceFiscale(resultSet.getString("code")));
        customerBean.setName(resultSet.getString("name"));
        customerBean.setCity(resultSet.getString("surname"));
        customerBean.setTelephone(resultSet.getString("telephone"));
        customerBean.setMobile(resultSet.getString("mobile"));
        customerBean.setEmail(resultSet.getString("email"));
        return customerBean;
    }

    private static ProfessionalBean makeProfessionalBean(ResultSet resultSet) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        ProfessionalBean professionalBean = new ProfessionalBean();
        professionalBean.setId(resultSet.getInt("id"));
        professionalBean.setCode(new CodiceFiscale(resultSet.getString("code")));
        professionalBean.setName(resultSet.getString("name"));
        professionalBean.setSurname(resultSet.getString("surname"));
        professionalBean.setCity(resultSet.getString("city"));
        professionalBean.setTelephone(resultSet.getString("telephone"));
        professionalBean.setMobile(resultSet.getString("mobile"));
        professionalBean.setEmail(resultSet.getString("email"));
        professionalBean.setRole(resultSet.getString("role"));
        professionalBean.setReduction(resultSet.getString("reduction"));
        return professionalBean;
    }

}
