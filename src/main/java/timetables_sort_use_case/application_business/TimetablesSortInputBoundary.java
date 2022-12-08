package timetables_sort_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;

/**
 * The input boundary used in the timetables sort use case
 */
public interface TimetablesSortInputBoundary {

    /**
     * @param request a SorterRequestModel that contains the stringified version of preferences selected
     */
    void timetablesSort(TimetablesSortRequestModel request);

    /**
     * @param timetables
     */
    void setTimetables(Timetable[] timetables);
}
