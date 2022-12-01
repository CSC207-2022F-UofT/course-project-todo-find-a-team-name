package recommend_br_use_case.interface_adapters;


import java.util.List;

/**
 * Class containing data needed for IRecommendBRView to show the output information for
 * recommend BR use case.
 * It contains list of course data shown to the user.
 */
public class RecommendBRViewModel {
    private final List<RecommendBRCourseViewModel> courseViewModels;

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

    /**
     * Returns whether obj is equal to this object
     *
     * @param obj object compared
     * @return whether obj is equal to this object
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecommendBRViewModel)){
            return false;
        }

        RecommendBRViewModel other = (RecommendBRViewModel) obj;

        return courseViewModels.equals(other.courseViewModels);
    }
}
