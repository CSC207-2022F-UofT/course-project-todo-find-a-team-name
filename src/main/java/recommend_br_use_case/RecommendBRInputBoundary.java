package recommend_br_use_case;

/**
 * Interface used to do dependence inversion for RecommendBRInteractor.
 */
public interface RecommendBRInputBoundary {

    /**
     * perform breadth course recommendation given the request model
     *
     * @param requestModel data used to recommend breadth courses
     */
    void recommendBr(RecommendBRRequestModel requestModel);
}
