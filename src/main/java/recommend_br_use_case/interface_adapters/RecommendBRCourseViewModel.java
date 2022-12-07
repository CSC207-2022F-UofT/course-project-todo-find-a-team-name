package recommend_br_use_case.interface_adapters;

import java.util.List;
import java.util.Objects;

/**
 * Class containing data needed for displaying course information for recommend BR use case.
 */
public class RecommendBRCourseViewModel {
    private String code;
    private String title;
    private final String brCategory;
    private final String lectureCode;
    private final String tutorialCode;
    private final String practicalCode;
    private final List<String> lectureBlockInfos;
    private final List<String> tutorialBlockInfos;
    private final List<String> practicalBlockInfos;

    /**
     * Constructs BRCourseViewModel with the given code, title, breadth category, lecture section code, tutorial
     * section code, practical code, list of lecture block information, tutorial block information, and practical
     * block information.
     * Each element in list of block information is formatted as:
     *      "[day], [start time] ~ [end time]"
     * where
     *      day is written as Monday, Tuesday, and so on.
     *      start time and end time is written as hh:mm
     *
     * @param code code of the course
     * @param title title of the course
     * @param brCategory breadth category of the course, represented as its full text (e.g. "Creative and Cultural
     *                  Representations")
     * @param lectureCode lecture code of the course
     * @param tutorialCode tutorial code of the course
     * @param practicalCode practical code of the course
     * @param lectureBlockInfos list of text containing lecture block information of the course
     * @param tutorialBlockInfos list of text containing tutorial block information of the course
     * @param practicalBlockInfos list of text containing practical block information of the course
     */
    public RecommendBRCourseViewModel(String code, String title, String brCategory, String lectureCode, String tutorialCode,
                                      String practicalCode, List<String> lectureBlockInfos, List<String> tutorialBlockInfos, List<String> practicalBlockInfos){
        this.code = code;
        this.title = title;
        this.brCategory = brCategory;
        this.lectureCode = lectureCode;
        this.tutorialCode = tutorialCode;
        this.practicalCode = practicalCode;
        this.lectureBlockInfos = lectureBlockInfos;
        this.tutorialBlockInfos = tutorialBlockInfos;
        this.practicalBlockInfos = practicalBlockInfos;
    }

    /**
     * Returns breadth category of the course
     *
     * @return code of the course
     */
    public String getBrCategory() {
        return brCategory;
    }

    /**
     * Returns title of the course
     *
     * @return title of the course
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns code of the course
     * @return code of the course
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns lecture code of the course
     *
     * @return lecture code of the course
     */
    public String getLectureCode() {
        return lectureCode;
    }

    /**
     * Returns tutorial code of the course
     *
     * @return tutorial code of the course
     */
    public String getTutorialCode() {
        return tutorialCode;
    }

    /**
     * Returns practical code of the course
     *
     * @return practical code of the course
     */
    public String getPracticalCode() {
        return practicalCode;
    }

    /**
     * Returns lecture block information of the course
     *
     * @return lecture block information of the course
     */
    public List<String> getLectureBlockInfos() {
        return lectureBlockInfos;
    }

    /**
     * Returns tutorial block information of the course
     *
     * @return tutorial block information of the course
     */
    public List<String> getTutorialBlockInfos() {
        return tutorialBlockInfos;
    }

    /**
     * Returns practical block information of the course
     *
     * @return practical block information of the course
     */
    public List<String> getPracticalBlockInfos() {
        return practicalBlockInfos;
    }

    /**
     * Sets the code of the course to the given argument
     *
     * @param code new code of the course
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the title of the course to the given argument
     *
     * @param title new title of the course
     */
    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof RecommendBRCourseViewModel)){
            return false;
        }

        RecommendBRCourseViewModel other = (RecommendBRCourseViewModel) obj;

        return code.equals(other.code) && title.equals(other.title) &&
                brCategory.equals(other.brCategory) && Objects.equals(lectureCode, other.lectureCode) &&
                Objects.equals(tutorialCode, other.tutorialCode) && Objects.equals(practicalCode, other.practicalCode) &&
                lectureBlockInfos.equals(other.lectureBlockInfos) &&
                tutorialBlockInfos.equals(other.tutorialBlockInfos) &&
                practicalBlockInfos.equals(other.practicalBlockInfos);
    }
}
