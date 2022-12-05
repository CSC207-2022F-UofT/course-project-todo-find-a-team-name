package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.interface_adapters.TimetableViewModel;

public interface AllTimetablesView {
    void updateTimetables(TimetableViewModel[] timetables);
}
