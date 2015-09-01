package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.OrderModel;
import it.univr.musiclovers.model.beans.OrderBean;
import java.io.Serializable;
import java.sql.SQLException;
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

}
