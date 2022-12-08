package overlap_maximization_use_case;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import org.junit.Before;
import org.junit.Test;
import overlap_crap_fix_locations_later.application_business.CalculateSectionHoursInteractor;
import overlap_crap_fix_locations_later.application_business.TimeTableMatchInteractor;
import overlap_crap_fix_locations_later.interface_adapters.OverlapInputView;
import overlap_crap_fix_locations_later.interface_adapters.OverlapMaxPresenter;
import overlap_crap_fix_locations_later.interface_adapters.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

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


    // We kind of need a dummy Dialog to pass through just from how clean architecture works, to soak best matching
    // Timetable.
    OverlapInputView dummyDialog = new OverlapInputView() {
        @Override
        public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels) {

        }

        @Override
        public void stashBestMatchingTimetable(TimetableViewModel timetableViewModel) {

        }
    };

    // Initialize dummy presenter and interactor.
    OverlapMaxPresenter testPresenter = new OverlapMaxPresenter();
    TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
            new CalculateSectionHoursInteractor(), testPresenter);

    /**
     * Method to handle runtime setup of this test class.
     **/
    @Before
    public void initialize_test_stuff() {
        testPresenter.setDialogToPassTo(dummyDialog);
    }

    /**
     * Test calculateTimetableOverlaps with an identical timetable -- it should give the full number of hours.
     **/
    @Test
    public void test_calculate_timetable_overlaps_identical() {
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse, testCourse2));

        HashMap<TimetableModel, Double> expected = new HashMap<>(1);
        expected.put(testTimetable2, 4.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps(testTimetable,
                List.of(testTimetable2));
        assert actual.equals(expected);
    }

    /**
     * Test calculateTimetableOverlaps with a basic (if small) case.
     **/
    @Test
    public void test_calculate_timetable_overlap_basic() {
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse2, testCourse3));

        HashMap<TimetableModel, Double> expected = new HashMap<TimetableModel, Double>(1);
        expected.put(testTimetable2, 2.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps(testTimetable,
                List.of(testTimetable2));
        assert actual.equals(expected);
    }

    /**
     * Test the interactor's calculateOverlaps functionality with 0 case -- two entirely different timetables.
     **/
    @Test
    public void test_calculate_timetable_overlap_zero() {
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse3));

        HashMap<TimetableModel, Double> expected = new HashMap<TimetableModel, Double>(1);
        expected.put(testTimetable2, 0.00);

        HashMap<TimetableModel, Double> actual = testInteractor.calculateTimetableOverlaps(testTimetable,
                List.of(testTimetable2));
        assert actual.equals(expected);
    }

    /**
     * Test determineBestMatchingTimetable with an identical timetable -- it should give that one...
     **/
    @Test
    public void test_bestMatchingTimetable_identical() {
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable3 = new TimetableModel(List.of(testCourse2, testCourse3));

        TimetableModel expected = testTimetable2;
        TimetableModel actual = testInteractor.determineBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));
        assert actual.equals(expected);
    }


    /**
     * Test bestMatchingTimetable with a bunch of basic timetables.
     * Note that if timetables tie, the order isn't specified, so
     * refer to the above calculateTimetableOverlaps() test for that one.
     **/
    @Test
    public void test_bestMatchingTimetable_marginal() {
        TimetableModel testTimetable = new TimetableModel(List.of(testCourse, testCourse2));
        TimetableModel testTimetable2 = new TimetableModel(List.of(testCourse2, testCourse3));
        TimetableModel testTimetable3 = new TimetableModel(List.of(testCourse3));

        TimetableModel expected = testTimetable2;
        TimetableModel actual = testInteractor.determineBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));
        assert actual.equals(expected);
    }
}
