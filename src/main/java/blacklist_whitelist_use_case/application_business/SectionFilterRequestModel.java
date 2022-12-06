package blacklist_whitelist_use_case.application_business;

import java.util.List;

/**
 * Class representing the input data from the user for Blacklist/Whitelist use case.
 */
public class SectionFilterRequestModel {
    private final String isInstructorBlackList;
    private final String isRoomBlackList;
    private final String isDayBlackList;
    private final String isTimeBlackList;
    private final String courseCodes;
    private final String instructorConstraints;
    private final String roomConstraints;
    private final List<Integer> dayConstraints;
    private final String startTime;
    private final String endTime;

    /**
     * Constructs SectionFilterRequestModel with the sessionType, courseCodes, Blacklist/Whitelist List Type for each
     * Constraints, and the corresponding Constraints domain
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
    public SectionFilterRequestModel(String courseCodes,
                                     String isInstructorBlackList,
                                     String isRoomBlackList,
                                     String isDayBlackList,
                                     String isTimeBlackList,
                                     String instructorConstraints,
                                     String roomConstraints,
                                     List<Integer> dayConstraints,
                                     String startTime,
                                     String endTime) {
        this.isInstructorBlackList = isInstructorBlackList;
        this.isRoomBlackList = isRoomBlackList;
        this.isDayBlackList = isDayBlackList;
        this.isTimeBlackList = isTimeBlackList;
        this.courseCodes = courseCodes;
        this.instructorConstraints = instructorConstraints;
        this.roomConstraints = roomConstraints;
        this.dayConstraints = dayConstraints;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getIsInstructorBlackList() {
        return isInstructorBlackList;
    }

    public String getIsRoomBlackList() {
        return isRoomBlackList;
    }

    public String getIsDayBlackList() {
        return isDayBlackList;
    }

    public String getIsTimeBlackList() {
        return isTimeBlackList;
    }

    public String getCourseCodes() {
        return courseCodes;
    }

    public String getInstructorConstraints() {
        return instructorConstraints;
    }

    public String getRoomConstraints() {
        return roomConstraints;
    }

    public List<Integer> getDayConstraints() {
        return dayConstraints;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SectionFilterRequestModel)) return false;
        SectionFilterRequestModel that = (SectionFilterRequestModel) o;
        return isInstructorBlackList.equals(that.isInstructorBlackList) && isRoomBlackList.equals(that.isRoomBlackList) && isDayBlackList.equals(that.isDayBlackList) && isTimeBlackList.equals(that.isTimeBlackList) && courseCodes.equals(that.courseCodes) && instructorConstraints.equals(that.instructorConstraints) && roomConstraints.equals(that.roomConstraints) && dayConstraints.equals(that.dayConstraints) && startTime.equals(that.startTime) && endTime.equals(that.endTime);
    }


    /**
     * Used for testing purpose initially.
     * @return a String representation of the RequestModel.
     */

    @Override
    public String toString() {
        return "SectionFilterRequestModel{" +
                "isInstructorBlackList='" + isInstructorBlackList + '\'' +
                ", isRoomBlackList='" + isRoomBlackList + '\'' +
                ", isDayBlackList='" + isDayBlackList + '\'' +
                ", isTimeBlackList='" + isTimeBlackList + '\'' +
                ", courseCodes=" + courseCodes +
                ", instructorConstraints=" + instructorConstraints +
                ", roomConstraints=" + roomConstraints +
                ", dayConstraints=" + dayConstraints +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}
