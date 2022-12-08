package display_timetable_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

/**
 * Interface for UI component that displays the timetable
 */
public interface ITimetableUI {

    /**
     * Update the timetable in this UI from the given viewModel
     *
     * @param viewModel object storing all information needed for displaying timetable
     */
    void updateTimetable(TimetableViewModel viewModel);

    /**
     * Display the given message to the user
     *
     * @param message error message
     */
    void showTimetableFailView(String message);
}
