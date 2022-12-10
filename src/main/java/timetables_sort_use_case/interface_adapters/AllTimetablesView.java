package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

public interface AllTimetablesView {

    /**
     * Take in an array of TimeTableViewModels and display it
     * @param timetables the updated timetables that we want to present
     */
    void updateTimetables(TimetableViewModel[] timetables);
}
