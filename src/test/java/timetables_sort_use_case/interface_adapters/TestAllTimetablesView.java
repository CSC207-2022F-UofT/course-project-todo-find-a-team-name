package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;

/**
 * A test view that mocks up AllTimetablesView.
 * timetables is the TimetableViewModel array passed to the view.
 */
public class TestAllTimetablesView implements AllTimetablesView {

    public TimetableViewModel[] timetables;

    @Override
    public void updateTimetables(TimetableViewModel[] timetables) {
        this.timetables = timetables;
    }
}
