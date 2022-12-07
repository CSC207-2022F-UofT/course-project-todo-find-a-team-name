package screens;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

public interface EditTimetableView {

    void updateTimetable(TimetableViewModel timetable);

    void displayResponse(String successMessage);
}
