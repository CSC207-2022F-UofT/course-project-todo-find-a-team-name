package timetables_sort_use_case.application_business;

/**
 * The input boundary used in the TimetablesSort use case.
 */
public interface AllTimetablesInputBoundary {

    /**
     * @param i the index of the timetable that was chosen
     */
    void updateSubscribers(int i);
}
