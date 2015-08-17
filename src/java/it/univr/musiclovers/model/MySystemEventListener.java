package it.univr.musiclovers.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.Application;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.PostConstructApplicationEvent;
import javax.faces.event.PreDestroyApplicationEvent;
import javax.faces.event.SystemEvent;
import javax.faces.event.SystemEventListener;

/**
 *
 * @author Marian Solomon
 */
public class MySystemEventListener implements SystemEventListener {

    @Override
    public void processEvent(SystemEvent event) throws AbortProcessingException {

        if (event instanceof PostConstructApplicationEvent) {
            System.out.println("PostConstructApplicationEvent is Called");
        }

        if (event instanceof PreDestroyApplicationEvent) {
            try {
                ConnectionModel.getInstance().cleanUp();
            } catch (Throwable ex) {
                Logger.getLogger(MySystemEventListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    @Override
    public boolean isListenerForSource(Object source) {
        //only for Application
        return (source instanceof Application);

    }

}
