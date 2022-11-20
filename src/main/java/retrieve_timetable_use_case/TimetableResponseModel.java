package retrieve_timetable_use_case;

import entities.TimetableCourse;

import java.util.List;

public class TimetableResponseModel {

    private List<TimetableCourse> courses;

    TimetableResponseModel(List<TimetableCourse> courses){
        this.courses = courses;
    }

    public List<TimetableCourse> getCourses() {
        return courses;
    }
}
