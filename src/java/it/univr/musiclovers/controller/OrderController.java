package it.univr.musiclovers.controller;

import static it.univr.musiclovers.model.Model.getConnection;
import static it.univr.musiclovers.model.Model.getTablePrefix;
import it.univr.musiclovers.model.OrderModel;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.EmployerBean;
import it.univr.musiclovers.model.beans.OrderBean;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author Marian Solomon
 */
@ManagedBean
@RequestScoped
public class OrderController extends ControllerModel implements Serializable {

    private OrderBean orderBeans;
    private static final long serialVersionUID = 1L;

    public List<OrderBean> getOrder() throws SQLException {
        return OrderModel.getOrders();
    }

    public OrderBean getOrderBeans() {
        return orderBeans;
    }

    public void setOrderBeans(OrderBean orderBeans) {
        this.orderBeans = orderBeans;
    }

    public String getProduct(int orderID) {
        return addParam(normalizeUrl("order"), "orderID", String.valueOf(orderID));
    }

    public void removeOrder(int orderID) throws SQLException {

    }
    
    public List<EmployerBean> getEmployers() throws SQLException{
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
            

}
