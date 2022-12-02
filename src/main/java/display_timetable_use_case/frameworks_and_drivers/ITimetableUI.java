package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.TimetableViewModel;

public interface ITimetableUI {

    void updateTimetable(TimetableViewModel viewModel);
    void showTimetableFailView(String message);
}
