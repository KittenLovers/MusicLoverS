package it.univr.musiclovers.model;

import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;
import java.sql.DriverManager;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javax.faces.context.ExternalContext;

/**
 *
 * @author Marian Solomon
 */
public class ConnectionModel {

    private static final long serialVersionUID = 1L;

    private final Properties properties = new Properties();
    private static ConnectionModel model;
    private HashMap<Integer, Map.Entry<Integer, Connection>> connectionPool;

    private ConnectionModel() {
        buildModel();
    }

    public static ConnectionModel getInstance() {
        if (!(model instanceof ConnectionModel)) {
            model = new ConnectionModel();
        }
        return model;
    }

    public synchronized final Connection getConnection() throws NullPointerException {
        int currentMinUsage = Integer.MAX_VALUE;
        int currentMin = 0;

        for (Map.Entry<Integer, Map.Entry<Integer, Connection>> entrySet : connectionPool.entrySet()) {
            int id = entrySet.getKey();
            Entry<Integer, Connection> value = entrySet.getValue();
            if (value.getKey() < currentMinUsage) {
                try {
                    if (!value.getValue().isClosed()) {
                        currentMin = id;
                        currentMinUsage = value.getKey();
                    }
                } catch (SQLException ex) {
                    for (Throwable throwable : ex) {
                        System.err.println(throwable);
                    }
                }
            }
        }
        return connectionPool.get(currentMin).getValue();
    }

    protected void cleanUp() throws Throwable {
        for (Entry<Integer, Entry<Integer, Connection>> connection : connectionPool.entrySet()) {
            connection.getValue().getValue().close();
        }
    }

    public final String getTablePrefix() {
        return properties.getProperty("table_prefix");
    }

    private void buildModel() {
        try {
            ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
            properties.load(externalContext.getResourceAsStream("/WEB-INF/config.properties"));
        } catch (IOException exception) {
            Logger.getLogger(ConnectionModel.class.getName()).log(Level.SEVERE, null, exception);
        }
        try {
            Class.forName(properties.getProperty("dbmanager.driver"));
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ConnectionModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        int poolSize = Integer.parseInt(properties.getProperty("db.pool.size"));
        connectionPool = new HashMap<>(poolSize);

        for (int i = 0; i < poolSize; i++) {
            try {
                connectionPool.put(i, new SimpleEntry<>(0, DriverManager.getConnection(
                        properties.getProperty("dburl") + '/' + properties.getProperty("dbname"), //host
                        properties.getProperty("username"),
                        properties.getProperty("password"))
                ));
            } catch (SQLException ex) {
                for (Throwable throwable : ex) {
                    System.err.println(throwable);
                }
            }
        }
    }

}
