package overlap_crap_fix_locations_later.presenters;

import overlap_crap_fix_locations_later.InputBoundaries.OverlapInputEntry;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapPresenting;
import overlap_crap_fix_locations_later.ViewModels.ModelToOverlapViewModelConverter;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A presenter who's job it is to pass some models gained from the interactor and throw that shit into
 * the InputDialog so that it may be used as input.
 */
public class OverlapMaxPresenter implements OverlapPresenting {

    private final OverlapInputEntry dialogToPassTo;

    public OverlapMaxPresenter(OverlapInputEntry inputDialog) {
        dialogToPassTo = inputDialog;
    }

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
}
