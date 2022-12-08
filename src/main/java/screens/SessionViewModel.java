package screens;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewCourseModel;

import java.util.HashMap;

public class SessionViewModel {

    private HashMap<String, TimetableViewCourseModel> courses;
    private String sessionType;

    public SessionViewModel(HashMap<String, TimetableViewCourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    public HashMap<String, TimetableViewCourseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {
        return sessionType;
    }
}
