package retrieve_timetable_use_case;

import java.util.List;

public class TimetableModel {

    private List<CourseModel> courses;
    private String sessionType;

    public TimetableModel(List<CourseModel> courses, String sessionType){
        this.courses = courses;
        this.sessionType = sessionType;
    }

    public List<CourseModel> getCourses() {
        return courses;
    }

    public String getSessionType() {return sessionType;}
}
