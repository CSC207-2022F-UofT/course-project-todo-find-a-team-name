package blacklist_whitelist_use_case.interface_adapters;

import blacklist_whitelist_use_case.application_business.SectionFilterInputBoundary;
import blacklist_whitelist_use_case.application_business.SectionFilterRequestModel;

import java.util.List;

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
     *
     * @param courseCodes a String of coourseCodes seperated by comma from the user input.
     * @param isInstructorBlackList a String representing whether or not it's a blacklistConstraint.
     * @param isRoomBlackList a String representing whether or not it's a blacklistConstraint.
     * @param isDayBlackList a String representing whether or not it's a blackListConstraint.
     * @param isTimeBlackList a String representing whether or not it's a blackListConstraint.
     * @param instructorConstraints a String of input instructor names seperated by comma as constraints.
     * @param roomConstraints a String of input rooms seperated by comma as constraints.
     * @param dayConstraints a List of Integer input representing the weekdays. 0 represents MO, 1 TU, 2 WE, 3 TH, 4 FR
     * @param startTime a string representing the startTime limit of Time Constraints. eg. "11:30"
     * @param endTime a string representing the endTime limit of Time Constraints. eg. "22:30"
     */
    public void filter(
                       String courseCodes,
                       String isInstructorBlackList,
                       String isRoomBlackList,
                       String isDayBlackList,
                       String isTimeBlackList,
                       String instructorConstraints,
                       String roomConstraints,
                       List<Integer> dayConstraints,
                       String startTime,
                       String endTime){
        SectionFilterRequestModel requestModel = new SectionFilterRequestModel(
                courseCodes, isInstructorBlackList, isRoomBlackList, isDayBlackList, isTimeBlackList,
                instructorConstraints, roomConstraints, dayConstraints, startTime, endTime);
        sectionFilterInteractor.filter(requestModel);
    }
}
