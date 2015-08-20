package it.univr.musiclovers.controller;

import java.io.IOException;
import java.io.Serializable;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

/**
 *
 * @author Marian Solomon
 */
public abstract class ControllerModel implements Serializable {

    private static final long serialVersionUID = 1L;

    protected ControllerModel() {
    }

    public static void exceptionHandler(SQLException exception) {
        redirect("errorpage");
        for (Throwable throwable : exception) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, throwable);
        }
    }

    public static void exceptionHandler(Exception exception) {
        redirect("errorpage");
        Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, exception);
    }

    public static void redirect(String target) {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        try {
            context.redirect(context.getApplicationContextPath()
                    .concat("/").concat(
                            target.concat(context.getInitParameter("it.univr.musiclovers.PAGE_EXT"))));
        } catch (IOException ex) {
            exceptionHandler(ex);
        }
    }

    public static String retString(String target) {
        return target.concat("?faces-redirect=true");
    }

}
