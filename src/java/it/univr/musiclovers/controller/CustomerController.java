package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.CustomerModel;
import it.univr.musiclovers.model.beans.CustomerBean;
import it.univr.musiclovers.model.beans.ProfessionalBean;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author Luca Zidda
 */
@ManagedBean
@SessionScoped
public class CustomerController extends ControllerModel implements Serializable {

    private CustomerBean selectedCustomer;
    private static final long serialVersionUID = 1L;

    public String getCustomer(int customerID) {
        return addParam(normalizeUrl("customer"), "customerID", String.valueOf(customerID));
    }

    public List<CustomerBean> getCustomers() throws SQLException {
        return CustomerModel.getCustomers();
    }

    public List<ProfessionalBean> getProfessionals() throws SQLException {
        return CustomerModel.getProfessionals();
    }
    
    public List<Integer> getProfessionalIDs() throws SQLException {
        return CustomerModel.getProfessionalIDs();
    }

    public CustomerBean getSelectedCustomer() {
        return selectedCustomer;
    }

    public void setSelectedCustomer(int customerID) throws SQLException {
        this.selectedCustomer = CustomerModel.getCustomer(customerID);
    }

    public String processCustomerForm() throws SQLException {
        if (selectedCustomer.getId() > 0) {
            CustomerModel.editCustomer(selectedCustomer);
        } else {
            CustomerModel.insertCustomer(selectedCustomer);
        }
        return redirectString("index.xhtml");
    }

    public void removeCustomer(int customerID) throws SQLException {
        CustomerModel.removeCustomer(customerID);
    }

}
