package recommend_br_use_case.application_business;

import java.util.HashSet;

/**
 * Class representing the input data from the user for recommend BR use case.
 */
public class RecommendBRRequestModel {

    private HashSet<String> brCategoriesSelected;
    private String preferredTime;

    /**
     * Constructs RecommendBRRequestModel with the given timetableID, set of breadth categories selected,
     * and preferred time
     *
     * @param brCategoriesSelected set of breadth categories represented by "1", "2", "3", "4", or "5"
     * @param preferredTime preferred time represented by "early", "balanced", "late", or "don't care"
     */
    public RecommendBRRequestModel(HashSet<String> brCategoriesSelected, String preferredTime) {
        this.brCategoriesSelected = brCategoriesSelected;
        this.preferredTime = preferredTime;
    }

    /**
     * Returns set of breadth categories contained in this request model
     *
     * @return set of breadth categories contained in this request model
     */
    public HashSet<String> getBrCategoriesSelected() {
        return brCategoriesSelected;
    }

    /**
     * Returns preferred time of this request model
     *
     * @return preferred time of this request model
     */
    public String getPreferredTime() {
        return preferredTime;
    }

    /**
     * Set breadth categories of this request model to the given set of breadth categories
     *
     * @param brCategoriesSelected new set of breadth categories represented by "1", "2", "3", "4", or "5"
     */
    public void setBrCategoriesSelected(HashSet<String> brCategoriesSelected) {
        this.brCategoriesSelected = brCategoriesSelected;
    }

    /**
     * Set preferred time of this request model to the given preferred time
     *
     * @param preferredTime new preferred time represented by "early", "balanced", "late", or "don't care"
     */
    public void setPreferredTime(String preferredTime) {
        this.preferredTime = preferredTime;
    }
}
