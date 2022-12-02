package display_timetable_use_case.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

public interface DisplayTimetableOutputBoundary {
    void prepareTimetable(TimetableModel timetableModel);
    void prepareFailView(String message);
}
