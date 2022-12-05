package timetablesSorter_use_case;

/**
 * Interface used for SorterInteractor dependency inversion
 */
public interface SorterInputBoundary {
    /**
     * sorts timetables by the User's preferences
     * @param requestModel data structure required for sorting
     * @return
     */
    SorterResponseModel preferenceSort(SorterRequestModel requestModel);
}
