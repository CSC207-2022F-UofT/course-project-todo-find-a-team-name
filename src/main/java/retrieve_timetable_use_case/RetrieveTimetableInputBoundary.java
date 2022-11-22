package retrieve_timetable_use_case;

public interface RetrieveTimetableInputBoundary {

    public CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    public CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);

    public TimetableModel retrieveTimetable(RetrieveTimetableRequestModel requestModel);
    public SessionModel retrieveSession();
}
