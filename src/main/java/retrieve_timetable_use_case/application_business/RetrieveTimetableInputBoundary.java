package retrieve_timetable_use_case.application_business;

import entities.Session;
import entities.Timetable;

/**
 * The input boundary used in the RetrieveTimetable use case.
 * All implementations should be able to retrieve the data of the entities below and return the
 * corresponding model.
 */
public interface RetrieveTimetableInputBoundary {

    CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);

    TimetableModel retrieveTimetable();

    SessionModel retrieveSession();

    void setSession(Session session);

    void setTimetable(Timetable timetable);
}
