package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.ProfessionalBean;
import it.univr.musiclovers.types.CodiceFiscale;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marian Solomon
 */
public abstract class CustomerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException {
        AccountBean result = null;
        String query = "SELECT A.id AS A_id, A.username AS A_username, A.password AS A_password, "
                + "P.role AS P_role, P.reduction AS P_reduction, "
                + "C.id AS C_id, C.code AS C_code, C.name AS C_name, C.surname AS C_surname, "
                + "C.city AS C_city, C.telephone AS C_telephone, C.mobile AS C_mobile, C.email AS C_email "
                + "FROM " + getTablePrefix() + "_professional AS P "
                + "JOIN " + getTablePrefix() + "_account AS A ON P.account_id = A.id "
                + "JOIN " + getTablePrefix() + "_customer AS C ON P.customer_id = C.id "
                + "WHERE A.username = ? AND A.password = ?";
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

    protected static AccountBean makeProfessionalAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("A_id"));
        accountBean.setUsername(resultSet.getString("A_username"));
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
        professionalBean.setId(resultSet.getInt("C_id"));
        try {
            professionalBean.setCode(new CodiceFiscale(resultSet.getString("C_code")));
        } catch (CodiceFiscale.MalformedCodiceFiscale ex) {
            Logger.getLogger(CustomerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        professionalBean.setName(resultSet.getString("C_name"));
        professionalBean.setSurname(resultSet.getString("C_surname"));
        professionalBean.setCity(resultSet.getString("C_city"));
        professionalBean.setTelephone(resultSet.getString("C_telephone"));
        professionalBean.setMobile(resultSet.getString("C_mobile"));
        professionalBean.setEmail(resultSet.getString("C_email"));
        professionalBean.setRole(resultSet.getString("P_role"));
        professionalBean.setReduction(resultSet.getString("P_reduction"));

        return professionalBean;
    }

}
