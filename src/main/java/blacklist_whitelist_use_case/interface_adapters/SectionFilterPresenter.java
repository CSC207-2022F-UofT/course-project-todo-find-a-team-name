package blacklist_whitelist_use_case.interface_adapters;

import blacklist_whitelist_use_case.application_business.SectionFilterOutputBoundary;
import blacklist_whitelist_use_case.application_business.SectionFilterResponseModel;

import java.util.Collections;

/**
 * Presenter is responsible for format the response model to prepare for success view to the output window.
 */
public class SectionFilterPresenter implements SectionFilterOutputBoundary {
    ISectionFilterView view;

    /**
     * Constructor for SecionFilterPresenter to prepare success view format to the output screen.
     */
    public SectionFilterPresenter(){}

    /**
     * Format the SectionFilterResponseModel converted to SectionFilterViewModel, and pass the view model to view to
     * display in the output screen.
     * @param responseModel a data structure that stores all the information about
     *                      modified courses and sections.
     */
    @Override
    public void prepareSuccessView(SectionFilterResponseModel responseModel) {
        for (String course: responseModel.getModifiedCourses().keySet()){
            Collections.sort(responseModel.getModifiedCourses().get(course));
        }
        SectionFilterViewModel viewModel = new SectionFilterViewModel(responseModel.getModifiedCourses());
        view.showSuccessView(viewModel);
    }

    /**
     *Format the SectionFilterResponseModel converted to SectionFilterViewModel, and pass the String the Screen to
     * display a warning or error message.
     * @param error error message going to be displayed for fail view.
     */
    @Override
    public void prepareFailView(String error){
        view.showFailView(error);
    }

    /**
     * Set the view of the presenter
     * @param view view object to display output.
     */
    public void setView(ISectionFilterView view){
        this.view = view;
    }
}

