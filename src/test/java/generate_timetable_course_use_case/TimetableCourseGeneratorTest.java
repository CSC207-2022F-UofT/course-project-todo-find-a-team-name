package generate_timetable_course_use_case;

import entities.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimetableCourseGeneratorTest {

    static Section lec0101;
    static Section lec0201;
    static Section tut0101;
    static Section tut0201;
    static Section pra0101;
    static Section pra0201;
    static CalendarCourse calCourse;

    @BeforeAll
    static void setUp(){
        List<Section> sections = new ArrayList<>();

        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "12:00", "13:00", ""));
        blocks1.add(new Block("TU", "14:00", "16:00", ""));
        lec0101 = new Section("LEC-0101", "inst1", blocks1);
        sections.add(lec0101);

        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "11:00", "14:00", ""));
        blocks2.add(new Block("WE", "12:30", "14:00", ""));
        lec0201 = new Section("LEC-0201", "inst1", blocks2);
        sections.add(lec0201);

        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("FR", "11:00", "12:00", ""));
        tut0101 = new Section("TUT-0101", "inst1", blocks3);
        sections.add(tut0101);

        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("TH", "11:00", "12:00", ""));
        tut0201 = new Section("TUT-0201", "inst1", blocks4);
        sections.add(tut0201);

        List<Block> blocks5 = new ArrayList<>();
        blocks5.add(new Block("MO", "18:00", "20:00", ""));
        pra0101 = new Section("PRA-0101", "inst1", blocks5);
        sections.add(pra0101);

        List<Block> blocks6 = new ArrayList<>();
        blocks6.add(new Block("MO", "10:00", "12:00", ""));
        pra0201 = new Section("PRA-0201", "inst1", blocks6);
        sections.add(pra0201);

        calCourse = new CalendarCourse("course", sections, "S",
                "COS111", "3");
    }
    @Test
    void testGenerateAllTimetableCoursesWithoutArgument() {
        List<TimetableCourse> expected = new ArrayList<>();

        List<Section> sections1 = new ArrayList<>();
        sections1.add(lec0101);
        sections1.add(tut0101);
        sections1.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections1, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections2 = new ArrayList<>();
        sections2.add(lec0101);
        sections2.add(tut0101);
        sections2.add(pra0201);
        try {
            expected.add(new TimetableCourse("course", sections2, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections3 = new ArrayList<>();
        sections3.add(lec0101);
        sections3.add(tut0201);
        sections3.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections3, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections4 = new ArrayList<>();
        sections4.add(lec0101);
        sections4.add(tut0201);
        sections4.add(pra0201);
        try {
            expected.add(new TimetableCourse("course", sections4, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections5 = new ArrayList<>();
        sections5.add(lec0201);
        sections5.add(tut0101);
        sections5.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections5, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections6 = new ArrayList<>();
        sections6.add(lec0201);
        sections6.add(tut0201);
        sections6.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections6, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
        List<TimetableCourse> actual = new TimetableCourseGenerator(calCourse).generateAllTimetableCourses();
        assertEquals(expected, actual);
    }

    @Test
    void testGenerateAllTimetableCoursesWithArgument() {
        List<TimetableCourse> expected = new ArrayList<>();

        List<Section> sections1 = new ArrayList<>();
        sections1.add(lec0101);
        sections1.add(tut0101);
        sections1.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections1, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> sections3 = new ArrayList<>();
        sections3.add(lec0201);
        sections3.add(tut0101);
        sections3.add(pra0101);
        try {
            expected.add(new TimetableCourse("course", sections3, "S", "COS111", "3"));
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        List<Section> lectures = new ArrayList<>();
        lectures.add(lec0101);
        lectures.add(lec0201);

        List<Section> tutorials = new ArrayList<>();
        tutorials.add(tut0101);

        List<Section> practicals = new ArrayList<>();
        practicals.add(pra0101);

        List<TimetableCourse> actual =
                new TimetableCourseGenerator(calCourse).generateAllTimetableCourses(lectures, tutorials, practicals);
        assertEquals(expected, actual);
    }
}