package retrieve_timetable_use_case;

public interface RetrieveTimetableInputBoundary {

    public CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    public CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);

    public TimetableResponseModel retrieveTimetable(RetrieveTimetableRequestModel requestModel);
    public SessionResponseModel retrieveSession();
}
