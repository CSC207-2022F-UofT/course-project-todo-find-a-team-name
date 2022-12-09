package overlap_use_case;

import entities.*;
import org.junit.Assert;
import org.junit.Test;
import retrieve_timetable_use_case.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestTimetableModelUnpacker {

    private final BlockModel basicBlockModel = new BlockModel(1, 18.00, 20.00, "Castle Badr");
    private final Block basicBlock = new Block("TU", "18:00", "20:00", "Castle Badr");

    private final SectionModel basicSectionModel = new SectionModel("TES101", "Mario-chan", List.of(basicBlockModel));
    private final Section basicSection = new Section("TES101", "Mario-chan", List.of(basicBlock));

    private final CourseModel basicCourseModel = new CourseModel("TES101", List.of(basicSectionModel), "S",
            "TES101", "1");

    private final CalendarCourse basicCalendarCourse = new CalendarCourse("TES101", List.of(basicSection), "S",
            "TES101", "1");

    private final TimetableCourse basicTimetableCourse = new TimetableCourse("TES101", List.of(basicSection), "S",
            "TES101", "1");

    public TestTimetableModelUnpacker() throws InvalidSectionsException {
    }


    /** Test unpackBlockModel with a basic case. **/
    @Test
    public void testUnpackBlockModelBasic(){
        assert basicBlock.equals(TimetableModelUnpacker.unpackBlockModel(basicBlockModel));
    }

    /** Test that unpackBlockModel will correctly throw an exception if we feed it a section an invalid wrong day. **/
    @Test
    public void testUnpackBlockModelInvalidDay(){
        BlockModel testModel = new BlockModel(99, 18.00, 20.00, "Castle Badr");
        Assert.assertThrows(IllegalArgumentException.class, () -> TimetableModelUnpacker.unpackBlockModel(testModel));
    }

    /** Test unpackSectionModel with a basic case. **/
    @Test
    public void testUnpackSectionModelBasic(){

        assert basicSection.equals(TimetableModelUnpacker.unpackSectionModel(basicSectionModel));
    }

    /** Test unpackCourseModelAsCalendarCourse with a basic case. **/
    @Test
    public void testUnpackCourseModelAsCalendarCourseBasic(){

        assert basicCalendarCourse.equals(TimetableModelUnpacker.unpackCourseModelAsCalendarCourse(basicCourseModel));
    }

    /** Test unpackCourseModelAsTimetableCourse with a basic case. **/
    @Test
    public void testUnpackCourseModelBasic() {

        assert basicTimetableCourse.equals(TimetableModelUnpacker.unpackCourseModelasTimetableCourse(basicCourseModel));
    }

    /** Test unpackTimetable with a basic case. **/
    @Test
    public void testUnpackTimetableBasic(){
        TimetableModel testTimetableModel = new TimetableModel(List.of(basicCourseModel), "S");
        Timetable expectedTimetable = new Timetable(new ArrayList<>(List.of(basicTimetableCourse)), "S");

        assert expectedTimetable.equals(TimetableModelUnpacker.unpackTimetable(testTimetableModel));
    }

    /** Test unpackSession with a very basic case. **/
    @Test
    public void testUnpackSessionBasic(){
        HashMap<String, CourseModel> courseModelMap = new HashMap<>();
        courseModelMap.put("TES101", basicCourseModel);

        SessionModel testSessionModel = new SessionModel(courseModelMap, "S");
        Session expectedSession = new Session("S");
        expectedSession.addCourse(basicCalendarCourse);

        assert expectedSession.equals(TimetableModelUnpacker.unpackSessionModel(testSessionModel));
    }

}
