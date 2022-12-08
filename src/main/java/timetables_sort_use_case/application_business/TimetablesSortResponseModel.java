package timetables_sort_use_case.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

/**
 * A response model that contains the relevant information needed by the presenter's view.
 * Instance Variables:
 * - timetables: the timetables that we will use to update view
 */
public record TimetablesSortResponseModel(TimetableModel[] timetables) {
}
