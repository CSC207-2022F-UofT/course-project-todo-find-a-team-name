package timetable_generator_use_case.application_business;

import retrieve_timetable_use_case.CourseModel;

import java.util.ArrayList;

public class TimetableGeneratorRequestModel {
    /** Takes in an ArrayList of CourseModel**/
    private final ArrayList<CourseModel> courses;
    public TimetableGeneratorRequestModel(ArrayList<CourseModel> courses) {
        this.courses = courses;
    }
    public ArrayList<CourseModel> getCourses() {
        return this.courses;
    }
}