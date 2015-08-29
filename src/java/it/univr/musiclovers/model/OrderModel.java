package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.*;
import it.univr.musiclovers.types.CodiceFiscale;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Marian Solomon
 */
public abstract class OrderModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static OrderBean getOrder(int orderID) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
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

    public static List<OrderBean> getOrders() throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
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

    public static OrderBean makeOrderBean(ResultSet resultSet) throws SQLException, CodiceFiscale.MalformedCodiceFiscale {
        OrderBean orderBean = new OrderBean();
        orderBean.setID(resultSet.getInt("id"));
        orderBean.setPrice(resultSet.getFloat("price"));
        orderBean.setSoldDate(resultSet.getDate("sold_date"));
        orderBean.setProduct(ProductModel.getProduct(resultSet.getInt("product_id")));
        orderBean.setSeller(EmployerModel.getAccount(resultSet.getInt("employer_id")));
        orderBean.setBuyer(null);
        orderBean.setOwner(CustomerModel.getProfessional(resultSet.getInt("owner_id")));
        return orderBean;
    }

}
