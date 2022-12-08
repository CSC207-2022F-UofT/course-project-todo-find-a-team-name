package retrieve_timetable_use_case.interface_adapters;

import retrieve_timetable_use_case.application_business.*;

/**
 * The controller used in the RetrieveTimetableUseCase.
 * interactor is the interactor associated with the RetrieveTimetableUseCase.
 */
public class RetrieveTimetableController {

    RetrieveTimetableInputBoundary interactor;

    public RetrieveTimetableController(RetrieveTimetableInputBoundary interactor){
        this.interactor = interactor;
    }

    /**
     * @param courseCode The course code of a course in the Session currently loaded in
     *                   the interactor.
     * @return a CourseModel containing the course's data.
     */
    public CourseModel retrieveCalendarCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveCalendarCourse(requestModel);
    }

    /**
     * @param courseCode The course code of a course in the Timetable currently loaded in
     *                   the interactor.
     * @return a CourseModel containing the course's data.
     */
    public CourseModel retrieveTimetableCourse(String courseCode){
        RetrieveTimetableRequestModel requestModel =
                new RetrieveTimetableRequestModel("", courseCode, "");
        return interactor.retrieveTimetableCourse(requestModel);
    }

    /**
     * @return a TimetableModel containing all of the timetable's data.
     */
    public TimetableModel retrieveTimetable(){
        return interactor.retrieveTimetable();
    }

    public SessionModel retrieveSession() {
        return interactor.retrieveSession();
    }
}
