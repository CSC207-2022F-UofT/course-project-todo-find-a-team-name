package display_timetable_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.application_business.EntityConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;

public class DisplayTimetableInteractor implements DisplayTimetableInputBoundary{
    private Timetable timetable = null;
    private final DisplayTimetableOutputBoundary presenter;

    public DisplayTimetableInteractor(DisplayTimetableOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void displayTimetable() {
        if (timetable != null){
            TimetableModel responseModel = EntityConverter.generateTimetableResponse(timetable);
            presenter.prepareTimetable(responseModel);
        } else {
           presenter.prepareFailView("Timetable not loaded yet!");
        }
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

}
