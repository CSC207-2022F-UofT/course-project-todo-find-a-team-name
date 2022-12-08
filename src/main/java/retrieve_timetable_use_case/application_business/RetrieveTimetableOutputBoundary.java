package retrieve_timetable_use_case.application_business;

/**
 * The output boundary used in the RetrieveTimetable use case.
 * Must be able to update a view's session.
 */
public interface RetrieveTimetableOutputBoundary {
    void updateSession(SessionModel sessionModel);
}
