package screens;

import java.util.EventListener;

public interface TimetableViewEventListener extends EventListener {
    void courseClicked(TimetableViewEvent e);
}
