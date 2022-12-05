package blacklist_whitelist_use_case.application_business;

import java.util.HashMap;
import java.util.List;

/**
 * Class representing Modified Calendar Course, containing session "S" or "F", and a dictionary mapping
 * course codes to filtered section codes, such that it could be used to
 */
public class SectionFilterResponseModel {
    private final String sessionType;
    private final HashMap<String, List<String>> modifiedCourses;

    /**
     * Construct SectionFilterResponseModel given the course and section codes and blocks represented by BRBlockResponseModel
     * @param modifiedCourses a HashMap mapping course codes to filtered section codes.
     * @param sessionType a string representing a session. eg. "S","F".
     */

    public SectionFilterResponseModel(HashMap<String, List<String>> modifiedCourses, String sessionType) {
        this.modifiedCourses = modifiedCourses;
        this.sessionType = sessionType;
    }

    /**
     * returns the Map of courseCode to "filtered" sectionCodes.
     * @return the Map of courseCode to "filtered" sectionCodes.
     */

    public HashMap<String, List<String>> getModifiedCourses() {
        return modifiedCourses;
    }

    /**
     * returns the session of courses.
     * @return a string representing the session of courses.
     */
    public String getSessionType() {
        return sessionType;
    }

    /**
     * ToString method for checking purpose only
     * @return string representation of the responseModel.
     */
    @Override
    public String toString() {
        return "SectionFilterResponseModel{" +
                "modifiedCourses=" + modifiedCourses +
                '}';
    }
}