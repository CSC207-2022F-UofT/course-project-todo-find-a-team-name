package screens;

import display_timetable_use_case.interface_adapters.TimetableViewModel;

public interface EditTimetableView {

    void updateTimetable(TimetableViewModel timetable);

    void displayResponse(String successMessage);
}
