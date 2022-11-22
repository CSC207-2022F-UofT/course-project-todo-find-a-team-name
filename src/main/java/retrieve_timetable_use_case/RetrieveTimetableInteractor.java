package retrieve_timetable_use_case;

import entities.Course;
import entities.Session;
import entities.Timetable;

import java.util.ArrayList;

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

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(timetable.getCourse(requestModel.getCourseCode()));
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(session.getCalendarCourse(requestModel.getCourseCode()));
    }

    @Override
    public SessionModel retrieveSession(){
        return EntityConverter.generateSessionResponse(session);
    }

    @Override
    public TimetableModel retrieveTimetable(RetrieveTimetableRequestModel requestModel){
        ArrayList<CourseModel> courses = new ArrayList<CourseModel>();
        for (Course course : timetable.getCourseList()){
            courses.add(EntityConverter.generateCourseResponse(course));
        }
        return new TimetableModel(courses);
    }


}
