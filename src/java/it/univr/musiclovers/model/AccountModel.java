package it.univr.musiclovers.model;

import it.univr.musiclovers.model.beans.AccountBean;
import java.io.Serializable;
import java.sql.SQLException;

/**
 *
 * @author Marian Solomon
 */
public class AccountModel extends Model implements Serializable {

    private static final long serialVersionUID = 1L;

    public static AccountBean getAccount(String username, String password) throws SQLException {
        AccountBean accountBean = null;
        try {
            accountBean = EmployerModel.getAccount(username, password);
            if (!(accountBean instanceof AccountBean)) {
                accountBean = CustomerModel.getAccount(username, password);
            }
        } catch (SQLException ex) {
            exceptionHandler(ex);
        }

        return accountBean;
    }

}
