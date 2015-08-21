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

    private static final ExternalContext CONTEXT = FacesContext.getCurrentInstance().getExternalContext();
    private static final String FILE_EXT = CONTEXT.getInitParameter("it.univr.musiclovers.PAGE_EXT");
    private static final long serialVersionUID = 1L;

    protected ControllerModel() {
    }

    public static String addExt(String target) {
        return target.concat(FILE_EXT);
    }

    public static String addParam(String target, String key, String value) {
        char delim = (target.contains("?") ? '&' : '?');
        return target + delim + key + '=' + value;
    }

    public static void exceptionHandler(SQLException exception) throws IOException {
        redirect("errorpage");
        for (Throwable throwable : exception) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, throwable);
        }
    }

    public static void exceptionHandler(Exception exception) throws IOException {
        redirect("errorpage");
        Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, exception);
    }

    public static void redirect(String target) throws IOException {
        CONTEXT.redirect(CONTEXT.getApplicationContextPath().concat("/").concat(redirectString(target)));
    }

    public static String redirectString(String target) {
        String ret = target;
        if (!ret.contains(FILE_EXT)) {
            ret = ret.concat(FILE_EXT);
        }
        return addParam(ret, "faces-redirect", "true");
    }

}
