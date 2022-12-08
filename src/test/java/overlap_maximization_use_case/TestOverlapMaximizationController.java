package overlap_maximization_use_case;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import org.junit.Test;
import overlap_crap_fix_locations_later.application_business.CalculateSectionHoursInteractor;
import overlap_crap_fix_locations_later.application_business.TimeTableMatchInteractor;
import overlap_crap_fix_locations_later.interface_adapters.*;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

public class TestOverlapMaximizationController {


    // Make some initial test data available to all tests.
    OverlapTimetableBlockViewModel testBlock =
            new OverlapTimetableBlockViewModel(24, 10.00, 12.00, "BA137");
    OverlapTimetableSectionViewModel testSection =
            new OverlapTimetableSectionViewModel("LEC0101", "Mario-chan", List.of(testBlock));

    OverlapTimetableBlockViewModel testBlock2 =
            new OverlapTimetableBlockViewModel(24, 12.00, 14.00, "BA137");
    OverlapTimetableSectionViewModel testSection2 =
            new OverlapTimetableSectionViewModel("LEC0201", "Mario-chan", List.of(testBlock2));

    OverlapTimetableBlockViewModel testBlock3 =
            new OverlapTimetableBlockViewModel(24, 14.00, 16.00, "BA137");
    OverlapTimetableSectionViewModel testSection3 =
            new OverlapTimetableSectionViewModel("LEC0201", "Mario-chan", List.of(testBlock3));


    OverlapTimetableCourseViewModel testCourse =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection), "S", "CSC110", "4");
    OverlapTimetableCourseViewModel testCourse2 =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection2), "S", "CSC110", "4");
    OverlapTimetableCourseViewModel testCourse3 =
            new OverlapTimetableCourseViewModel("Foundations",
                    List.of(testSection3), "S", "CSC110", "4");

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

    OverlapMaximizationController testController = new OverlapMaximizationController(testInteractor);

    /**
     * Test determineBestMatchingTimetable with an identical timetable -- it should give that one...
     **/
    @Test
    public void test_bestMatchingTimetable_identical() {
        OverlapTimetableViewModel testTimetable = new OverlapTimetableViewModel(List.of(testCourse, testCourse2));
        OverlapTimetableViewModel testTimetable2 = new OverlapTimetableViewModel(List.of(testCourse, testCourse2));
        OverlapTimetableViewModel testTimetable3 = new OverlapTimetableViewModel(List.of(testCourse2, testCourse3));

        OverlapTimetableViewModel expected = testTimetable2;
        TimetableModel actual = testController.getBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));

        assert actual.equals(OverlapTimetableViewModelToModelConverter.convertOverlapTimetableViewModelToModel(expected));
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

        OverlapTimetableViewModel expected = testTimetable2;
        TimetableModel actual = testController.getBestMatchingTimetable(testTimetable,
                List.of(testTimetable2, testTimetable3));

        assert actual.equals(OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableViewModelToModel(expected));
    }

}
