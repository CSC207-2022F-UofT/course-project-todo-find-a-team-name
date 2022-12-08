package timetable_generator_use_case.interface_adapters;
//import display_timetable_use_case.interface_adapters.TimetableViewModel;
//
//import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
//
//import timetable_generator_use_case.application_business.TimetableGeneratorOutputBoundary;
//import timetable_generator_use_case.application_business.TimetableGeneratorResponseModel;
//import timetables_sort_use_case.interface_adapters.AllTimetablesView;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import timetable_generator_use_case.application_business.TimetableGeneratorOutputBoundary;
import timetable_generator_use_case.application_business.TimetableGeneratorResponseModel;
import timetables_sort_use_case.interface_adapters.AllTimetablesView;

public class TimetableGeneratorPresenter implements TimetableGeneratorOutputBoundary {
    private AllTimetablesView view;

    @Override
    public void prepareSuccessView(TimetableGeneratorResponseModel responseModel) {
        TimetableViewModel[] timetableViewModels = new TimetableViewModel[responseModel.getGeneratedTimetables().size()];
        for (int i = 0; i < timetableViewModels.length; i++){
            timetableViewModels[i] = TimetableModelConverter.timetableToView(responseModel.getGeneratedTimetables().get(i));
        }
        view.updateTimetables(timetableViewModels);
    }

    public void setView(AllTimetablesView view) {
        this.view = view;
    }

}
