package timetables_sort_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

import java.util.concurrent.Flow;

/**
 * A concrete implementation of the SorterInputBoundary, used in the timetables sort use case.
 * timetables are the timetables being reordered.
 * presenter is the SorterOutputBoundary that updates in response to the user's input
 * TODO: the actual thing and flow stuff
 */
public class TimetablesSortInteractor implements TimetablesSortInputBoundary, Flow.Subscriber<Object> {

    private Timetable[] timetables;
    private final TimetablesSortOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;

    public TimetablesSortInteractor(TimetablesSortOutputBoundary presenter) {
        this.presenter = presenter;
    }

    @Override
    public void sort(TimetablesSortRequestModel request) {
        System.out.println("interactor");
        TimetableModel[] timetablesModel = new TimetableModel[timetables.length];
        for(int i = 0; i < timetables.length; i++) {
            timetablesModel[i] = retrieveInteractor.retrieveTimetable();
        }
         TimetablesSortResponseModel response = new TimetablesSortResponseModel(timetablesModel);
    }

    /**
     *
     * @param timetables updates the timetables used by the interactor
     */
    @Override
    public void setTimetables(Timetable[] timetables) {
        this.timetables = timetables;
    }

    /**
     * @param retrieveInteractor the RetrieveTimetableInputBoundary used to create a view model of
     *                           an updated timetable.
     */
    @Override
    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor) {
        this.retrieveInteractor = retrieveInteractor;
    }

    /**
     *
     * @param retrieveInteractor the RetrieveTimetableInputBoundary used to create view models of the updated timetables
     */
    public void setRetrieveInputBoundary(RetrieveTimetableInputBoundary retrieveInteractor) {
        this.retrieveInteractor = retrieveInteractor;
    }

    /**
     * @param subscription a new subscription.
     *                     A method called when the interactor subscribes to a new Subscription, currently does nothing.
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     *
     * @param item
     */
    @Override
    public void onNext(Object item) {

    }
    /**
     * @param throwable the exception encountered by either the Subscriber or Publisher.
     *                  This method is called when a throwable is thrown by the Subscriber or Publisher, and
     *                  currently does nothing.
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     * Method invoked when no other Subscriber method invocations will occur. Does nothing currently.
     */
    @Override
    public void onComplete() {

    }
}
