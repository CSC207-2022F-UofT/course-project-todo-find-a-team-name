package overlap_crap_fix_locations_later;

import entities.Timetable;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapInputEntry;
import overlap_crap_fix_locations_later.ViewModels.ModelToOverlapViewModelConverter;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.EntityConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * An interactor whose main purpose is to listen to TimetableGeneratorInteractor, and relay it's data through to
 * the inputDialog through a presenter.
 * <p>
 * I am assuming that this is subscribed to something that gives me a List<Timetable>.
 */
public class OverlapGeneratedTimetableRelayInteractor implements Flow.Subscriber<Object> {

    private List<Timetable> timetableList = new ArrayList<>();

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {
        // We will only really be getting a List<Timetable>, but check it anyway.
        if (item instanceof List) {
            for (Object thing : (List<?>) item) {
                if (thing instanceof Timetable) {
                    timetableList.add((Timetable) thing);
                }
            }
        }

        // Turn each timeTable into a model.
        List<TimetableModel> timetableModels = new ArrayList<>();
        for (Timetable timetable : timetableList) {
            timetableModels.add(EntityConverter.generateTimetableResponse(timetable));
        }

        // Pass it to the presenter, which will turn it into ViewModels.

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}

/**
 * A presenter who's job it is to pass some models gained from the interactor and throw that shit into
 * the InputDialog so that it may be used as input.
 */
class OverlapMaxPresenter implements OverlapPresenting {

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

interface OverlapPresenting {

    /**
     * Pass viewModels to the dialog, given a list of TimetableModels
     **/
    public void passViewModelsToDialog(List<TimetableModel> timetableModels);
}