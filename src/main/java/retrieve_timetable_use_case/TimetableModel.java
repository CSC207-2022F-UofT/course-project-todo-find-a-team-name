package retrieve_timetable_use_case;

import java.util.List;

public class TimetableModel {

    private List<CourseModel> courses;

    TimetableModel(List<CourseModel> courses){
        this.courses = courses;
    }

    public List<CourseModel> getCourses() {
        return courses;
    }
}
