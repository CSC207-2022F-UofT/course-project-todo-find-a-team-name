package entities;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class WeekdayConstraintTest {
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

        List<Integer> days = new ArrayList<Integer>(List.of(1, 3, 4));
        WeekdayConstraint weekdayConstraint = new WeekdayConstraint(days, true);

        // testing to check if all sections with the blacklisted time interval are removed. (No sections satisfied),
        // in this case there is a early return which deletes one of the section type, causing inconsistency.
        CalendarCourse courseWithNoSections = new CalendarCourse("Course1", emptySections, "", "", "");
        assertFalse(weekdayConstraint.filter(course1));


    }

    @Test
    void filterWithWhiteListNoRemove() {
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
        CalendarCourse course2 = new CalendarCourse("Course1",sections1, "", "","");



        List<Integer> days = new ArrayList<Integer>(List.of(0, 1, 2,3, 4));
        WeekdayConstraint weekdayConstraint = new WeekdayConstraint(days, false);

        // testing to check if all sections without the whitelisted time intervals are not removed, and the
        // section type is consistent.
        assertTrue(weekdayConstraint.filter(course2));
        assertTrue(course1.equals(course2));
    }

    @Test
    void filterWithBlackListRemoveAll() {
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


        List<Integer> days = new ArrayList<Integer>(List.of(1, 2,3,4,0));
        WeekdayConstraint weekdayConstraint = new WeekdayConstraint(days, true);
        ArrayList<Section> emptySections = new ArrayList<>();

        // testing to check if all sections with the blacklisted time interval are removed. (No sections satisfied) I
        // In this case, it does early return false since it detects inconsistency in section types.
        CalendarCourse courseWithNoSections = new CalendarCourse("Course1", emptySections, "", "", "");
        assertFalse(weekdayConstraint.filter(course1));
    }
    @Test
    void testToString() {
        List<Integer> days = new ArrayList<Integer>(List.of(1, 3, 4));
        WeekdayConstraint weekdayConstraint1 = new WeekdayConstraint(days, true);
        WeekdayConstraint weekdayConstraint2 = new WeekdayConstraint(days, false);
        String expected1 = "Weekday BlackList Constraint: [TU, TH, FR]";
        assertEquals(expected1, weekdayConstraint1.toString());
        String expected2 = "Weekday WhiteList Constraint: [TU, TH, FR]";
        assertEquals(expected2, weekdayConstraint2.toString());
    }

}