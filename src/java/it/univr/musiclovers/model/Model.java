package it.univr.musiclovers.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marian Solomon
 */
public abstract class Model {

    public static void exceptionHandler(SQLException exception) throws SQLException {
        exceptionHandlerWithoutThrow(exception);
        throw exception;
    }

    public static void exceptionHandlerWithoutThrow(SQLException exception) {
        for (Throwable throwable : exception) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, throwable);
        }
    }

    public static Connection getConnection() {
        return ConnectionModel.getInstance().getConnection();
    }

    public static String getTablePrefix() {
        return ConnectionModel.getInstance().getTablePrefix();
    }

}
