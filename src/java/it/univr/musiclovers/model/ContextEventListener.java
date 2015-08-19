package it.univr.musiclovers.model;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 *
 * @author MarianClaudiu
 */
@WebListener
public class ContextEventListener implements ServletContextListener {

    /**
     *
     * @param event
     */
    @Override
    public void contextInitialized(ServletContextEvent event) {
        ConnectionModel.getInstance();
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        try {
            ConnectionModel.cleanUp();
        } catch (SQLException ex) {
            Logger.getLogger(ContextEventListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
