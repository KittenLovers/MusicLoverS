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

    private static final String FILE_EXT = FacesContext.getCurrentInstance().getExternalContext().getInitParameter("it.univr.musiclovers.PAGE_EXT");
    private static final long serialVersionUID = 1L;

    public static String addExt(String target) {
        return target.concat(FILE_EXT);
    }

    public static String addParam(String target, String key, String value) {
        char delim = (target.contains("?") ? '&' : '?');
        return target + delim + key + '=' + value;
    }

    public static void exceptionHandler(SQLException exception) {
        try {
            redirect("errorpage");
        } catch (IOException ex) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
        for (Throwable throwable : exception) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, throwable);
        }
    }

    public static Object getSessionMapValue(String key) {
        return FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get(key);
    }

    public static String normalizeUrl(String target) {
        String ret = target;
        if (ret.endsWith("/")) {
            ret = ret.concat("index".concat(FILE_EXT));
        }
        if (!ret.contains(FILE_EXT)) {
            ret = ret.concat(FILE_EXT);
        }
        return ret;
    }

    public static void redirect(String target) throws IOException {
        ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
        context.redirect(context.getApplicationContextPath().concat("/").concat(normalizeUrl(target)));
    }

    public static String redirectString(String target) {
        String ret = normalizeUrl(target);
        return addParam(ret, "faces-redirect", "true");
    }

    public static void setSessionMapValue(String key, Object value) {
        FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put(key, value);
    }
}
