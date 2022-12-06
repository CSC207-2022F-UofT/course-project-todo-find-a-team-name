package blacklist_whitelist_use_case;

import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.application_business.SectionFilterOutputBoundary;
import blacklist_whitelist_use_case.application_business.SectionFilterRequestModel;
import blacklist_whitelist_use_case.application_business.SectionFilterResponseModel;
import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Test Cases for SectionFilterInteractor
 * Test all possibilities of Interactor filter method, including test for different
 * The main logic for filtering is tested inside the Entity folder, specifically the four
 * Constraints Concrete Class.
 */

class SectionFilterInteractorTest {
    static Session session;
    SectionFilterInteractor interactor;

    /**
     * Create a Demo Session Gateway for Testing Purpose.
     */
    @BeforeEach
    public void setUp(){
        //The following creates a Session Entity for testing purpose, which contains 5 Calendarcourses,
        //each has seperate blocks documenting the info of time, instructorname, date, and room number.
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

        session = new Session("F");
        session.addCourse(new CalendarCourse("CSC207", sections1, "F", "CSC207H1", "5"));
        session.addCourse(new CalendarCourse("CSC258", sections2, "F", "CSC258H1", "5"));
        session.addCourse(new CalendarCourse("MAT235", sections3, "F", "MAT235H1", "5"));
        session.addCourse(new CalendarCourse("CSC236", sections4, "F", "CSC236H1", "5"));
        session.addCourse(new CalendarCourse("STA247", sections5, "F", "STA247H1", "5"));
    }

    /**
     * Test Correctness of the filter method when the user input valid courses in Session and
     * apply blacklist constraints from four aspects.
     */
    @Test
    void filterValidCourses() {
        SectionFilterOutputBoundary presenter1 = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
                HashMap<String, ArrayList<String>> expected = new HashMap<>();
                expected.put("CSC207H1", new ArrayList<>(Arrays.asList("LEC-0101", "TUT-0401")));
                expected.put("MAT235H1", new ArrayList<>(Arrays.asList("LEC-0101", "TUT-0401",
                        "PRA-0301",
                        "LEC-0201",
                        "TUT-0402",
                        "LEC-0509"
                        )));
                expected.put("CSC258H1", new ArrayList<>(Arrays.asList("LEC-0101",
                        "TUT-0401",
                        "PRA-0301",
                        "LEC-0201",
                        "TUT-0402",
                        "LEC-0509"
                        )));
                expected.put("CSC236H1", new ArrayList<>(Arrays.asList("LEC-0101",
                        "TUT-0401",
                        "PRA-0301",
                        "LEC-0201",
                        "TUT-0402",
                        "LEC-0509")));
                Assertions.assertEquals(expected, responseModel.getModifiedCourses()); //Expected to go through this success branch, since in this case
                                           //filter is successful.
            }

            @Override
            public void prepareFailView(String error) {
                Assertions.fail();
            }
        };
        interactor = new SectionFilterInteractor(presenter1);
        interactor.setSession(session);
        SectionFilterRequestModel requestModel = new SectionFilterRequestModel(
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
     * Test Correctness of the filter method when the user input invalid courses in Session F.
     */
    @Test
    void filterInValidCourses() {

        SectionFilterOutputBoundary presenter3 = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
                 Assertions.fail(); //Not supposed to go from success Branch.

            }
            @Override
            public void prepareFailView(String error) {
                Assertions.assertEquals("Course Code Input: CS888666 does not exist!", error);
                //Supposed to display the error message of invalid course code.
            }
        };
        SectionFilterInteractor interactor = new SectionFilterInteractor(presenter3);
        interactor.setSession(session);
        SectionFilterRequestModel requestModel3 = new SectionFilterRequestModel(
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
     * Test Correctness of the filter method when the user input Empty Course Code.
     */
    @Test
    void filterEmptyCourseCode() {
        SectionFilterOutputBoundary presenter4 = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
                Assertions.fail();
                //Not expected to go through this success branch, since in this case
                //no course code is entered.

            }
            @Override
            public void prepareFailView(String error) {
                Assertions.assertEquals("No course code has been entered.", error);
                //Supposed to display the error message of empty course code input.
            }
        };
        SectionFilterInteractor interactor = new SectionFilterInteractor(presenter4);
        interactor.setSession(session);
        SectionFilterRequestModel requestModel4 = new SectionFilterRequestModel(
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
        interactor.filter(requestModel4);
    }

    /**
     * Test Correctness of the filter method when the user input Reversed Time Constraints, when the
     * endTime Limit is smaller than the startTime limit.
     */
    @Test
    void filterInvalidTime() {
        SectionFilterOutputBoundary presenter5 = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
                Assertions.fail();
                //Not expected to go through this success branch, since in this case
                //incorrect time interval is entered.

            }
            @Override
            public void prepareFailView(String error) {
                Assertions.assertEquals("StartTime Must be BEFORE EndTime", error);
                //Supposed to display the error message of invalid time interval input.
            }
        };
        SectionFilterInteractor interactor = new SectionFilterInteractor(presenter5);
        interactor.setSession(session);
        SectionFilterRequestModel requestModel5 = new SectionFilterRequestModel(
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
        );//Notice endTime is less than the startTime.
        interactor.filter(requestModel5);
    }

    /**
     * Test Correctness of the filter method when the user enters coursecode and constraints such that
     * the filtered courses do not include mandatory lecture, practical, or tutorial sections.
     * eg. for CSC207H1, it has LEC0101, LEC0102, LEC0303, TUT0101, TUT0102.
     * If after filtering it only has LEC0101, LEC0102, without any available tutorial sections,
     * then the interactor asks the presenter to prepare fail view.
     */
    @Test
    void filterNoAvailableSections() {
        SectionFilterOutputBoundary presenter6 = new SectionFilterOutputBoundary() {
            @Override
            public void prepareSuccessView(SectionFilterResponseModel responseModel) {
                Assertions.fail();
                //Not expected to go through this success branch, since in this case
                //incorrect time interval is entered.

            }
            @Override
            public void prepareFailView(String error) {
                Assertions.assertEquals("Filtering Condition Failed: CSC207H1 is missing mandatory tutorial, lecture, or practical after filtering.", error);
                //Supposed to display the error message of invalid time interval input.
            }
        };
        SectionFilterInteractor interactor = new SectionFilterInteractor(presenter6);
        interactor.setSession(session);
        SectionFilterRequestModel requestModel6 = new SectionFilterRequestModel(
                "CSC207H1",
                "/",
                "/",
                "/",
                "WHITELIST",
                "",
                "",
                new ArrayList<>(Arrays.asList(2, 3, 4)),
                "9:30",
                "10:30"
        );//Notice Sections Type is missing here after the constraints being applied.
        interactor.filter(requestModel6);
    }
}