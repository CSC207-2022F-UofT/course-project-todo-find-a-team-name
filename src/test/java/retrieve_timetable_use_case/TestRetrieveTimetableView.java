package retrieve_timetable_use_case;

import retrieve_timetable_use_case.application_business.RetrieveTimetableView;
import display_timetable_use_case.frameworks_and_drivers.SessionViewModel;

/**
 * A mock view used to simulate a RetrieveTimetableView object.
 */
public class TestRetrieveTimetableView implements RetrieveTimetableView {
    public SessionViewModel session;

    /**
     * @param sessionViewModel the current session's view model.
     */
    @Override
    public void updateSession(SessionViewModel sessionViewModel) {
        this.session = sessionViewModel;
    }
}
