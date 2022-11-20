package retrieve_timetable_use_case;

import entities.Course;
import entities.TimetableCourse;

import java.util.List;

public class TimetableResponseModel {

    private List<CourseResponseModel> courses;

    TimetableResponseModel(List<CourseResponseModel> courses){
        this.courses = courses;
    }

    public List<CourseResponseModel> getCourses() {
        return courses;
    }
}
