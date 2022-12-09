package retrieve_timetable_use_case.application_business;

import display_timetable_use_case.frameworks_and_drivers.SessionViewModel;

/**
 * A view used in the RetrieveTimetable use case that holds and can update its session.
 */
public interface RetrieveTimetableView {
    void updateSession(SessionViewModel sessionViewModel);
}
