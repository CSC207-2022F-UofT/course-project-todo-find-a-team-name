package overlap_crap_fix_locations_later.interface_adapters;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import overlap_crap_fix_locations_later.application_business.OverlapOutputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * A presenter who's job it is to pass some models gained from the interactor and throw that shit into
 * the InputDialog so that it may be used as input.
 */
public class OverlapMaxPresenter implements OverlapOutputBoundary {

    private OverlapInputView dialogToPassTo = null;

    /**
     * Convert a list of timetableModels to ViewModels and then pass them to the dialog.
     **/
    @Override
    public void passViewModelsToDialog(List<TimetableModel> timetableModels) {
        ArrayList<OverlapTimetableViewModel> timetableViewModels = new ArrayList<>();
        for (TimetableModel timetableModel : timetableModels) {
            timetableViewModels.add(ModelToOverlapViewModelConverter.convertTimetableModel(timetableModel));
        }

        dialogToPassTo.stashTimetableViewModels(timetableViewModels);
    }

    /**
     * Pass the bestMatchingTimetable as a TimetableViewModel to the Dialog.
     **/
    @Override
    public void passBestTimetable(TimetableModel timetableModel) {
        TimetableViewModel bestTimetableViewModel = TimetableModelConverter.timetableToView(timetableModel);
        dialogToPassTo.stashBestMatchingTimetable(bestTimetableViewModel);
    }

    /**
     * SET the view of this presenter! Like, the dialog to input to!!!
     */
    public void setDialogToPassTo(OverlapInputView dialog) {
        this.dialogToPassTo = dialog;
    }
}
