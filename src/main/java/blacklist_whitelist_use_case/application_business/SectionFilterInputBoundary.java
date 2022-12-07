package blacklist_whitelist_use_case.application_business;

/**
 * Interface used to make dependence inversion for SectionFilterInteractor.
 */

public interface SectionFilterInputBoundary {
    /**
     * perform section filter given the request model
     *
     * @param requestModel a data structure that stores all the information about
     *                       sessionType, courseDomain, constraintDomain, and constraintType.
     */
    void filter(SectionFilterRequestModel requestModel);
}
