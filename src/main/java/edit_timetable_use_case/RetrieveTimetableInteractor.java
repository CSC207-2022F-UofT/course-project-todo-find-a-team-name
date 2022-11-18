package edit_timetable_use_case;

public class RetrieveTimetableInteractor implements RetrieveTimetableInputBoundary {

    private Timetable timetable;
    private Session session;

    public RetrieveTimetableInteractor(Timetable timetable, Session session){
        this.timetable = timetable;
        this.session = session;
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public TimetableResponseModel retrieveTimetable(RetrieveTimetableRequestModel requestModel) {
        TimetableResponseModel responseModel = new TimetableResponseModel();

        return null;
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return null;
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return null;
    }
}
