package it.univr.musiclovers.model;

import java.sql.Connection;

/**
 *
 * @author Marian Solomon
 */
public abstract class Model {

    public static Connection getConnection() {
        return ConnectionModel.getConnection();
    }

    public static String getTablePrefix() {
        return ConnectionModel.getTablePrefix();
    }

}
