package it.univr.musiclovers.model;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.AbstractMap.SimpleEntry;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marian Solomon
 */
public class ConnectionModel implements Serializable {

    private static HashMap<Integer, Map.Entry<Integer, Connection>> connectionPool = new HashMap<>();
    ;

    private static volatile ConnectionModel model;
    private static final Properties properties = new Properties();
    private static final long serialVersionUID = 1L;

    private ConnectionModel() {
        buildModel();
    }

    public final String getTablePrefix() {
        return properties.getProperty("table_prefix");
    }

    /**
     *
     * @throws Throwable
     */
    protected void cleanUp() throws Throwable {
        for (Entry<Integer, Entry<Integer, Connection>> connection : connectionPool.entrySet()) {
            connection.getValue().getValue().close();
        }
    }

    /**
     *
     * @return
     */
    protected final synchronized Connection getConnection() {
        int currentMinUsage = Integer.MAX_VALUE;
        int currentMin = 0;
        Connection connection = null;
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
                        Logger.getLogger(ConnectionModel.class.getName()).log(Level.SEVERE, null, throwable);
                    }
                }
            }
        }
        connection = connectionPool.get(currentMin).getValue();
        return connection;
    }

    private void buildModel() {
        ExternalContext externalContext = FacesContext.getCurrentInstance().getExternalContext();
        try (InputStream inputStream = externalContext.getResourceAsStream("/WEB-INF/config.properties")) {
            properties.load(inputStream);
        } catch (IOException ex) {
            Logger.getLogger(ConnectionModel.class.getName()).log(Level.SEVERE, null, ex);
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
                    Logger.getLogger(ConnectionModel.class.getName()).log(Level.SEVERE, null, throwable);
                }
            }
        }
    }

    /**
     *
     * @return
     */
    public static ConnectionModel getInstance() {
        if (model == null) {
            model = new ConnectionModel();
        }
        return model;
    }

}
