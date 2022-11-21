package retrieve_timetable_use_case;

import entities.CalendarCourse;

import java.util.HashMap;

public class SessionResponseModel {

    private HashMap<String, CourseResponseModel> courses;
    private String sessionType;

    public SessionResponseModel(HashMap<String, CourseResponseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    public HashMap<String, CourseResponseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {
        return sessionType;
    }
}
