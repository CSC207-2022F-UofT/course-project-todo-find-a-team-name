package blacklist_whitelist_use_case;

import java.util.List;

/**
 * Class representing the input data from the user for Blacklist/Whitelist use case.
 */
public class SectionFilterRequestModel {
    private String sessionType;
    private String isInstructorBlackList;
    private String isRoomBlackList;
    private String isDayBlackList;
    private String isTimeBlackList;
    private String courseCodes;
    private String instructorConstraints;
    private String roomConstraints;
    private List<Integer> dayConstraints;
    private String startTime;
    private String endTime;

    /**
     * Constructs SectionFilterRequestModel with the sessionType, courseCodes, Blacklist/Whitelist List Type for each
     * Constraints, and the corresponding Constraints domain
     *
     * @param sessionType a String representation of session by "S" or "F"
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
    public SectionFilterRequestModel(String sessionType,
                                     String courseCodes,
                                     String isInstructorBlackList,
                                     String isRoomBlackList,
                                     String isDayBlackList,
                                     String isTimeBlackList,
                                     String instructorConstraints,
                                     String roomConstraints,
                                     List<Integer> dayConstraints,
                                     String startTime,
                                     String endTime) {
        this.sessionType = sessionType;
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

    public String getSessionType() {
        return sessionType;
    }

    public void setSessionType(String sessionType) {
        this.sessionType = sessionType;
    }

    public String getIsInstructorBlackList() {
        return isInstructorBlackList;
    }

    public void setInstructorBlackList(String instructorBlackList) {
        isInstructorBlackList = instructorBlackList;
    }

    public String getIsRoomBlackList() {
        return isRoomBlackList;
    }

    public void setRoomBlackList(String roomBlackList) {
        isRoomBlackList = roomBlackList;
    }

    public String getIsDayBlackList() {
        return isDayBlackList;
    }

    public void setDayBlackList(String dayBlackList) {
        isDayBlackList = dayBlackList;
    }

    public String getIsTimeBlackList() {
        return isTimeBlackList;
    }

    public void setTimeBlackList(String timeBlackList) {
        isTimeBlackList = timeBlackList;
    }

    public String getCourseCodes() {
        return courseCodes;
    }

    public void setCourseCodes(String courseCodes) {
        this.courseCodes = courseCodes;
    }

    public String getInstructorConstraints() {
        return instructorConstraints;
    }

    public void setInstructorConstraints(String instructorConstraints) {
        this.instructorConstraints = instructorConstraints;
    }

    public String getRoomConstraints() {
        return roomConstraints;
    }

    public void setRoomConstraints(String roomConstraints) {
        this.roomConstraints = roomConstraints;
    }

    public List<Integer> getDayConstraints() {
        return dayConstraints;
    }

    public void setDayConstraints(List<Integer> dayConstraints) {
        this.dayConstraints = dayConstraints;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
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
