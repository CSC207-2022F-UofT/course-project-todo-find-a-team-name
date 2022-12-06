package blacklist_whitelist_use_case.interface_adapters;

import java.util.HashMap;
import java.util.List;

/**
 * Class storing data needed for displaying courseCodes and the filtered section codes for BlackList/Whitelist use case.
 */
public class SectionFilterViewModel {
    private final HashMap<String, List<String>> modifiedCourses;

    /**
     * Constructor of SectionFilterViewModel.
     * Class storing data needed for displaying courseCodes and the filtered section codes for BlackList/Whitelist
     * use case.
     *
     * @param modifiedCourses a hashmap of course codes and filtered section codes representing available sections
     *                        of each course.
     */
    public SectionFilterViewModel(HashMap<String, List<String>> modifiedCourses) {
        this.modifiedCourses = modifiedCourses;
    }

    /**
     * return a hashmap of course codes and filtered section codes representing available sections of each course.
     *
     * @return a hashmap of course codes and filtered section codes representing available sections of each course.
     */
    public HashMap<String, List<String>> getModifiedCourses() {
        return modifiedCourses;
    }

}