package timetable_generator_use_case;

import entities.CalendarCourse;
import entities.Timetable;
import entities.TimetableCourse;
import generate_timetable_course_use_case.TimetableCourseGenerator;

import java.util.*;

/**
 * Generates all possible Timetables
 */

public class TimetableGeneratorInteractor implements TimeTableGeneratorInputBoundary{
    /**
     * Returns an ArrayList of all possible timetables by checking every possible timetable course
     * for a given course against timetables in TimetableGeneratorRequestModel having an overlap with the class.
     * Only returns empty list of Timetables if there is only conflicts with given course and all given timeables.
     * @param course, TimetableGeneratorRequestModel
     * @return ArrayList<Timetable>
     */
    public ArrayList<Timetable> timetableCourseAdder(CalendarCourse course, TimetableGeneratorRequestModel timetables){
        ArrayList<Timetable> allTimetables = timetables.getTimetables();
        ArrayList<Timetable> newTimetables = new ArrayList<Timetable>();
        for(Timetable timetable : allTimetables){
            TimetableCourseGenerator possibleTimes = new TimetableCourseGenerator(course);
            for(TimetableCourse possibleTime: possibleTimes.generateAllTimetableCourse()){
                if (!timetable.hasCourseOverlap(possibleTime)){
                    ArrayList<TimetableCourse> currentCourses = timetable.getCourseList();
                    currentCourses.add(possibleTime);
                    newTimetables.add(new Timetable(currentCourses, timetable.getSessionType()));
                }
            }
        }
        return newTimetables;
    }
    /**
     * Recursively creates a list of timetables that has no conflict by "adding" each possible timetable Course
     * to each possible timetable and only keeping the timetables that have no conflict.
     * @param courses List<CalendarCourse> List of CalendarCourse
     * @return ArrayList<Timetable>
     */
    public ArrayList<Timetable> timetableGenerator(List<CalendarCourse> courses, TimetableGeneratorRequestModel timetables){
        if (courses.isEmpty())
            return timetables.getTimetables();
        else{
            ArrayList<Timetable> newTimetableList = timetableCourseAdder(courses.get(0), timetables);
            TimetableGeneratorRequestModel newTimetables = new TimetableGeneratorRequestModel(newTimetableList);
            return timetableGenerator(courses.subList(1, -1), newTimetables);
        }
    }

}
