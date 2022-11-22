package screens;

import recommend_br_use_case.RecommendBRInputBoundary;
import recommend_br_use_case.RecommendBRRequestModel;

import java.util.HashSet;

/**
 * Controller for Recommend BR use case, responsible for requesting interactor to
 * recommend breadth courses
 */
public class RecommendBRController {

    private final RecommendBRInputBoundary interactor;

    /**
     * Constructs RecommendBRController given RecommendBRInputBoundary (interactor)
     *
     * @param interactor interactor responsible for recommending breadth courses
     */
    public RecommendBRController(RecommendBRInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Recommends breadth courses to the user and displays the result
     *
     * @param brCategoriesSelected breadth categories selected by user
     * @param preferredTime preferred time selected by user
     */
    public void recommendBr(HashSet<String> brCategoriesSelected, String preferredTime){

        RecommendBRRequestModel recommendBRRequestModel = new RecommendBRRequestModel(brCategoriesSelected, preferredTime);

        interactor.recommendBr(recommendBRRequestModel);
    }




}
