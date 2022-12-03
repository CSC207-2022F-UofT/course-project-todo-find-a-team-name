package overlap_use_case;

import entities.*;
import org.junit.Assert;
import org.junit.Test;
import retrieve_timetable_use_case.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestTimetableModelUnpacker {

    private BlockModel basicBlockModel = new BlockModel(1, 18.00, 20.00, "Castle Badr");
    private Block basicBlock = new Block("TU", "18:00", "20:00", "Castle Badr");

    private SectionModel basicSectionModel = new SectionModel("TES101", "Mario-chan", List.of(basicBlockModel));
    private Section basicSection = new Section("TES101", "Mario-chan", List.of(basicBlock));

    private CourseModel basicCourseModel = new CourseModel("TES101", List.of(basicSectionModel), "S",
            "TES101", "1");

    private CalendarCourse basicCalendarCourse = new CalendarCourse("TES101", List.of(basicSection), "S",
            "TES101", "1");

    private TimetableCourse basicTimetableCourse = new TimetableCourse("TES101", List.of(basicSection), "S",
            "TES101", "1");

    public TestTimetableModelUnpacker() throws InvalidSectionsException {
    }


    /** Test unpackBlockModel with a basic case. **/
    @Test
    public void test_unpack_block_model_basic(){
        BlockModel testModel = basicBlockModel;
        Block expectedBlock = basicBlock;
        assert expectedBlock.equals(TimetableModelUnpacker.unpackBlockModel(testModel));
    }

    /** Test that unpackBlockModel will correctly throw an exception if we feed it a section an invalid wrong day.. **/
    @Test
    public void test_unpack_block_model_bad_section(){
        BlockModel testModel = new BlockModel(99, 18.00, 20.00, "Castle Badr");
        Block expectedBlock = new Block("!?", "18:00", "20:00", "Castle Badr");
        Assert.assertThrows(IllegalArgumentException.class, () -> {TimetableModelUnpacker.unpackBlockModel(testModel);});
    }

    /** Test unpackSectionModel with a basic case. **/
    @Test
    public void test_unpack_sectionModel_basic(){

        SectionModel testSectionModel = basicSectionModel;
        Section testSection = basicSection;

        assert testSection.equals(TimetableModelUnpacker.unpackSectionModel(testSectionModel));
    }

    /** Test unpackCourseModelAsCalendarCourse with a basic case. **/
    @Test
    public void test_unpack_course_model_as_calendar_course_basic(){
        CourseModel testCourseModel = basicCourseModel;

        CalendarCourse expectedCourse = basicCalendarCourse;

        assert expectedCourse.equals(TimetableModelUnpacker.unpackCourseModelAsCalendarCourse(testCourseModel));
    }

    /** Test unpackCourseModelAsTimetableCourse with a basic case. **/
    @Test
    public void test_unpack_course_model_as_timetable_course_basic() throws InvalidSectionsException {
        CourseModel testCourseModel = basicCourseModel;

        TimetableCourse expectedCourse = basicTimetableCourse;

        assert expectedCourse.equals(TimetableModelUnpacker.unpackCourseModelasTimetableCourse(testCourseModel));
    }

    /** Test unpackTimetable with a basic case. **/
    @Test
    public void test_unpack_timetable_basic(){
        TimetableModel testTimetableModel = new TimetableModel(List.of(basicCourseModel), "S");
        Timetable expectedTimetable = new Timetable(new ArrayList<>(List.of(basicTimetableCourse)), "S");

        assert expectedTimetable.equals(TimetableModelUnpacker.unpackTimeTable(testTimetableModel));
    }

    /** Test unpackSession with a very basic case. **/
    @Test
    public void test_unpack_session_basic(){
        HashMap<String, CourseModel> courseModelMap = new HashMap<>();
        courseModelMap.put("TES101", basicCourseModel);

        SessionModel testSessionModel = new SessionModel(courseModelMap, "S");
        Session expectedSession = new Session("S");
        expectedSession.addCourse(basicCalendarCourse);

        assert expectedSession.equals(TimetableModelUnpacker.unpackSessionModel(testSessionModel));
    }

}
