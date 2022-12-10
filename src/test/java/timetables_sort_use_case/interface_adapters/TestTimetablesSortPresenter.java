package timetables_sort_use_case.interface_adapters;

import timetables_sort_use_case.application_business.TimetablesSortOutputBoundary;
import timetables_sort_use_case.application_business.TimetablesSortResponseModel;

public class TestTimetablesSortPresenter implements TimetablesSortOutputBoundary {
    public TimetablesSortResponseModel response;
    @Override
    public void prepareView(TimetablesSortResponseModel responseModel) {
        response = responseModel;
    }

    @Override
    public void setView(AllTimetablesView view) {

    }
}
