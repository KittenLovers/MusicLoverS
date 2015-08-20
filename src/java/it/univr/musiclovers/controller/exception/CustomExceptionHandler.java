package it.univr.musiclovers.controller.exception;

import it.univr.musiclovers.controller.ControllerModel;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.FacesException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;

/**
 *
 * @author Marian Solomon
 */
public class CustomExceptionHandler extends ExceptionHandlerWrapper {

    private final ExceptionHandler wrapped;

    public CustomExceptionHandler(ExceptionHandler wrapped) {
        this.wrapped = wrapped;
    }

    /**
     *
     * @return
     */
    @Override
    public ExceptionHandler getWrapped() {
        return wrapped;
    }

    /**
     *
     * @throws FacesException
     */
    @Override
    public void handle() throws FacesException {
        Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents().iterator();
        while (i.hasNext()) {
            ExceptionQueuedEvent event = i.next();
            ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event.getSource();
            Throwable throwable = context.getException();
            try {
                Logger.getLogger(CustomExceptionHandler.class.getName()).log(Level.SEVERE, null, throwable);
                ControllerModel.redirect("error");
            } finally {
                i.remove();
            }
        }
        getWrapped().handle();
    }
}
