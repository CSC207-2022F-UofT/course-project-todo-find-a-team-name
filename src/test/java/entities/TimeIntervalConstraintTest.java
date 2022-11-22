package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TimeIntervalConstraintTest {
    @Test
    void filterWithBlackListRemoveAllSection() {
        //blocks1
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
        //blocks2
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));
        //blocks3
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));
        //blocks4
        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));
        //blocks5
        List<Block> blocks5 = new ArrayList<>();
        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks5.add(new Block("WE", "16:30", "18:00", "room3"));
        //blocks6
        List<Block> blocks6 = new ArrayList<>();
        blocks6.add(new Block("FR", "11:30", "12:30", "room3"));

        //creating 6 corresponding section containing those 6 blocks.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);
        Section section3 = new Section("PRA-0301", "inst3", blocks3);
        Section section4 = new Section("LEC-0201", "inst4", blocks4);
        Section section5 = new Section("TUT-0402", "inst5", blocks5);
        Section section6 = new Section("LEC-0509", "inst6", blocks6);

        //Making 2 seperate sections to build CalendarCourse.
        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);

        List<Section> sections2 = new ArrayList<>();
        sections2.add(section3);
        sections2.add(section4);
        sections2.add(section5);
        sections2.add(section6);

        //emptySection for testing purpose
        List<Section> emptySections = new ArrayList<>();

        CalendarCourse course1 = new CalendarCourse("Course1",sections1, "", "","");


        TimeIntervalConstraint timeIntervalConstraint = new TimeIntervalConstraint(14.5, 15.0, true);

        // testing to check original and modified course section type consistency.
        CalendarCourse courseWithNoSections = new CalendarCourse("Course1", emptySections, "", "", "");
        assertFalse(timeIntervalConstraint.filter(course1));


    }

    @Test
    void filterWithWhiteListRemovePartial() {
        //blocks1
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
        //blocks2
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));
        //blocks3
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));
        //blocks4
        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));
        //blocks5
        List<Block> blocks5 = new ArrayList<>();
        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks5.add(new Block("WE", "16:30", "18:00", "room2"));
        //blocks6
        List<Block> blocks6 = new ArrayList<>();
        blocks6.add(new Block("FR", "11:30", "12:30", "room7"));

        //creating 6 corresponding section containing those 6 blocks.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);
        Section section3 = new Section("PRA-0301", "inst3", blocks3);
        Section section4 = new Section("LEC-0201", "inst4", blocks4);
        Section section5 = new Section("TUT-0402", "inst5", blocks5);
        Section section6 = new Section("LEC-0509", "inst6", blocks6);

        //Making 2 seperate sections to build CalendarCourse.
        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);

        List<Section> sections2 = new ArrayList<>();
        sections2.add(section3);
        sections2.add(section4);
        sections2.add(section5);
        sections2.add(section6);

        List<Section> expectedSections = new ArrayList<>();
        expectedSections.add(section3);
        expectedSections.add(section5);


        CalendarCourse course1 = new CalendarCourse("Course1",sections1, "", "","");
        CalendarCourse course2 = new CalendarCourse("Course2",sections2, "", "","");



        TimeIntervalConstraint timeIntervalConstraint = new TimeIntervalConstraint(14.5, 18, false);

        // testing to check if all sections without the whitelisted time intervals are removed.
        // testing to check original and modified course section type consistency.
        CalendarCourse courseWithModifiedSections = new CalendarCourse("Course2", expectedSections, "", "", "");
        assertFalse(timeIntervalConstraint.filter(course2));
        assertTrue(courseWithModifiedSections.equals(course2));
    }

    @Test
    void filterWithBlackListUnchanged() {
        //blocks1
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
        //blocks2
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));
        //blocks3
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));
        //blocks4
        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));
        //blocks5
        List<Block> blocks5 = new ArrayList<>();
        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks5.add(new Block("WE", "16:30", "18:00", "room2"));
        //blocks6
        List<Block> blocks6 = new ArrayList<>();
        blocks6.add(new Block("FR", "11:30", "12:30", "room7"));

        //creating 6 corresponding section containing those 6 blocks.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);
        Section section3 = new Section("PRA-0301", "inst3", blocks3);
        Section section4 = new Section("LEC-0201", "inst4", blocks4);
        Section section5 = new Section("TUT-0402", "inst5", blocks5);
        Section section6 = new Section("LEC-0509", "inst6", blocks6);

        //Making 2 seperate sections to build CalendarCourse.
        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);

        List<Section> sections2 = new ArrayList<>();
        sections2.add(section3);
        sections2.add(section4);
        sections2.add(section5);
        sections2.add(section6);

        CalendarCourse course1 = new CalendarCourse("Course1",sections1, "", "","");


        TimeIntervalConstraint timeIntervalConstraint = new TimeIntervalConstraint(9.5, 12.5, true);

        // testing to check original and modified course section type consistency.
        CalendarCourse courseUnchanged = new CalendarCourse("Course1",sections1, "", "","");
        assertFalse(timeIntervalConstraint.filter(course1));
    }
    @Test
    void testToString() {
        TimeIntervalConstraint timeIntervalConstraint1 = new TimeIntervalConstraint(11.5,12, true);
        TimeIntervalConstraint timeIntervalConstraint2 = new TimeIntervalConstraint(13.5, 15.5, false);
        String expected1 = "Time BlackList Constraint: 11:30-12:00";
        assertEquals(expected1, timeIntervalConstraint1.toString());
        String expected2 = "Time WhiteList Constraint: 13:30-15:30";
        assertEquals(expected2, timeIntervalConstraint2.toString());
    }
}


