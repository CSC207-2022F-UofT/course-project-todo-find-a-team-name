package retrieve_timetable_use_case;

import java.util.HashMap;

public class SessionModel {

    private HashMap<String, CourseModel> courses;
    private String sessionType;

    public SessionModel(HashMap<String, CourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    public HashMap<String, CourseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {
        return sessionType;
    }
}
