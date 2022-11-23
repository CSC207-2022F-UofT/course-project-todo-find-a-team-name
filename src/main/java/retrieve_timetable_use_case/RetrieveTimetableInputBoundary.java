package retrieve_timetable_use_case;

/**
 * The input boundary used in the RetrieveTimetable use case.
 * All implementations should be able to retrieve the data of the entities below and return the
 * corresponding model.
 */
public interface RetrieveTimetableInputBoundary {

    public CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    public CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);

    public TimetableModel retrieveTimetable();
    public SessionModel retrieveSession();
}
