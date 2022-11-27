package timetable_generator_use_case;

import entities.CalendarCourse;
import entities.Timetable;

import java.util.ArrayList;
import java.util.List;

public interface TimeTableGeneratorInputBoundary {
    /**
     * Returns an ArrayList of all possible timetables by checking every possible timetable course
     * for a given course against timetables in TimetableGeneratorRequestModel having an overlap with the class.
     * Only returns empty list of Timetables if there is only conflicts with given course and all given timeables.
     * @param course, TimetableGeneratorRequestModel
     * @return ArrayList<Timetable>
     */
    ArrayList<Timetable> timetableCourseAdder(CalendarCourse course, TimetableGeneratorRequestModel timetables);
    ArrayList<Timetable> timetableGenerator(List<CalendarCourse> courses, TimetableGeneratorRequestModel timetables);
}
