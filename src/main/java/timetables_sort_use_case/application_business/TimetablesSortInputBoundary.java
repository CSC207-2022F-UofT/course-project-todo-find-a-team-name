package timetables_sort_use_case.application_business;

import entities.Timetable;

/**
 * The input boundary used in the timetables sort use case
 */
public interface TimetablesSortInputBoundary {

    /**
     * sorts the timetables based on the request model and updates the view accordingly
     * @param request a TimetablesSortRequestModel that contains the stringified version of TimeButton and BreakButton
     */
    void timetablesSort(TimetablesSortRequestModel request);

    void setTimetables(Timetable[] timetables);
}
