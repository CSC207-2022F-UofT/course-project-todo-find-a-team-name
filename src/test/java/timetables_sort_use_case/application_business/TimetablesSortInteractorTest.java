package timetables_sort_use_case.application_business;

import entities.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import timetables_sort_use_case.interface_adapters.TestTimetablesSortPresenter;

import java.util.ArrayList;
import java.util.List;

class TimetablesSortInteractorTest {
    private TimetablesSortInteractor interactor;
    private TestTimetablesSortPresenter presenter;

    /**
     * sets up 2 timetables in the interactor to test its functionalities
     */
    @BeforeEach
    void setUp() {
        presenter = new TestTimetablesSortPresenter();
        interactor = new TimetablesSortInteractor(presenter);

        Block block1 = new Block("MO", "11:00", "12:00", "");
        Block block2 = new Block("FR", "11:00", "12:00", "");
        java.util.List<Block> blocks1 = new ArrayList<>();
        blocks1.add(block1);
        blocks1.add(block2);

        Block block3 = new Block("WE", "11:00", "12:00", "");
        java.util.List<Block> blocks2 = new ArrayList<>();
        blocks2.add(block3);

        Block block4 = new Block("TU", "16:00", "17:00", "");
        Block block5 = new Block("FR", "16:00", "17:00", "");
        java.util.List<Block> blocks3 = new ArrayList<>();
        blocks3.add(block4);
        blocks3.add(block5);

        Block block6 = new Block("MO", "14:00", "16:00", "");
        java.util.List<Block> blocks4 = new ArrayList<>();
        blocks4.add(block6);

        Section s1 = new Section("LEC0101", "", blocks1);
        Section s2 = new Section("TUT0101", "", blocks2);

        Section s3 = new Section("LEC0401", "", blocks3);
        Section s4 = new Section("TUT0301", "", blocks4);

        java.util.List<Section> sections1 = new ArrayList<>();
        sections1.add(s1);
        sections1.add(s2);
        List<Section> sections2 = new ArrayList<>();
        sections2.add(s3);
        sections2.add(s4);

        TimetableCourse c1;
        TimetableCourse c2;

        try {
            c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
            c2 = new TimetableCourse("some other title", sections2, "", "CSC207H1", "");
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        ArrayList<TimetableCourse> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        Timetable timetable = new Timetable(courses, "F");

        Block block10 = new Block("MO", "11:00", "12:00", "");
        Block block20 = new Block("MO", "12:00", "13:00", "");
        java.util.List<Block> blocks10 = new ArrayList<>();
        blocks10.add(block10);
        blocks10.add(block20);

        Block block30 = new Block("MO", "13:00", "14:00", "");
        java.util.List<Block> blocks20 = new ArrayList<>();
        blocks20.add(block30);

        Block block40 = new Block("MO", "14:00", "15:00", "");
        Block block50 = new Block("MO", "15:00", "16:00", "");
        java.util.List<Block> blocks30 = new ArrayList<>();
        blocks30.add(block40);
        blocks30.add(block50);

        Block block60 = new Block("MO", "16:00", "17:00", "");
        java.util.List<Block> blocks40 = new ArrayList<>();
        blocks40.add(block60);

        Section s10 = new Section("LEC0101", "", blocks10);
        Section s20 = new Section("TUT0101", "", blocks20);

        Section s30 = new Section("LEC0401", "", blocks30);
        Section s40 = new Section("TUT0301", "", blocks40);

        java.util.List<Section> sections10 = new ArrayList<>();
        sections10.add(s10);
        sections10.add(s20);
        List<Section> sections20 = new ArrayList<>();
        sections20.add(s30);
        sections20.add(s40);

        TimetableCourse c10;
        TimetableCourse c20;

        try {
            c10 = new TimetableCourse("999", sections10, "", "CSC236H1", "");
            c20 = new TimetableCourse("999", sections20, "", "CSC207H1", "");
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        ArrayList<TimetableCourse> courses1 = new ArrayList<>();
        courses1.add(c10);
        courses1.add(c20);
        Timetable timetable1 = new Timetable(courses1, "F");


        List<Timetable> timetables = new ArrayList<>();
        timetables.add(timetable);
        timetables.add(timetable1);

        interactor.onNext(timetables);
    }

    /**
     * tests that the commuter option returns the timetable with the least days first
     */
    @Test
    void testCommuter() {
        interactor.timetablesSort(new TimetablesSortRequestModel("morning", "commuter"));
        String actual = presenter.response.getTimetables()[0].getCourses().get(0).getTitle();
        Assertions.assertEquals("999", actual);
    }

    /**
     * tests that the break option returns the timetable with the most breaks first
     */
    @Test
    void testBreak() {
        interactor.timetablesSort(new TimetablesSortRequestModel("morning", "break"));
        String actual = presenter.response.getTimetables()[0].getCourses().get(0).getTitle();
        Assertions.assertEquals("some title", actual);
    }

    /**
     * tests that the morning option returns the timetable with the earliest classes first (after break
     * preferences)
     */
    @Test
    void testMorning() {
        interactor.timetablesSort(new TimetablesSortRequestModel("morning", ""));
        String actual = presenter.response.getTimetables()[0].getCourses().get(0).getTitle();
        Assertions.assertEquals("some title", actual);
    }
    /**
     * tests that the evening option returns the timetable with the latest classes first (after break
     * preferences)
     */
    @Test
    void testEvening() {
        interactor.timetablesSort(new TimetablesSortRequestModel("evening", ""));
        String actual = presenter.response.getTimetables()[0].getCourses().get(0).getTitle();
        Assertions.assertEquals("999", actual);
    }
    /**
     * tests that the morning option returns the timetable with the classes whose average is closest to 2pm
     * first (after break preferences)
     */
    @Test
    void testAfternoon() {
        interactor.timetablesSort(new TimetablesSortRequestModel("afternoon", ""));
        String actual = presenter.response.getTimetables()[0].getCourses().get(0).getTitle();
        Assertions.assertEquals("999", actual);
    }
}
