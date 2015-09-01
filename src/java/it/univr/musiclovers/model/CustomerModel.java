package it.univr.musiclovers.model;

import static it.univr.musiclovers.model.Model.getConnection;
import static it.univr.musiclovers.model.Model.getTablePrefix;
import it.univr.musiclovers.model.beans.AccountBean;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.ProfessionalBean;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Marian Solomon
 */
public abstract class CustomerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static CustomerBean getCustomer(int customerID) throws SQLException {
        CustomerBean result = new CustomerBean();
        String query = "SELECT * "
                + "FROM " + getTablePrefix() + "_customer "
                + "WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeCustomerBean(resultSet);
                }
            }
        }
        return result;
    }
    
    public static AccountBean getAccount(String username, String password) throws SQLException {
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

    public static ProfessionalBean getProfessional(int professionalID) throws SQLException {
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

    protected static AccountBean makeProfessionalAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("id"));
        accountBean.setUsername(resultSet.getString("username"));
        accountBean.setPerson(getProfessional(accountBean.getId()));
        return accountBean;
    }

    private static CustomerBean makeCustomerBean(ResultSet resultSet) throws SQLException {
        CustomerBean customerBean = new CustomerBean();
        customerBean.setId(resultSet.getInt("id"));
        customerBean.setCode(resultSet.getString("code"));
        customerBean.setName(resultSet.getString("name"));
        customerBean.setSurname(resultSet.getString("surname"));
        customerBean.setCity(resultSet.getString("city"));
        customerBean.setTelephone(resultSet.getString("telephone"));
        customerBean.setMobile(resultSet.getString("mobile"));
        customerBean.setEmail(resultSet.getString("email"));
        return customerBean;
    }

    private static ProfessionalBean makeProfessionalBean(ResultSet resultSet) throws SQLException {
        ProfessionalBean professionalBean = new ProfessionalBean();
        professionalBean.setId(resultSet.getInt("id"));
        professionalBean.setCode(resultSet.getString("code"));
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
    
    public static void editCustomer(CustomerBean customerBean) throws SQLException {
        String query = "UPDATE " + getTablePrefix() + "_customer "
                + "SET code = ?, name = ?, surname = ?, city = ?, telephone = ?, "
                + "mobile = ?, email = ? "
                + "WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setString(1, customerBean.getCode());
            prepareStatement.setString(2, customerBean.getName());
            prepareStatement.setString(3, customerBean.getSurname());
            prepareStatement.setString(4, customerBean.getCity());
            prepareStatement.setString(5, customerBean.getTelephone());
            prepareStatement.setString(6, customerBean.getMobile());
            prepareStatement.setString(7, customerBean.getEmail());
            prepareStatement.execute();
        }
    }

    public static void insertCustomer(CustomerBean customerBean) throws SQLException {
        String query = "INSERT INTO " + getTablePrefix() + "_customer "
                + "(code, name, surname, city, telephone, mobile, email) VALUES"
                + "(?,?,?,?,?,?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, customerBean.getCode());
            prepareStatement.setString(2, customerBean.getName());
            prepareStatement.setString(3, customerBean.getSurname());
            prepareStatement.setString(4, customerBean.getCity());
            prepareStatement.setString(5, customerBean.getTelephone());
            prepareStatement.setString(6, customerBean.getMobile());
            prepareStatement.setString(7, customerBean.getEmail());
            int affectedRows = prepareStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating customer failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customerBean.setId(generatedKeys.getInt("id"));
                } else {
                    throw new SQLException("Creating customer failed, no ID obtained.");
                }
            }
        }
    }

    public static void removeCustomer(int customerID) throws SQLException {        
        String query = "DELETE FROM " + getTablePrefix() + "_CUSTOMER WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, customerID);
            prepareStatement.execute();
        }
    }    
}
