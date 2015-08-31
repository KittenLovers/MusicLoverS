package it.univr.musiclovers.model;

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
        ConnectionModel.cleanUp();
    }

}
