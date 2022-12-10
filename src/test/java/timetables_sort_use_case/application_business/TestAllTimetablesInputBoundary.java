package timetables_sort_use_case.application_business;

public class TestAllTimetablesInputBoundary implements AllTimetablesInputBoundary {
    private int j;

    @Override
    public void updateSubscribers(int i) {
        this.j = i;
    }

    public int getJ() {
        return j;
    }
}
