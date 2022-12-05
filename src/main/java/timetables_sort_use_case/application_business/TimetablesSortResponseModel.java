package timetables_sort_use_case.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

/**
 * A response model that contains the relevant information needed by the presenter's view.
 * Timetables is a Timetable
 */
public class TimetablesSortResponseModel {
    private final TimetableModel[] timetables;

    /**
     * @param timetables the array of timetables
     */
    public TimetablesSortResponseModel(TimetableModel[] timetables) {
        this.timetables = timetables;
    }

    /**
     * @return returns an array of TimetableModels
     */
    public TimetableModel[] getTimetables() {
        return timetables;
    }
}
