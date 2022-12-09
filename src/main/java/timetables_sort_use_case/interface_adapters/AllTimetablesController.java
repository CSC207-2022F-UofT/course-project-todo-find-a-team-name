package timetables_sort_use_case.interface_adapters;

import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

/**
 * The controller responsible for subscribing to AllTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    final AllTimetablesInputBoundary allTimetablesInteractor;

    public AllTimetablesController(AllTimetablesInputBoundary allTimetablesInteractor) {
        this.allTimetablesInteractor = allTimetablesInteractor;
    }

    /**
     * publish the selected timetable to subscribers
     * @param i the index of the timetable that was selected
     */
    public void updateSubscribers(int i) {
        allTimetablesInteractor.updateSubscribers(i);
    }

}
