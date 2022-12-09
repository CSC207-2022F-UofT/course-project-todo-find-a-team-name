package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

//import static org.junit.jupiter.api.Assertions.*;

class TimetableTest{
    @Test
        // Tests if an empty Timetable is able to be created
    void emptyTimetableSuccess(){
        ArrayList<TimetableCourse> emptyCourselist = new ArrayList<>();
        Timetable emptyTimetable1 = new Timetable(emptyCourselist, "F");
        assert emptyTimetable1.getCourseList().isEmpty();
    }
    @Test
        // Tests is a course can be added properly to a Timetable
    void addingToCourselist() throws InvalidSectionsException {
        ArrayList<TimetableCourse> emptyCourselist = new ArrayList<>();
        Timetable emptyTimetable2 = new Timetable(emptyCourselist, "F");
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
        Section s2 = new Section("TUT0102", "inst2", new ArrayList<>());
        TimetableCourse course1 = new TimetableCourse("Test Course",
                new ArrayList<>(List.of(s1, s2)),
                "F", "EGX101", "BR1");
        emptyTimetable2.addToCourseList(course1);
        assert emptyTimetable2.getCourseList().get(0) == course1;
    }
//    @Test
//    void testHasCourseOverlap() throws InvalidSectionsException
//        ArrayList<TimetableCourse> aCourseList = new ArrayList<>();
//        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
//        Section s2 = new Section("TUT0102", "inst2", new ArrayList<>());
//        TimetableCourse course1 = new TimetableCourse("Test Course",
//                new ArrayList<>(List.of(s1, s2)),
//                "F", "EGX101", "BR1");
//        aCourseList.add(course1);
//        Timetable timetable1 = new Timetable(aCourseList, "F");
//
//        Section s3 = new Section("LEC0101", "inst1", new ArrayList<>());
//        Section s4 = new Section("TUT0102", "inst2", new ArrayList<>());
//        TimetableCourse course2 = new TimetableCourse("Test Course",
//                new ArrayList<>(List.of(s3, s4)),
//                "F", "EGX101", "BR1");
//
//        assertTrue(timetable1.hasCourseOverlap(course2));
//

}