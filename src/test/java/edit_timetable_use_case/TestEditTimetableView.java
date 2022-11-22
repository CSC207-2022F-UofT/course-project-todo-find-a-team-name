package edit_timetable_use_case;

import screens.EditTimetableView;
import screens.TimetableViewModel;

/**
 * A test view that mocks up EditTimetableView.
 */
public class TestEditTimetableView implements EditTimetableView {

    public String response;
    public TimetableViewModel timetable;
    /**
     * @param timetable
     */
    @Override
    public void updateTimetable(TimetableViewModel timetable) {
        this.timetable = timetable;
    }

    /**
     * @param successMessage
     */
    @Override
    public void displayResponse(String successMessage) {
        this.response = successMessage;
    }
}