package entities;


import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RoomConstraintTest {
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

        //creating 6 corresponding section containing those 6 blocks.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);

        //Making 2 seperate sections to build CalendarCourse.
        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);


        //emptySection for testing purpose
        List<Section> emptySections = new ArrayList<>();

        CalendarCourse course1 = new CalendarCourse("Course1",sections1, "", "","");

        List<String> rooms1 = new ArrayList<>();
        rooms1.add("room3");
        rooms1.add("room2");
        rooms1.add("room1");

        RoomConstraint roomConstraint1 = new RoomConstraint(rooms1, true);

        // testing to check original and modified course section type consistency.
        CalendarCourse courseWithNoSections = new CalendarCourse("Course1", emptySections, "", "", "");
        assertFalse(roomConstraint1.filter(course1));
        assertFalse(courseWithNoSections.equals(course1));

    }

    @Test
    void filterWithWhiteListRemovePartial() {
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
        Section section3 = new Section("PRA-0301", "inst3", blocks3);
        Section section4 = new Section("LEC-0201", "inst4", blocks4);
        Section section5 = new Section("TUT-0402", "inst5", blocks5);
        Section section6 = new Section("LEC-0509", "inst6", blocks6);

        //Making 2 seperate sections to build CalendarCourse.

        List<Section> sections2 = new ArrayList<>();
        sections2.add(section3);
        sections2.add(section4);
        sections2.add(section5);
        sections2.add(section6);

        List<Section> expectedSections = new ArrayList<>();
        expectedSections.add(section3);
        expectedSections.add(section5);


        CalendarCourse course2 = new CalendarCourse("Course2",sections2, "", "","");

        List<String> rooms2 = new ArrayList<>();
        rooms2.add("room2");
        rooms2.add("room3");

        RoomConstraint roomConstraint2 = new RoomConstraint(rooms2, false);

        // testing to check original and modified course section type consistency.
        CalendarCourse courseWithModifiedSections = new CalendarCourse("Course2", expectedSections, "", "", "");
        assertFalse(roomConstraint2.filter(course2));
        assertTrue(courseWithModifiedSections.equals(course2));

    }

    @Test
    void filterWithWhiteListUnchanged() {
        //blocks1
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
        //blocks2
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));

        //creating 6 corresponding section containing those 6 blocks.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);

        //Making 2 seperate sections to build CalendarCourse.
        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);

        CalendarCourse course1 = new CalendarCourse("Course1",sections1, "", "","");

        List<String> rooms2 = new ArrayList<>();
        rooms2.add("room2");
        rooms2.add("room3");

        RoomConstraint roomConstraint2 = new RoomConstraint(rooms2, false);

        // testing to check original and modified course section type consistency.
        CalendarCourse courseUnchanged = new CalendarCourse("Course1",sections1, "", "","");
        assertFalse(roomConstraint2.filter(course1));
        assertTrue(courseUnchanged.equals(course1));

    }
    @Test
    void testToString() {
        List<String> rooms1 = new ArrayList<>();
        rooms1.add("roomA");
        rooms1.add("roomB");
        rooms1.add("roomC");
        List<String> rooms2 = new ArrayList<>();
        rooms2.add("roomX");
        rooms2.add("roomY");
        RoomConstraint roomConstraint1 = new RoomConstraint(rooms1, true);
        RoomConstraint roomConstraint2 = new RoomConstraint(rooms2, false);
        String expected1 = "Room BlackList Constraint: [roomA, roomB, roomC]";
        assertEquals(expected1, roomConstraint1.toString());
        String expected2 = "Room WhiteList Constraint: [roomX, roomY]";
        assertEquals(expected2, roomConstraint2.toString());
    }
}
