package timetables_sort_use_case.application_business;

import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class AllTimetablesPublisher implements AllTimetablesInputBoundary, Flow.Publisher<Object>{
    private final ArrayList<Flow.Subscriber<Object>> subscribers = new ArrayList<>();

    public void subscribe(Flow.Subscriber<Object> subscriber) {
        this.subscribers.add(subscriber);
    }

    public void updateTimetable(TimetableModel timetable) {
        for (Flow.Subscriber<Object> subscriber : subscribers) {
            subscriber.onNext(timetable);
        }
    }
}
