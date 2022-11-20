package retrieve_timetable_use_case;

import entities.Block;
import entities.Course;
import entities.Section;
import entities.Session;
import entities.Timetable;

import java.util.ArrayList;
import java.util.HashMap;

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
    public CourseResponseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(timetable.getCourse(requestModel.getCourseCode()));
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public CourseResponseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(session.getCalendarCourse(requestModel.getCourseCode()));
    }

    @Override
    public SessionResponseModel retrieveSession(){
        return EntityConverter.generateSessionResponse(session);
    }

    @Override
    public TimetableResponseModel retrieveTimetable(RetrieveTimetableRequestModel requestModel){
        ArrayList<CourseResponseModel> courses = new ArrayList<CourseResponseModel>();
        for (Course course : timetable.getCourseList()){
            courses.add(EntityConverter.generateCourseResponse(course));
        }
        return new TimetableResponseModel(courses);
    }


}
