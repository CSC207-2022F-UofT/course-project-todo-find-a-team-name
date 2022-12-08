package overlap_crap_fix_locations_later.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

public interface OverlapOutputBoundary {

    /**
     * Pass viewModels to the dialog, given a list of TimetableModels
     **/
    public void passViewModelsToDialog(List<TimetableModel> timetableModels);

    /**
     * Pass the best
     * matching timetable as a TimetableViewModel to the Dialog (for use with Kai's TimetableView),
     * given a TimetableModel from the Interactor.
     */
    public void passBestTimetable(TimetableModel timetableModel);
}
