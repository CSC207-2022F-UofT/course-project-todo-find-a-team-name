package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

public class AllTimetablesController {
    DisplayTimetableInputBoundary interactor;
    AllTimetablesInputBoundary publisher;
    public AllTimetablesController(DisplayTimetableInputBoundary interactor, AllTimetablesInputBoundary publisher) {
        this.interactor = interactor;
        this.publisher = publisher;
    }
    public void setTTUI(TimetableViewModel timetableViewModel) {

    }
}
