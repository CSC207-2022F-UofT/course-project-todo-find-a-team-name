package timetablesSorter_use_case;

/**
 * The output boundary of the Sort use case
 */
public interface SorterOutputBoundary {
    /**
     * @param responseModel: a SorterReponseModel that contains the timetables that the User wants to sort
     * @return the input response model, reorder such that his preferences are on top
     */
    SorterResponseModel prepareView(SorterResponseModel responseModel);
}
