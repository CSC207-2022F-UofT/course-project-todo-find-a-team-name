package overlap_crap_fix_locations_later.application_business;

import entities.Timetable;
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

    private final OverlapOutputBoundary presenter;

    public OverlapGeneratedTimetableRelayInteractor(OverlapOutputBoundary presenter) {
        this.presenter = presenter;
    }

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
        presenter.passViewModelsToDialog(timetableModels);

        timetableList = new ArrayList<>();

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}

