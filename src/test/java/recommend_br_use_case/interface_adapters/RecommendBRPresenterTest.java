package recommend_br_use_case.interface_adapters;

import org.junit.jupiter.api.Test;
import recommend_br_use_case.application_business.RecommendBRResponseModel;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Test class used for testing RecommendBRPresenter
 */
class RecommendBRPresenterTest {

    /**
     * Checks whether prepareSuccessView converts the response model to view model properly and passes in
     * to IRecommendBRView.showSuccessView
     */
    @Test
    void prepareSuccessView() {
        IRecommendBRView dummyView = new IRecommendBRView() {
            @Override
            public void showSuccessView(RecommendBRViewModel viewModel) {
                List<RecommendBRCourseViewModel> expectedCourseModels = new ArrayList<>();
                List<String> expectedLectureBlockInfos1 = new ArrayList<>();
                expectedLectureBlockInfos1.add("Monday, 12:00 ~ 14:00");

                List<String> expectedTutorialBlockInfos1 = new ArrayList<>();
                expectedTutorialBlockInfos1.add("Tuesday, 13:00 ~ 15:00");

                List<String> expectedPracticalBlockInfos1 = new ArrayList<>();

                expectedCourseModels.add(new RecommendBRCourseViewModel("COS1", "course1",
                        "Creative and Cultural Representations",
                        "LEC-0101", "TUT-0101", null,
                        expectedLectureBlockInfos1, expectedTutorialBlockInfos1, expectedPracticalBlockInfos1));


                List<String> expectedLectureBlockInfos2 = new ArrayList<>();
                expectedLectureBlockInfos2.add("Monday, 12:00 ~ 14:00");
                expectedLectureBlockInfos2.add("Wednesday, 9:00 ~ 10:30");

                List<String> expectedTutorialBlockInfos2 = new ArrayList<>();

                List<String> expectedPracticalBlockInfos2 = new ArrayList<>();
                expectedPracticalBlockInfos2.add("Tuesday, 13:00 ~ 15:00");

                expectedCourseModels.add(new RecommendBRCourseViewModel("COS2", "course2",
                        "Thought, Belief, and Behaviour",
                        "LEC-0101", null, "PRA-0101",
                        expectedLectureBlockInfos2, expectedTutorialBlockInfos2, expectedPracticalBlockInfos2));
                RecommendBRViewModel expected = new RecommendBRViewModel(expectedCourseModels);

                assertEquals(expected, viewModel);
            }

            @Override
            public void showFailView(String message) {
                fail("showFailView(" + message + ") should not be called.");
            }
        };
        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter(dummyView);

        List<CourseModel> courseModels = new ArrayList<>();

        List<SectionModel> sections1 = new ArrayList<>();
        sections1.add(new SectionModel("LEC-0101", "inst1", List.of(new BlockModel(0, 12, 14, "room1"))));
        sections1.add(new SectionModel("TUT-0101", "inst3", List.of(new BlockModel(1, 13, 15, "room2"))));
        courseModels.add(new CourseModel("course1", sections1, "F", "COS1", "1"));

        List<SectionModel> sections2 = new ArrayList<>();
        sections2.add(new SectionModel("LEC-0101", "inst4", List.of(new BlockModel(0, 12, 14, "room3"),
        new BlockModel(2, 9, 10.5, "room3"))));
        sections2.add(new SectionModel("PRA-0101", "inst5", List.of(new BlockModel(1, 13, 15, "room4"))));
        courseModels.add(new CourseModel("course2", sections2, "F", "COS2", "2"));

        RecommendBRResponseModel responseModel = new RecommendBRResponseModel(courseModels);
        recommendBRPresenter.prepareSuccessView(responseModel);
    }

    /**
     * Checks whether message passed in to prepareFailView is directly passed into showFailView in IRecommendBRView
     */
    @Test
    void prepareFailView() {
        IRecommendBRView dummyView = new IRecommendBRView() {
            @Override
            public void showSuccessView(RecommendBRViewModel viewModel) {
                fail("showSuccessView shouldn't be called");
            }

            @Override
            public void showFailView(String message) {
                assertEquals("Hello!", message);
            }
        };
        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter(dummyView);
        recommendBRPresenter.prepareFailView("Hello!");

    }
}