package overlap_maximization_use_case;

import generate_overlapping_timetable_use_case.application_business.CalculateSectionHoursInteractor;
import generate_overlapping_timetable_use_case.application_business.TimeTableMatchInteractor;
import generate_overlapping_timetable_use_case.interface_adapters.*;
import org.junit.Test;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

public class TestOverlapMaximizationController {


    // Make some initial test data available to all tests.
    final OverlapTimetableBlockViewModel testBlock =
            new OverlapTimetableBlockViewModel(24, 10.00, 12.00, "BA137");
    final OverlapTimetableSectionViewModel testSection =
            new OverlapTimetableSectionViewModel("LEC0101", "Mario-chan", List.of(testBlock));

    final OverlapTimetableBlockViewModel testBlock2 =
            new OverlapTimetableBlockViewModel(24, 12.00, 14.00, "BA137");
    final OverlapTimetableSectionViewModel testSection2 =
            new OverlapTimetableSectionViewModel("LEC0201", "Mario-chan", List.of(testBlock2));

    final OverlapTimetableBlockViewModel testBlock3 =
            new OverlapTimetableBlockViewModel(24, 14.00, 16.00, "BA137");
    final OverlapTimetableSectionViewModel testSection3 =
            new OverlapTimetableSectionViewModel("LEC0201", "Mario-chan", List.of(testBlock3));


    final OverlapTimetableCourseViewModel testCourse =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection), "S", "CSC110", "4");
    final OverlapTimetableCourseViewModel testCourse2 =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection2), "S", "CSC110", "4");
    final OverlapTimetableCourseViewModel testCourse3 =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection3), "S", "CSC110", "4");


    // Initialize dummy presenter and interactor.
    final OverlapMaxPresenter testPresenter = new OverlapMaxPresenter();
    final TimeTableMatchInteractor testInteractor = new TimeTableMatchInteractor(
            new CalculateSectionHoursInteractor(), testPresenter);

    final OverlapMaximizationController testController = new OverlapMaximizationController(testInteractor);

    /**
     * Test determineBestMatchingTimetable with an identical timetable -- it should give that one...
     **/
    @Test
    public void test_bestMatchingTimetable_identical() {
        OverlapTimetableViewModel testTimetable = new OverlapTimetableViewModel(List.of(testCourse, testCourse2));
        OverlapTimetableViewModel testTimetable2 = new OverlapTimetableViewModel(List.of(testCourse, testCourse2));
        OverlapTimetableViewModel testTimetable3 = new OverlapTimetableViewModel(List.of(testCourse2, testCourse3));

        TimetableModel actual = testController.getBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));

        assert actual.equals(OverlapTimetableViewModelToModelConverter.convertOverlapTimetableViewModelToModel(testTimetable2));
    }


    /**
     * Test bestMatchingTimetable with a bunch of basic timetables.
     * Note that if timetables tie, the order isn't specified, so
     * refer to the above calculateTimetableOverlaps() test for that one.
     **/
    @Test
    public void test_bestMatchingTimetable_marginal() {
        OverlapTimetableViewModel testTimetable = new OverlapTimetableViewModel(List.of(testCourse, testCourse2));
        OverlapTimetableViewModel testTimetable2 = new OverlapTimetableViewModel(List.of(testCourse2, testCourse3));
        OverlapTimetableViewModel testTimetable3 = new OverlapTimetableViewModel(List.of(testCourse3));

        TimetableModel actual = testController.getBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));

        assert actual.equals(OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableViewModelToModel(testTimetable2));
    }

}
