package overlap_crap_fix_locations_later.InputBoundaries;

import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

public interface OverlapPresenting {

    /**
     * Pass viewModels to the dialog, given a list of TimetableModels
     **/
    public void passViewModelsToDialog(List<TimetableModel> timetableModels);
}
