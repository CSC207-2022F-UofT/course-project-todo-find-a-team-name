package fileio_use_case.interface_adapters;

import fileio_use_case.application_business.timetable_specific_classes.SaveTimetableInteractor;

public class SaveTimetableController {
    private final SaveTimetableInteractor interactor;
    public SaveTimetableController(SaveTimetableInteractor interactor) {
        this.interactor = interactor;
    }

    /**
     * Calls save timetable interactor to pass in timetable to timetable gateway
     */
    public void saveTimetable()  {
        this.interactor.updatingTimetableToFile();
    }
}
