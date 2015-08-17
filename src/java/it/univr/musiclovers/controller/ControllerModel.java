package it.univr.musiclovers.controller;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Marian Solomon
 */
public abstract class ControllerModel {

    private static final long serialVersionUID = 1L;

    /**
     * Utility method to handle all the exceptions that are fired in the servlet
     * implementations
     *
     * @param exception
     * @param request
     * @param response
     */
    protected static void exceptionHandler(Exception exception, HttpServletRequest request, HttpServletResponse response) {
        Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, exception);
        forward("error_page.jsf", request, response);
    }

    /**
     * Utility method to forward a httprequest
     *
     * @param next
     * @param request
     * @param response
     */
    protected static void forward(String next, HttpServletRequest request, HttpServletResponse response) {
        try {
            request.getRequestDispatcher(next).forward(request, response);
        } catch (ServletException | IOException ex) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Utility method to redirect a httprequest
     *
     * @param target
     * @param request
     * @param response
     */
    protected static void redirect(String target, HttpServletRequest request, HttpServletResponse response) {
        try {
            response.sendRedirect(request.getContextPath() + '/' + target);
        } catch (IOException ex) {
            Logger.getLogger(ControllerModel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
