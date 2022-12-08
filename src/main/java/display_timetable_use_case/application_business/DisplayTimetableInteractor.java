package display_timetable_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.application_business.EntityConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.concurrent.Flow.Subscription;
import java.util.concurrent.Flow.Subscriber;

/**
 * Interactor for display timetable use case, which communicates with the presenter to
 * display the timetable
 */
public class DisplayTimetableInteractor implements DisplayTimetableInputBoundary, Subscriber<Object>{
    private Timetable timetable = null;
    private final DisplayTimetableOutputBoundary presenter;

    /**
     * Constructs the DisplayTimetableInteractor from the given presenter
     *
     * @param presenter presenter for display timetable use case
     */
    public DisplayTimetableInteractor(DisplayTimetableOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * Tells the presenter to prepare for displaying the timetable if timetable is not null.
     * If timetable is null tell the presenter to prepare for displaying the fail view with error message.
     */
    @Override
    public void displayTimetable() {
        if (timetable != null){
            TimetableModel responseModel = EntityConverter.generateTimetableResponse(timetable);
            presenter.prepareTimetable(responseModel);
        } else {
            presenter.prepareFailView("Timetable not loaded yet!");
        }
    }

    /**
     * Set the timetable to the given timetable
     *
     * @param timetable new timetable
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * Unimplemented method invoked prior to invoking any other Subscriber
     * methods for the given Subscription
     *
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Subscription subscription) {}

    /**
     * Update timetable if the given item is Session or Timetable.
     * @param item the item
     */
    @Override
    public void onNext(Object item) {
        if (item instanceof Timetable){
            timetable = (Timetable) item;
        }
    }

    /**
     * Unimplemented method invoked upon an unrecoverable error encountered by
     * a Publisher or Subscription.
     *
     * @param throwable the exception
     */
    @Override
    public void onError(Throwable throwable) {}

    /**
     * Unimplemented method invoked when it is known that no additional
     * Subscriber method invocation will occur.
     */
    @Override
    public void onComplete() {

    }
}
