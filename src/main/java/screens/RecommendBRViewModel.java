package screens;

import java.util.List;

/**
 * Class containing data needed for IRecommendBRView to show the output information for
 * recommend BR use case.
 * It contains list of course data shown to the user.
 */
public class RecommendBRViewModel {
    List<RecommendBRCourseViewModel> courseViewModels;

    /**
     * Constructs RecommendBRViewModel from the given list of BRCourseViewModel
     *
     * @param courseViewModels list of BRCourseViewModel containing information display for each course
     */
    public RecommendBRViewModel(List<RecommendBRCourseViewModel> courseViewModels){
        this.courseViewModels = courseViewModels;
    }

    /**
     * Returns list of BRCourseViewModel representing information about the course shown to the user
     *
     * @return list of BRCourseViewModel representing information about the course shown to the user
     */
    public List<RecommendBRCourseViewModel> getCourseViewModels() {
        return courseViewModels;
    }
}
