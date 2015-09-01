package it.univr.musiclovers.model;

import static it.univr.musiclovers.model.Model.getConnection;
import static it.univr.musiclovers.model.Model.getTablePrefix;
import it.univr.musiclovers.model.beans.*;
import java.io.Serializable;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marian Solomon
 */
public abstract class OrderModel extends Model implements Serializable {

    private static final String NEW_FORMAT = "dd/MM/yyyy";
    private static final String OLD_FORMAT = "yyyy-MM-dd";
    private static final long serialVersionUID = 1L;

    public static void editOrder(OrderBean orderBean) throws SQLException {
        String query = "UPDATE " + getTablePrefix() + "_product "
                + "SET price = ?, sold_date = ?, payment_type = ?, product_id = ?, employer_id = ?, "
                + "buyer_id = ?, owner_id = ? "
                + "WHERE id = ?";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query)) {
            prepareStatement.setFloat(1, orderBean.getPrice());
            prepareStatement.setDate(2, Date.valueOf(orderBean.getSoldDate()));
            prepareStatement.setString(3, orderBean.getPaymentType());
            prepareStatement.setInt(4, orderBean.getProduct().getId());
            prepareStatement.setInt(5, orderBean.getSeller().getId());
            prepareStatement.setInt(6, orderBean.getBuyer().getId());
            prepareStatement.setInt(7, orderBean.getOwner().getId());
            prepareStatement.execute();
        }
    }

    public static OrderBean getOrder(int orderID) throws SQLException, ParseException {
        OrderBean result = new OrderBean();
        String query = "SELECT * FROM " + getTablePrefix() + "_order "
                + " WHERE id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, orderID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    result = makeOrderBean(resultSet);
                }
            }
        }
        return result;
    }

    public static List<OrderBean> getOrders() throws SQLException, ParseException {
        List<OrderBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_order";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(makeOrderBean(resultSet));
                }
            }
        }
        return result;
    }

    public static List<OrderBean> getOrdersByBuyer(int buyerID) throws SQLException, ParseException {
        List<OrderBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_order WHERE buyer_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, buyerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(makeOrderBean(resultSet));
                }
            }
        }
        return result;
    }

    public static List<OrderBean> getOrdersBySeller(int sellerID) throws SQLException, ParseException {
        List<OrderBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_order WHERE owner_id = ?";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(query)) {
            preparedStatement.setInt(1, sellerID);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    result.add(makeOrderBean(resultSet));
                }
            }
        }
        return result;
    }

    public static void insertOrder(OrderBean orderBean) throws SQLException {
        String query = "INSERT INTO " + getTablePrefix() + "_order "
                + "(price, sold_date, payment_type, product_id, employer_id, buyer_id, owner_id) VALUES "
                + "(?,?,?,?,?,?,?)";
        try (PreparedStatement prepareStatement = getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            prepareStatement.setFloat(1, orderBean.getPrice());
            prepareStatement.setDate(2, Date.valueOf(orderBean.getSoldDate()));
            prepareStatement.setString(3, orderBean.getPaymentType());
            prepareStatement.setInt(4, orderBean.getProduct().getId());
            prepareStatement.setInt(5, orderBean.getSeller().getId());
            prepareStatement.setInt(6, orderBean.getBuyer().getId());
            prepareStatement.setInt(7, orderBean.getOwner().getId());
            int affectedRows = prepareStatement.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating order failed, no rows affected.");
            }

            try (ResultSet generatedKeys = prepareStatement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    orderBean.setID(generatedKeys.getInt("id"));
                } else {
                    throw new SQLException("Creating order failed, no ID obtained.");
                }
            }
        }
    }

    public static OrderBean makeOrderBean(ResultSet resultSet) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
        java.util.Date d = sdf.parse(resultSet.getDate("sold_date").toString());
        sdf.applyPattern(NEW_FORMAT);
        OrderBean orderBean = new OrderBean();
        orderBean.setID(resultSet.getInt("id"));
        orderBean.setPrice(resultSet.getFloat("price"));
        orderBean.setSoldDate(resultSet.getDate("sold_date").toString());
        orderBean.setProduct(ProductModel.getProduct(resultSet.getInt("product_id")));
        orderBean.setSeller(EmployerModel.getAccount(resultSet.getInt("employer_id")));
        orderBean.setBuyer(CustomerModel.getCustomer(resultSet.getInt("buyer_id")));
        orderBean.setOwner(CustomerModel.getProfessional(resultSet.getInt("owner_id")));
        orderBean.setPaymentType(resultSet.getString("payment_type"));
        return orderBean;
    }

}
