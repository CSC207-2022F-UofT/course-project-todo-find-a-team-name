package timetable_generator_use_case.application_business;

import java.util.HashMap;
import java.util.List;

public class TimetableGeneratorRequestModel {
    /** Takes in an ArrayList of CourseModel**/
    private final HashMap<String, List<String>> courses;

    public TimetableGeneratorRequestModel(HashMap<String, List<String>> courses) {
        this.courses = courses;
    }

    public HashMap<String, List<String>> getCourses() {
        return this.courses;
    }
}