package blacklist_whitelist_use_case;

import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Test Cases for SectionFilterInteractor
 * The main logic for filtering is tested inside the Entity folder, specifically the four
 * Constraints Concrete Class.
 */

class SectionFilterInteractorTest {
    SectionFilterInteractor interactor;

    /**
     * Create a Demo Session Gateway for Testing Purpose.
     */
    @BeforeEach
    public void setUp(){
        //Create blocks entities.
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));

        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));

        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));

        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));

        List<Block> blocks5 = new ArrayList<>();
        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
        blocks5.add(new Block("WE", "16:30", "18:00", "room3"));

        List<Block> blocks6 = new ArrayList<>();
        blocks6.add(new Block("FR", "11:30", "12:30", "room3"));
        blocks6.add(new Block("FR", "11:30", "12:30", "room4"));

        //Create demo section entities for testing.
        Section section1 = new Section("LEC-0101", "inst1", blocks1);
        Section section2 = new Section("TUT-0401", "inst2", blocks2);
        Section section3 = new Section("PRA-0301", "inst3", blocks3);
        Section section4 = new Section("LEC-0201", "inst4", blocks4);
        Section section5 = new Section("TUT-0402", "inst5", blocks5);
        Section section6 = new Section("LEC-0509", "inst6", blocks6);

        List<Section> sections1 = new ArrayList<>();
        sections1.add(section1);
        sections1.add(section2);

        List<Section> sections2 = new ArrayList<>();
        sections2.add(section1);
        sections2.add(section2);
        sections2.add(section3);
        sections2.add(section4);
        sections2.add(section5);
        sections2.add(section6);

        List<Section> sections3 = new ArrayList<>();
        sections3.add(section1);
        sections3.add(section2);
        sections3.add(section3);
        sections3.add(section4);
        sections3.add(section5);
        sections3.add(section6);

        List<Section> sections4 = new ArrayList<>();
        sections4.add(section1);
        sections4.add(section2);
        sections4.add(section3);
        sections4.add(section4);
        sections4.add(section5);
        sections4.add(section6);

        List<Section> sections5 = new ArrayList<>();
        sections5.add(section1);
        sections5.add(section4);

        //Create fall and winter Session that will be used to test Constraints Applier.

        Session fall = new Session("F");
        fall.addCourse(new CalendarCourse("CSC207", sections1, "F", "CSC207H1", "5"));
        fall.addCourse(new CalendarCourse("CSC258", sections2, "F", "CSC258H1", "5"));
        fall.addCourse(new CalendarCourse("MAT235", sections3, "F", "MAT235H1", "5"));
        fall.addCourse(new CalendarCourse("CSC236", sections4, "F", "CSC236H1", "5"));
        fall.addCourse(new CalendarCourse("STA247", sections5, "F", "STA247H1", "5"));

        Session winter = new Session("S");
        winter.addCourse(new CalendarCourse("CSC207", sections1, "S", "CSC207H1", "5"));
        winter.addCourse(new CalendarCourse("CSC258", sections2, "S", "CSC258H1", "5"));
        winter.addCourse(new CalendarCourse("MAT235", sections3, "S", "MAT235H1", "5"));
        winter.addCourse(new CalendarCourse("CSC236", sections4, "S", "CSC236H1", "5"));
        winter.addCourse(new CalendarCourse("STA247", sections5, "S", "STA247H1", "5"));

        SectionFilterOutputBoundary presenter = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
            }

            @Override
            public void prepareFailView(String error) {
            }
        };
        interactor = new SectionFilterInteractor(presenter);
        interactor.setFallSession(fall);
        interactor.setWinterSession(winter);
    }

    /**
     * Test Correctness of the filter method when the user input valid courses in Session S and
     * apply blacklist constraints from four aspects.
     */
    @Test
    void filterValidCoursesBL() {
        SectionFilterRequestModel requestModel = new SectionFilterRequestModel(
                "S",
                "CSC207H1, CSC258H1, CSC236H1, MAT235H1",
                "BLACKLIST",
                "BLACKLIST",
                "BLACKLIST",
                "BLACKLIST",
                "W Zhan",
                "SS 990",
                new ArrayList<>(),
                "7:30",
                "8:30"
        );
        interactor.filter(requestModel);
    }

    /**
     * Test Correctness of the filter method when the user input valid courses in Session S and
     * apply whitelist constraints.
     */
    @Test
    void filterValidCoursesWL() {
        SectionFilterRequestModel requestModel2 = new SectionFilterRequestModel(
                "S",
                "CSC207H1, CSC258H1, CSC236H1, MAT235H1",
                "/",
                "/",
                "/",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "11:30",
                "12:30"
        );
        interactor.filter(requestModel2);
    }

    /**
     * Test Correctness of the filter method when the user input invalid courses in Session F.
     */
    @Test
    void filterInValidCourses() {
        SectionFilterRequestModel requestModel3 = new SectionFilterRequestModel(
                "F",
                "CS888666",
                "/",
                "/",
                "BLACKLIST",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "11:30",
                "12:30"
        );
        interactor.filter(requestModel3);
        SectionFilterRequestModel requestModel3b = new SectionFilterRequestModel(
                "S",
                "CS888666",
                "/",
                "/",
                "BLACKLIST",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "11:30",
                "12:30"
        );
        interactor.filter(requestModel3b);
    }

    /**
     * Test Correctness of the filter method when the user input Calendar Courses in Session F with Blacklist
     * Constraints that result in missing mandatory tutorial, lecture, or practical sections.
     */
    @Test
    void filterNoValidSections() {
        SectionFilterRequestModel requestModel4 = new SectionFilterRequestModel(
                "F",
                "CSC207H1,  CSC258H1",
                "/",
                "/",
                "/",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "19:30",
                "22:30"
        );
        interactor.filter(requestModel4);
    }

    /**
     * Test Correctness of the filter method when the user input Empty Course Code.
     */
    @Test
    void filterEmptyCourseCode() {
        SectionFilterRequestModel requestModel5 = new SectionFilterRequestModel(
                "S",
                "",
                "/",
                "/",
                "/",
                "/",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "9:30",
                "22:30"
        );
        interactor.filter(requestModel5);
    }

    /**
     * Test Correctness of the filter method when the user input Reversed Time Constraints, when the
     * endTime Limit is smaller than the startTime limit.
     */
    @Test
    void filterInvalidTime() {
        SectionFilterRequestModel requestModel6 = new SectionFilterRequestModel(
                "S",
                "CSC207H1",
                "/",
                "/",
                "/",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "9:30",
                "8:30"
        );
        interactor.filter(requestModel6);
    }


}