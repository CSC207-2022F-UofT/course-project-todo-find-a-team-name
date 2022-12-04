package timetable_generator_use_case.application_business;

import entities.*;
import generate_timetable_course_use_case.TimetableCourseGenerator;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.TimetableModel;

import java.util.*;

/**
 * Generates all possible Timetables
 */

public class TimetableGeneratorInteractor implements TimetableGeneratorInputBoundary {
    private String sessionType;
    public TimetableGeneratorInteractor() {
        this.sessionType = "";
    }
    /** Helper Function: Converts ArrayList of CourseModels */
    public TimetableGeneratorResponseModel generateTimetable(TimetableGeneratorRequestModel lst, String sessionType) throws InvalidSectionsException {
        TimetableModel emptyTimetable =
                new TimetableModel(new ArrayList<CourseModel>());
        // Store sessionType
        this.sessionType = sessionType;
        // Create ArrayList of Empty TimetableModels
        ArrayList<TimetableModel> timetables = new ArrayList<>();
        timetables.add(emptyTimetable);
        // Call timetableGenerator method passing in lst and empty ArrayList TimetableModels.
        ArrayList<TimetableModel> generatedTimetables = timetableGenerator(lst.getCourses(), timetables);
        return new TimetableGeneratorResponseModel(generatedTimetables);
    }
    /**
     *
     * Returns a TimetableGeneratorResponseModel of all possible timetables (TimetableModel)
     * by checking every possible timetable course for a given course against timetables in
     * TimetableGeneratorRequestModel having an overlap with the class.
     * Only returns empty list of Timetables if there is only conflicts with
     * given course and all given timetables.
     * @param course, TimetableGeneratorRequestModel
     * @return ArrayList<TimetableModel>
     */
    public ArrayList<TimetableModel> timetableCourseAdder(CourseModel course, ArrayList<TimetableModel> timetables) throws InvalidSectionsException {
        ArrayList<TimetableModel> newTimetables = new ArrayList<>();
        for(TimetableModel timetable : timetables){
            CourseConverter toConvert = new CourseConverter();
            CalendarCourse tempCalCourse = toConvert.courseModelToCalendarCourse(course);
            TimetableCourseGenerator possibleTimes = new TimetableCourseGenerator(tempCalCourse);
            TimetableConverter toConvertTimetable = new TimetableConverter();
            Timetable tempTimetable = toConvertTimetable.timetableModelToTimetable(timetable, this.sessionType);
            for(TimetableCourse possibleTime: possibleTimes.generateAllTimetableCourse()){
                if (!(tempTimetable.hasCourseOverlap(possibleTime))){
                    List<CourseModel> currentCourses = timetable.getCourses();
                    ArrayList<CourseModel> currentCoursesNew = (ArrayList<CourseModel>) currentCourses;
                    CourseModel possibleTimeConverted = toConvert.timetableCourseToCourseModel(possibleTime);
                    currentCoursesNew.add(possibleTimeConverted);
                    newTimetables.add(new TimetableModel(currentCoursesNew));
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
    public ArrayList<TimetableModel> timetableGenerator(ArrayList<CourseModel> courses, ArrayList<TimetableModel> timetables) throws InvalidSectionsException {
        if (courses.isEmpty())
            return timetables;
        else{
            ArrayList<TimetableModel> newTimetableList = timetableCourseAdder(courses.get(0), timetables);
            return timetableGenerator((ArrayList<CourseModel>) courses.subList(1, -1), newTimetableList);
        }
    }

}
