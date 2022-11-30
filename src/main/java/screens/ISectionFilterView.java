package screens;

/**
 * Interface created for dependency inversion.
 */

public interface ISectionFilterView {
    /**
     * Display the data stored inside the viewModel to the user
     * @param viewModel a data structure that stores the information to be displayed to the user.
     */
    void showSuccessView(SectionFilterViewModel viewModel);

    /**
     * Display the error message to inform the user of a potential error in their input.
     * @param message an error message telling the user the reason their input is not valid.
     */
    void showFailView(String message);
}