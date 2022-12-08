package overlap_crap_fix_locations_later.tests;

import org.junit.Test;
import overlap_crap_fix_locations_later.ViewModels.*;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

/**
 * Tests for OverlapTimetableViewModelToModelConverter's methods.
 */
public class TestOverlapTimetableViewModelToModelConverter {

    private final BlockModel basicBlockModel = new BlockModel(1, 18.00, 20.00, "Castle Badr");
    private final OverlapTimetableBlockViewModel basicBlockViewModel =
            new OverlapTimetableBlockViewModel(1, 18.00, 20.00, "Castle Badr");

    private final SectionModel basicSectionModel = new SectionModel("TES101", "Mario-chan", List.of(basicBlockModel));
    private final OverlapTimetableSectionViewModel basicSectionViewModel =
            new OverlapTimetableSectionViewModel("TES101", "Mario-chan", List.of(basicBlockViewModel));

    private final CourseModel basicCourseModel = new CourseModel("TES101", List.of(basicSectionModel), "S",
            "TES101", "1");

    private final OverlapTimetableCourseViewModel basicCourseViewModel =
            new OverlapTimetableCourseViewModel("TES101", List.of(basicSectionViewModel), "S",
                    "TES101", "1");

    private final TimetableModel basicTimetableModel = new TimetableModel(List.of(basicCourseModel));

    private final OverlapTimetableViewModel basicTimetableViewModel =
            new OverlapTimetableViewModel(List.of(basicCourseViewModel));

    /**
     * Test conversion to a BlockModel with a basic case.
     **/
    @Test
    public void test_conversion_to_block_model_basic() {
        assert OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableBlockViewModelToModel(basicBlockViewModel).equals(basicBlockModel);
    }

    /**
     * Test conversion to a SectionModel with a basic case.
     **/
    @Test
    public void test_conversion_to_section_model_basic() {
        assert OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableSectionViewModelToModel(basicSectionViewModel).equals(basicSectionModel);
    }

    /**
     * Test conversion to a CourseModel with a basic case.
     **/
    @Test
    public void test_conversion_to_course_model_basic() {
        assert OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableCourseViewModelToModel(basicCourseViewModel).equals(basicCourseModel);
    }

    /**
     * Test conversion to a TimetableModel with a basic case.
     **/
    @Test
    public void test_conversion_to_timetable_model_basic() {
        assert OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableViewModelToModel(basicTimetableViewModel).equals(basicTimetableModel);
    }


}
