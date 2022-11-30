package screens;

/**
 * Interface used to do dependency inversion for IRecommendView
 */
public interface IRecommendBRView {

    /**
     * display data contained in viewModel to the user
     *
     * @param viewModel data that represents all information shown in success view
     */
    void showSuccessView(RecommendBRViewModel viewModel);

    /**
     * Display error message to the user
     *
     * @param message message explaining how it failed
     */
    void showFailView(String message);
}
