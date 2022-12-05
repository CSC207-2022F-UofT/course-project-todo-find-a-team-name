package timetables_sort_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;

/**
 * The input boundary used in the timetables sort use case
 */
public interface TimetablesSortInputBoundary {
    /**
     * sorts timetables by the User's preferences
     * @param request a SorterRequestModel that contains the stringified version of preferences selected
     */
    void sort(TimetablesSortRequestModel request);

    void setTimetables(Timetable[] timetables);
    void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor);
}
