package timetables_sort_use_case.application_business;

public class TestTimetablesSortInputBoundary implements TimetablesSortInputBoundary {

    private String timeButton;
    private String breakButton;
    @Override
    public void timetablesSort(TimetablesSortRequestModel request) {
         timeButton = request.getTimeButton();
         breakButton = request.getBreakButton();
    }

    public String getBreakButton() {
        return breakButton;
    }

    public String getTimeButton() {
        return timeButton;
    }
}
