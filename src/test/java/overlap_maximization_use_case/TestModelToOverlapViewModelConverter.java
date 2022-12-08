package overlap_maximization_use_case;

import org.junit.Test;
import overlap_crap_fix_locations_later.interface_adapters.*;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

/**
 * A test class that contains tests for the methods in testModelToOverlapViewModelConverter
 **/
public class TestModelToOverlapViewModelConverter {
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

    private final OverlapTimetableViewModel basicTimeteableViewModel =
            new OverlapTimetableViewModel(List.of(basicCourseViewModel));

    /**
     * Test convertBlockModel with a basic case.
     **/
    @Test
    public void test_convert_block_model_basic() {
        assert ModelToOverlapViewModelConverter.convertBlockModel(basicBlockModel).equals(basicBlockViewModel);
    }

    /**
     * Test convertSectionModel with a basic case.
     **/
    @Test
    public void test_convert_section_model_basic() {
        assert ModelToOverlapViewModelConverter.convertSectionModel(basicSectionModel).equals(basicSectionViewModel);
    }

    /**
     * Test convertCourseModel with a basic case.
     **/
    @Test
    public void test_convert_course_model_basic() {
        assert ModelToOverlapViewModelConverter.convertCourseModel(basicCourseModel).equals(basicCourseViewModel);
    }

    /**
     * Test convertTimetableModel with a basic case.
     **/
    @Test
    public void test_convert_timetable_model_basic() {
        assert ModelToOverlapViewModelConverter.convertTimetableModel(basicTimetableModel)
                .equals(basicTimeteableViewModel);
    }


}
