package blacklist_whitelist_use_case.application_business;

import java.util.HashMap;
import java.util.List;

/**
 * Class representing Modified Calendar Course containing a dictionary mapping
 * course codes to filtered section codes, such that it could be used to
 */
public class SectionFilterResponseModel {
    private final HashMap<String, List<String>> modifiedCourses;

    /**
     * Construct SectionFilterResponseModel given the course and section codes and blocks represented by BRBlockResponseModel
     * @param modifiedCourses a HashMap mapping course codes to filtered section codes.
     */

    public SectionFilterResponseModel(HashMap<String, List<String>> modifiedCourses) {
        this.modifiedCourses = modifiedCourses;
    }

    /**
     * returns the Map of courseCode to "filtered" sectionCodes.
     * @return the Map of courseCode to "filtered" sectionCodes.
     */

    public HashMap<String, List<String>> getModifiedCourses() {
        return modifiedCourses;
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