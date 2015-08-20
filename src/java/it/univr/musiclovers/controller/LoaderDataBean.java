package it.univr.musiclovers.controller;

import it.univr.musiclovers.model.Model;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author MarianClaudiu
 */
@ManagedBean
@RequestScoped
public class LoaderDataBean extends Model {

    private static void executeQuery(final String query) {
        try (Statement statement = getConnection().createStatement()) {
            statement.executeUpdate(query);
        } catch (SQLException ex) {
            Logger.getLogger(LoaderDataBean.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void loadData() {
        executeQuery(loadQuery("../resource/sql/create_tables.sql"));
        executeQuery(loadQuery("../resource/sql/insert_account.sql"));
        executeQuery(loadQuery("../resource/sql/insert_customer.sql"));
        executeQuery(loadQuery("../resource/sql/insert_employer.sql"));
        executeQuery(loadQuery("../resource/sql/insert_brand.sql"));
        executeQuery(loadQuery("../resource/sql/insert_professional.sql"));
        executeQuery(loadQuery("../resource/sql/insert_product.sql"));
    }

    private String loadQuery(String file) {
        final StringBuilder builder = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource(file).getFile()))) {
            String line = br.readLine();
            for (int i = 0; line != null; line = br.readLine(), i++) {
                builder.append(line);
            }
        } catch (IOException ex) {
            Logger.getLogger(LoaderDataBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        return builder.toString();
    }
}
