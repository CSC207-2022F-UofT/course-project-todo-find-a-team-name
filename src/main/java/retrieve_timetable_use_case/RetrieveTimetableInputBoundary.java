package retrieve_timetable_use_case;

public interface RetrieveTimetableInputBoundary {

    CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);


}
