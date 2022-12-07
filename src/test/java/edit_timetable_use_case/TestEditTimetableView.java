package edit_timetable_use_case;

import screens.EditTimetableView;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

/**
 * A test view that mocks up EditTimetableView.
 */
public class TestEditTimetableView implements EditTimetableView {

    public String response;
    public TimetableViewModel timetable;

    @Override
    public void updateTimetable(TimetableViewModel timetable) {
        this.timetable = timetable;
    }

    @Override
    public void displayResponse(String successMessage) {
        this.response = successMessage;
    }
}
