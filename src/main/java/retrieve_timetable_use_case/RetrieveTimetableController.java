package retrieve_timetable_use_case;

public class RetrieveTimetableController {

    RetrieveTimetableInputBoundary interactor;

    public RetrieveTimetableController(RetrieveTimetableInputBoundary interactor){
        this.interactor = interactor;
    }

    public CourseModel retrieveCalendarCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveCalendarCourse(requestModel);
    }

    public CourseModel retrieveTimetableCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveTimetableCourse(requestModel);
    }

    public TimetableModel retrieveTimetable(){
        return interactor.retrieveTimetable();
    }
}
