package fileio_use_case.application_business.timetable_specific_classes;

import entities.Timetable;
import entities.TimetableCourse;

import java.util.ArrayList;
import java.util.HashMap;

/** Builds Session **/
public class TimetableBuilder {
    public TimetableBuilder() {
    }
    /**
     * Returns a Timetable if given the HashMap representation of all courses in Timetable
     *
     * @param allCourses - contains all courses from Timetable, String - session type (Fall (F), Winter (S))
     * @return Timetable
     */
    public Timetable aTimetableBuilder(HashMap<String, TimetableCourse> allCourses, String sessionType) {
        ArrayList<TimetableCourse> allTimetableCourses = new ArrayList<>(allCourses.values());
        return new Timetable(allTimetableCourses, sessionType);
    }
}