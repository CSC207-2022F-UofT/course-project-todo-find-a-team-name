package recommend_br_use_case.application_business;

import recommend_br_use_case.application_business.BRSectionResponseModel;

/**
 * Class representing TimetableCourse entity containing code, title, breadth category, and sections
 * represented by BRSectionResponseModel.
 *
 */
public class BRCourseResponseModel {
    private String code;
    private String title;
    private String brCategory;
    private BRSectionResponseModel lectureModel;
    private BRSectionResponseModel tutorialModel;
    private BRSectionResponseModel practicalModel;


    /**
     * Create BRCourseResponseModel from the given code, title, breadth category, and sections
     * represented by BRSectionResponseModel
     *
     * @param code code of the timetable course
     * @param title title of the timetable course
     * @param brCategory breadth category of the timetable course, represented by "1", "2", "3", "4", or "5"
     * @param lectureModel BRSectionResponseModel representing lecture of the timetable course
     * @param tutorialModel BRSectionResponseModel representing tutorial of the timetable course
     * @param practicalModel BRSectionResponseModel representing practical of the timetable course
     */
    public BRCourseResponseModel(String code, String title, String brCategory, BRSectionResponseModel lectureModel,
                                 BRSectionResponseModel tutorialModel, BRSectionResponseModel practicalModel){
        this.code = code;
        this.title = title;
        this.brCategory = brCategory;
        this.lectureModel = lectureModel;
        this.tutorialModel = tutorialModel;
        this.practicalModel = practicalModel;
    }

    /**
     * Returns breadth category of the timetable course represented by this model
     *
     * @return breadth category of the timetable course represented by this model
     */
    public String getBrCategory() {
        return brCategory;
    }

    /**
     * Returns title of the timetable course represented by this model
     *
     * @return title of the timetable course represented by this model
     */
    public String getTitle() {
        return title;
    }

    /**
     * Returns course code of the timetable course represented by this model
     *
     * @return course code of the timetable course represented by this model
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns BRSectionResponseModel corresponding to the lecture of the
     * timetable course represented by this model
     *
     * @return Returns BRSectionResponseModel corresponding to the lecture of the
     * timetable course represented by this model
     */
    public BRSectionResponseModel getLectureModel() {
        return lectureModel;
    }

    /**
     * Returns BRSectionResponseModel corresponding to the tutorial of the
     * timetable course represented by this model
     *
     * @return Returns BRSectionResponseModel corresponding to the tutorial of the
     * timetable course represented by this model
     */
    public BRSectionResponseModel getTutorialModel() {
        return tutorialModel;
    }

    /**
     * Returns BRSectionResponseModel corresponding to the practical of the
     * timetable course represented by this model
     *
     * @return Returns BRSectionResponseModel corresponding to the practical of the
     * timetable course represented by this model
     */
    public BRSectionResponseModel getPracticalModel() {
        return practicalModel;
    }

    /**
     * Sets the course code of the timetable course represented by this model to given code
     *
     * @param code new code of the timetable course
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * Sets the title of the timetable course represented by this model to given title
     *
     * @param title new title of the timetable course
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * Sets the breadth category of the timetable course represented by this model to given brCategory
     *
     * @param brCategory new breadth category of the timetable course
     */
    public void setBrCategory(String brCategory) {
        this.brCategory = brCategory;
    }

    /**
     * Sets the lecture of the timetable course represented by this model to given lectureModel
     *
     * @param lectureModel new lecture of the timetable course, represented by BRSectionResponseModel
     */
    public void setLectureModel(BRSectionResponseModel lectureModel) {
        this.lectureModel = lectureModel;
    }

    /**
     * Sets the tutorial of the timetable course represented by this model to given tutorialModel
     *
     * @param tutorialModel new tutorial of the timetable course, represented by BRSectionResponseModel
     */
    public void setTutorialModel(BRSectionResponseModel tutorialModel) {
        this.tutorialModel = tutorialModel;
    }

    /**
     * Sets the practical of the timetable course represented by this model to given practicalModel
     *
     * @param practicalModel new practical of the timetable course, represented by BRSectionResponseModel
     */
    public void setPracticalModel(BRSectionResponseModel practicalModel) {
        this.practicalModel = practicalModel;
    }
}
