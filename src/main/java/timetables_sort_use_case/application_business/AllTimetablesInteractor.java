package timetables_sort_use_case.application_business;

import entities.Timetable;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * A concrete implementation of the AllTimetablesInputBoundary, used in the TimetablesSort use case
 * subscribers is the list of subscribers for when the user chooses a timetable to further inspect
 * timetables is the list of timetables that the user is currently presented with, it is updated by
 * the publisher that this interactor is subscribed to (Timetable Generator Interactor)
 */
public class AllTimetablesInteractor implements AllTimetablesInputBoundary,
        Flow.Publisher<Object>, Flow.Subscriber<Object>{
    private final ArrayList<Flow.Subscriber<Object>> subscribers = new ArrayList<>();
    private List<Timetable> timetables = new ArrayList<>();
    public void subscribe(Flow.Subscriber<Object> subscriber) {
        this.subscribers.add(subscriber);
    }

    /**
     * Sends every subscriber the timetable that the user wants to inspect
     * @param i the index of the timetable that was chosen
     */
    public void updateSubscribers(int i) {
        for (Flow.Subscriber<Object> subscriber : subscribers) {
            Timetable timetable = this.timetables.get(i);
            subscriber.onNext(timetable);
        }

    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     * Updates this Interactor's timetables according to the publisher
     * Note that timetables is of type List<Timetable> unless this project is expanded on in the future
     * @param timetables the updated timetables
     */
    @Override
    public void onNext(Object timetables) {
        List<Timetable> updatedTimetables = new ArrayList<>();
        if (timetables instanceof List) {
            for (Object thing : (List<?>) timetables) {
                if (thing instanceof Timetable) {
                    updatedTimetables.add((Timetable) thing);
                }
            }
            this.timetables = updatedTimetables;
        }

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
