package blacklist_whitelist_use_case.application_business;

/**
 * Interface used to perform dependence inversion for SectionFilterPresenter class.
 */
public interface SectionFilterOutputBoundary {
    /**
     *
     * @param responseModel a data structure that stores all the information about
     *                      modified courses and sections.
     */
    void prepareSuccessView(SectionFilterResponseModel responseModel);

    /**
     * Format the response model message to prepare for fail view.
     * @param error error message going to be displayed for fail view.
     */
    void prepareFailView(String error);

}