package retrieve_timetable_use_case;

public class RetrieveTimetableController {

    RetrieveTimetableInputBoundary interactor;

    public RetrieveTimetableController(RetrieveTimetableInputBoundary interactor){
        this.interactor = interactor;
    }

    public CourseResponseModel retrieveCalendarCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveCalendarCourse(requestModel);
    }

    public CourseResponseModel retrieveTimetableCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveTimetableCourse(requestModel);
    }

    public TimetableResponseModel retrieveTimetable(String timetable){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel(timetable, "", "");
        return interactor.retrieveTimetable();
    }
}
