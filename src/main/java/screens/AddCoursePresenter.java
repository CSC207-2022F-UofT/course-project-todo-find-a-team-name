package screens;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import edit_timetable_use_case.AddCourseOutputBoundary;
import edit_timetable_use_case.EditTimetableResponseModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

public class AddCoursePresenter implements AddCourseOutputBoundary {
    private EditTimetableView view;

    public AddCoursePresenter(){}
    public AddCoursePresenter(EditTimetableView view){
        this.view = view;
    }
    @Override
    public void prepareView(EditTimetableResponseModel responseModel)  {
        TimetableModel updatedTimetable = responseModel.getUpdatedTimetable();
        TimetableViewModel ttViewModel = TimetableModelConverter.timetableToView(updatedTimetable);
        view.updateTimetable(ttViewModel);
        view.displayResponse(responseModel.getCourseCode() + " was successfully added.");
    }

    public void setView(EditTimetableView view){
        this.view = view;
    }
}
