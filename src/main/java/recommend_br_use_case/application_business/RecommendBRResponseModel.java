package recommend_br_use_case.application_business;

import retrieve_timetable_use_case.application_business.CourseModel;

import java.util.List;

/**
 * Class representing data outputted by recommend BR use case
 */
public class RecommendBRResponseModel {
    private final List<CourseModel> courses;

    /**
     * Constructs RecommendBRResponseModel with the given courses
     *
     * @param courses List of courses represented by BRCourseResponseModel
     */
    public RecommendBRResponseModel(List<CourseModel> courses){
        this.courses = courses;
    }

    /**
     * Returns list of courses this response model contains, represented by
     * BRCourseResponseModel
     * @return list of course response model this response model contains
     */
    public List<CourseModel> getCourses() {
        return courses;
    }
}
