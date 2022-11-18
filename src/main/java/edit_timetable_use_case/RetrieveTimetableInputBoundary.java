package edit_timetable_use_case;

public interface RetrieveTimetableInputBoundary {

    TimetableResponseModel retrieveTimetable(RetrieveTimetableRequestModel requestModel);

    CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel);

    CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel);


}
