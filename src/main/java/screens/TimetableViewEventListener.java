package screens;

import java.util.EventListener;

/**
 * Observer of the TimetableView that get notified when user clicks course section in the
 * TimetableView
 */
public interface TimetableViewEventListener extends EventListener {

    /**
     * Process course section clicking event performed by the user
     *
     * @param e event occurred
     */
    void courseClicked(TimetableViewEvent e);
}
