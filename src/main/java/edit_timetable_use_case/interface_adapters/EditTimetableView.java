package edit_timetable_use_case.interface_adapters;

import screens.TimetableViewModel;

public interface EditTimetableView {

    void updateTimetable(TimetableViewModel timetable);

    void displayResponse(String successMessage);
}
