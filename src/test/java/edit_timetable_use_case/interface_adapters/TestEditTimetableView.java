package edit_timetable_use_case.interface_adapters;

import screens.TimetableViewModel;
import screens.EditTimetableView;
import display_timetable_use_case.interface_adapters.TimetableViewModel;

/**
 * A test view that mocks up EditTimetableView.
 * response is used to store the message the presenter passes to the View.
 * timetable is the TimetableViewModel passed to the view.
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
