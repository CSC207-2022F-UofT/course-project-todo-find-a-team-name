package display_timetable_use_case.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

/**
 * Interface for DisplayTimetableOutputBoundary
 */
public interface DisplayTimetableOutputBoundary {

    /**
     * Format the timetableModel, so it can be displayed to the user
     *
     * @param timetableModel model containing information for the timetable
     */
    void prepareTimetable(TimetableModel timetableModel);

    /**
     * Format the message, so it can be displayed to the user
     * @param message error message
     */
    void prepareFailView(String message);
}
