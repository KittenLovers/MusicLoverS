package it.univr.musiclovers.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Marian Solomon
 */
public abstract class ControllerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    public static void exceptionHandler(SQLException exception) {
        for (Throwable throwable : exception) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, throwable);
        }
    }

    public static String retString(String target) {
        return target.concat("?faces-redirect=true");
    }

}
