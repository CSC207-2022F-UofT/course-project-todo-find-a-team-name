package retrieve_timetable_use_case;

import entities.Course;
import entities.Session;
import entities.Timetable;

import java.util.ArrayList;

/**
 * The main interactor used in the retrieve timetable use case. It uses EntityConverter's methods
 * to accept a timetable/session data entity and convert it into the corresponding model, which
 * can then be used and passed between controllers and presenters as necessary.
 * timetable refers to the Timetable currently loaded into the interactor, and any retrieveTimetableCourse
 * calls will search through timetable.
 * session similarly refers to the Session currently into the interavtor, and retrieveCalendarCourse
 * calls will search through session.
 */
public class RetrieveTimetableInteractor implements RetrieveTimetableInputBoundary {

    private Timetable timetable;
    private Session session;

    public RetrieveTimetableInteractor(Timetable timetable, Session session){
        this.timetable = timetable;
        this.session = session;
    }


    /**
     * @param requestModel contains the course code of the desired course in timetable.
     * @return a CourseModel of the given course containing equivalent information.
     */
    @Override
    public CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(timetable.getCourse(requestModel.getCourseCode()));
    }

    /**
     * @param requestModel contains the course code of the desired course in session.
     * @return a CourseModel of the given course containing equivalent information.
     */
    @Override
    public CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(session.getCalendarCourse(requestModel.getCourseCode()));
    }

    /**
     * @return a SessionModel containing all information in session.
     */
    @Override
    public SessionModel retrieveSession(){
        return EntityConverter.generateSessionResponse(session);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * @return a TimetableModel containing all information in timetable.
     */
    @Override
    public TimetableModel retrieveTimetable(){
        ArrayList<CourseModel> courses = new ArrayList<>();
        for (Course course : timetable.getCourseList()){
            courses.add(EntityConverter.generateCourseResponse(course));
        }
        return new TimetableModel(courses);
    }


}
