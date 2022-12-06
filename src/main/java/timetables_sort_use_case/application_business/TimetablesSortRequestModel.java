package timetables_sort_use_case.application_business;

/**
 * The request model used to request timetables sorting
 * Instance Attributes
 */
public class TimetablesSortRequestModel {
    private final String timeButton;
    private final String breakButton;

    /**
     * @param timeButton the time period that the user prefers.
     * @param breakButton the format of breaks that the user prefers.
     */
    public TimetablesSortRequestModel(String timeButton, String breakButton) {
        this.timeButton = timeButton;
        this.breakButton = breakButton;
    }

    /**
     * @return returns the time period that the User prefers
     */
    public String getTimeButton() {
        return timeButton;
    }

    /**
     * @return returns the break format that the User prefers
     */
    public String getBreakButton() {
        return breakButton;
    }
}
