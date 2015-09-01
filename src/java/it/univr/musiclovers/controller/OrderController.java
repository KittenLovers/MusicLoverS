package it.univr.musiclovers.controller;

import static it.univr.musiclovers.controller.ControllerModel.redirectString;
import static it.univr.musiclovers.model.Model.getConnection;
import static it.univr.musiclovers.model.Model.getTablePrefix;
import it.univr.musiclovers.model.OrderModel;
import it.univr.musiclovers.model.beans.EmployerBean;
import it.univr.musiclovers.model.beans.OrderBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@SessionScoped
public class OrderController extends ControllerModel implements Serializable {

    private OrderBean selectedOrder;
    private static final long serialVersionUID = 1L;

    public List<OrderBean> getOrder() throws SQLException {
        return OrderModel.getOrders();
    }

    public OrderBean getSelectedOrder() {
        return selectedOrder;
    }

    public void setSelectedOrder(int orderID) throws SQLException {
        this.selectedOrder = OrderModel.getOrder(orderID);
    }

    public String getProduct(int orderID) {
        return addParam(normalizeUrl("order"), "orderID", String.valueOf(orderID));
    }

    public void removeOrder(int orderID) throws SQLException {

    }

    public String processProductOrderForm() throws SQLException {
        if (selectedOrder.getID() > 0) {
            OrderModel.editOrder(selectedOrder);
        } else {
            OrderModel.insertOrder(selectedOrder);
        }
        return redirectString("index.xhtml");
    }

    public List<EmployerBean> getEmployers() throws SQLException {
        ArrayList<EmployerBean> result = new ArrayList<>();
        String query = "SELECT * FROM " + getTablePrefix() + "_employer ORDER BY id ASC";
        try (Statement statement = getConnection().createStatement()) {
            try (ResultSet resultSet = statement.executeQuery(query)) {
                while (resultSet.next()) {
                    result.add(makeEmployerBean(resultSet));
                }
            }
        }
        return result;
    }

    private static EmployerBean makeEmployerBean(ResultSet resultSet) throws SQLException {
        EmployerBean employerBean = new EmployerBean();
        employerBean.setId(resultSet.getInt("id"));
        employerBean.setCode(resultSet.getString("code"));
        employerBean.setName(resultSet.getString("name"));
        employerBean.setSurname(resultSet.getString("surname"));
        employerBean.setBirthDate(resultSet.getDate("birthDate"));
        return employerBean;
    }

    public List<OrderBean> getOrdersBySeller(int sellerID) throws SQLException {
        return OrderModel.getOrdersBySeller(sellerID);
    }

    public List<OrderBean> getOrdersByBuyer(int buyerID) throws SQLException {
        return OrderModel.getOrdersByBuyer(buyerID);
    }
}
