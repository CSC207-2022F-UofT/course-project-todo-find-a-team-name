package timetable_generator_use_case.application_business;

import entities.InvalidSectionsException;
import entities.Timetable;
import entities.TimetableCourse;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.TimetableModel;
import timetable_generator_use_case.application_business.CourseConverter;

import java.util.ArrayList;

/**
 * This is a Helper class for the TimetableGeneratorInteractor
 * to convert a timetable back and forth from TimetableModel to Timetable and vice versa.
 */
public class TimetableConverter {
    public Timetable timetableModelToTimetable(TimetableModel model, String sessionType) throws InvalidSectionsException {
        ArrayList<TimetableCourse> allCourses= new ArrayList<>();
        for (CourseModel course : model.getCourses()) {
            CourseConverter toConvert = new CourseConverter();
            TimetableCourse tempCalCourse = toConvert.courseModelToTimetableCourse(course);
            allCourses.add(tempCalCourse);
        }
        return new Timetable(allCourses, sessionType);
    }
}
