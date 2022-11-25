package recommend_br_use_case;

/**
 * Interface used to do dependence inversion for RecommendBRPresenter.
 */
public interface RecommendBROutputBoundary {
    /**
     * Format the response model to prepare for success view.
     *
     * @param responseModel data formatted to prepare for success view
     */
    void prepareSuccessView(RecommendBRResponseModel responseModel);

    /**
     * Format the message to prepare for fail view
     *
     * @param message text presented to the view
     */
    void prepareFailView(String message);
}
