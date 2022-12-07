package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

public interface AllTimetablesView {
    void updateTimetables(TimetableViewModel[] timetables);
}
