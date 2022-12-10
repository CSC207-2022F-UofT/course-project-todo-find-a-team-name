package timetables_sort_use_case.application_business;

/**
 * The request model used to request timetables sorting
 * Instance Attributes:
 * - timeButton: the user's time preference
 * - breakButton: the user's break preference
 */
public class TimetablesSortRequestModel {

    private final String timeButton;
    private final String breakButton;
    public TimetablesSortRequestModel(String timeButton, String breakButton) {
        this.timeButton = timeButton;
        this.breakButton = breakButton;
    }
    public String getTimeButton() {
        return timeButton;
    }
    public String getBreakButton() {
        return breakButton;
    }
}
