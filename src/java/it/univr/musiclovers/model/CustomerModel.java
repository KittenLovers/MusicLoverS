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
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marian Solomon
 */
public abstract class CustomerModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

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
            prepareStatement.setInt(8, customerBean.getId());
            prepareStatement.execute();
        }
    }

    public static void editProfessional(AccountBean accountBean) throws SQLException {
        String query = "UPDATE " + getTablePrefix() + "_professional "
                + "SET role = ?, reduction = ? "
                + "WHERE customer_id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setString(1, ((ProfessionalBean) accountBean.getPerson()).getRole());
            prepareStatement.setInt(2, ((ProfessionalBean) accountBean.getPerson()).getReduction());
            prepareStatement.setInt(3, ((ProfessionalBean) accountBean.getPerson()).getId());
            prepareStatement.execute();
        }
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

    public static AccountBean getAccount(int accountID) throws SQLException {
        AccountBean result = null;
        String query = "SELECT * "
                + "FROM " + getTablePrefix() + "_account "
                + "WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, accountID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalAccountBean(resultSet);
                }
            }
        }
        return result;
    }

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

    public static List<CustomerBean> getCustomers() throws SQLException {
        ArrayList<CustomerBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_customer ORDER BY id ASC";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeCustomerBean(resultSet));
                }
            }
        }
        return result;
    }

    public static ProfessionalBean getProfessional(int customerID) throws SQLException {
        ProfessionalBean result = new ProfessionalBean();
        String query = "SELECT * FROM " + getTablePrefix() + "_professional "
                + "JOIN " + getTablePrefix() + "_customer ON id = customer_id "
                + "WHERE customer_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, customerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalBean(resultSet);
                }
            }
        }
        return result;
    }

    public static ProfessionalBean getProfessionalByAccountID(int accountID) throws SQLException {
        ProfessionalBean result = null;
        String query = "SELECT * FROM " + getTablePrefix() + "_professional "
                + "JOIN " + getTablePrefix() + "_customer ON id = customer_id "
                + "WHERE account_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, accountID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeProfessionalBean(resultSet);
                }
            }
        }
        return result;
    }

    public static List<Integer> getProfessionalIDs() throws SQLException {
        ArrayList<Integer> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_professional";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(resultSet.getInt("customer_id"));
                }
            }
        }
        return result;
    }

    public static List<ProfessionalBean> getProfessionals() throws SQLException {
        ArrayList<ProfessionalBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_professional "
                + "JOIN " + getTablePrefix() + "_customer  ON id = customer_id";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeProfessionalBean(resultSet));
                }
            }
        }
        return result;
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

    public static void insertProfessional(AccountBean accountBean) throws SQLException {
        String query = "INSERT INTO " + getTablePrefix() + "_account "
                + "(username, password) VALUES"
                + "(?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setString(1, accountBean.getUsername());
            prepareStatement.setString(2, accountBean.getPassword());
            int affectedRows = prepareStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating account failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    accountBean.setId(generatedKeys.getInt("id"));
                } else {
                    throw new SQLException("Creating account failed, no ID obtained.");
                }
            }
        }
        ProfessionalBean professionalBean = ((ProfessionalBean) accountBean.getPerson());
        insertCustomer(professionalBean);

        query = "INSERT INTO " + getTablePrefix() + "_professional "
                + "(customer_id, account_id, role, redcution) VALUES"
                + "(?,?,?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setInt(1, professionalBean.getId());
            prepareStatement.setInt(2, accountBean.getId());
            prepareStatement.setString(3, professionalBean.getRole());
            prepareStatement.setInt(4, professionalBean.getReduction());
            prepareStatement.executeUpdate();
        }
    }

    public static void removeCustomer(int customerID) throws SQLException {

        //todo check ordes
        String query = "DELETE FROM " + getTablePrefix() + "_professional WHERE customer_id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, customerID);
            prepareStatement.execute();
        }
        query = "DELETE FROM " + getTablePrefix() + "_customer WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setInt(1, customerID);
            prepareStatement.execute();
        }
        ProfessionalBean professionalBean = getProfessional(customerID);
        if (professionalBean instanceof ProfessionalBean) {
            query = "DELETE FROM " + getTablePrefix() + "_account WHERE id = ?";
            try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
                prepareStatement.setInt(1, professionalBean.getAccountID());
                prepareStatement.execute();
            }
        }
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

    private static AccountBean makeProfessionalAccountBean(ResultSet resultSet) throws SQLException {
        AccountBean accountBean = new AccountBean();
        accountBean.setId(resultSet.getInt("id"));
        accountBean.setUsername(resultSet.getString("username"));
        accountBean.setPerson(getProfessionalByAccountID(accountBean.getId()));
        return accountBean;
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
        professionalBean.setReduction(resultSet.getInt("reduction"));
        professionalBean.setAccountID(resultSet.getInt("account_id"));
        return professionalBean;
    }
}
