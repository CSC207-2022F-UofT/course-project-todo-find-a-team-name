package blacklist_whitelist_use_case.interface_adapters;

import blacklist_whitelist_use_case.application_business.SectionFilterInputBoundary;
import blacklist_whitelist_use_case.application_business.SectionFilterRequestModel;

/**
 * Controller for BlackListWhiteListUseCase responsible for requesting the interactor to apply those constraints
 * to filter out the sections that fails the constraint.
 */
public class SectionFilterController {
    private final SectionFilterInputBoundary sectionFilterInteractor;

    /**
     * Constructs SectionFilterController with a passed in SectionFilterInteractor that implements the
     * SectionFilterInputBoundary.
     * @param sectionFilterInteractor blacklist/whitelist use case interactor responsible for filtering out the
     *                                unwanted sections of a calendar course after applying constraints.
     */
    public SectionFilterController(SectionFilterInputBoundary sectionFilterInteractor){
        this.sectionFilterInteractor = sectionFilterInteractor;
    }

    /**
     * Filter out the unwanted sections of a calendar course after applying constraints and display to the user.
     * @param requestModel Data Structure representing the input data from the user for Blacklist/Whitelist use case.
     */
    public void filter(SectionFilterRequestModel requestModel){
        sectionFilterInteractor.filter(requestModel);
    }
}
