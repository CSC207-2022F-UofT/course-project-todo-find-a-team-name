package overlap_crap_fix_locations_later;

import entities.Constraint;
import org.junit.Test;
import retrieve_timetable_use_case.BlockModel;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.SectionModel;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestTimeTableMatchInteractor {

    // Make some initial test data available to all tests.
    BlockModel testBlock = new BlockModel(24, 10.00, 12.00, "BA137");
    SectionModel testSection = new SectionModel("LEC0101", "Mario-chan", List.of(testBlock));

    BlockModel testBlock2 = new BlockModel(24, 12.00, 14.00, "BA137");
    SectionModel testSection2 = new SectionModel("LEC0201", "Mario-chan", List.of(testBlock2));

    BlockModel testBlock3 = new BlockModel(24, 14.00, 16.00, "BA137");
    SectionModel testSection3 = new SectionModel("LEC0201", "Mario-chan", List.of(testBlock3));


    CourseModel testCourse = new CourseModel("Foundations", List.of(testSection), "S", "CSC110", "4");
    CourseModel testCourse2 = new CourseModel("Foundations", List.of(testSection2), "S", "CSC110", "4");
    CourseModel testCourse3 = new CourseModel("Foundations", List.of(testSection3), "S", "CSC110", "4");


    /** Test calculateTimetableOverlaps with an identical timetable -- it should give the full number of hours. **/
    @Test
    public void test_calculate_timetable_overlaps_identical(){
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse, testCourse2));

        TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
                new ArrayList<TimetableModel>(List.of(testTimetable2)),
                // Note the main timetable:
                testTimetable,
                false,
                new ArrayList<Constraint>(),
                new CalculateSectionHoursInteractor());

        HashMap<TimetableModel, Double> expected = new HashMap<TimetableModel, Double>(1);
        expected.put(testTimetable2, 4.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps();
        assert actual.equals(expected);
    }

    /** Test calculateTimetableOverlaps with a basic (if small) case. **/
    @Test
    public void test_calculate_timetable_overlap_basic(){
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse2, testCourse3));

        TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
                new ArrayList<TimetableModel>(List.of(testTimetable2)),
                // Note the main timetable:
                testTimetable,
                false,
                new ArrayList<Constraint>(),
                new CalculateSectionHoursInteractor());

        HashMap<TimetableModel, Double> expected = new HashMap<TimetableModel, Double>(1);
        expected.put(testTimetable2, 2.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps();
        assert actual.equals(expected);
    }

    /** Test the interactor's calculateOverlaps functionality with 0 case -- two entirely different timetables. **/
    @Test
    public void test_calculate_timetable_overlap_zero(){
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse3));

        TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
                new ArrayList<TimetableModel>(List.of(testTimetable2)),
                // Note the main timetable:
                testTimetable,
                false,
                new ArrayList<Constraint>(),
                new CalculateSectionHoursInteractor());

        HashMap<TimetableModel, Double> expected = new HashMap<TimetableModel, Double>(1);
        expected.put(testTimetable2, 0.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps();
        assert actual.equals(expected);
    }

    /** Test determineBestMatchingTimetable with an identical timetable -- it should give that one... **/
    @Test
    public void test_bestMatchingTimetable_identical(){
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable3 = new TimetableModel(List.of(testCourse2, testCourse3));

        TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
                new ArrayList<TimetableModel>(List.of(testTimetable2, testTimetable3)),
                // Note the main timetable:
                testTimetable,
                false,
                new ArrayList<Constraint>(),
                new CalculateSectionHoursInteractor());

        TimetableModel expected = testTimetable2;
        TimetableModel actual = testInteractor.determineBestMatchingTimetable();
        assert actual.equals(expected);
    }


    /** Test bestMatchingTimetable with a bunch of basic timetables.
     * Note that if timetables tie, the order isn't specified, so
     * refer to the above calculateTimetableOverlaps() test for that one.**/
    @Test
    public void test_bestMatchingTimetable_marginal(){
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse2, testCourse3));
        TimetableModel testTimetable3 = new TimetableModel(List.of(testCourse3));

        TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
                new ArrayList<TimetableModel>(List.of(testTimetable2, testTimetable3)),
                // Note the main timetable:
                testTimetable,
                false,
                new ArrayList<Constraint>(),
                new CalculateSectionHoursInteractor());

        TimetableModel expected = testTimetable2;
        TimetableModel actual = testInteractor.determineBestMatchingTimetable();
        assert actual.equals(expected);
    }
}
