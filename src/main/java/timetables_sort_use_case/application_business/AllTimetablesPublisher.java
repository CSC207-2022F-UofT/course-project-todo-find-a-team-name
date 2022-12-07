package timetables_sort_use_case.application_business;

import entities.Timetable;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class AllTimetablesPublisher implements AllTimetablesInputBoundary, Flow.Publisher<Object>{
    private final ArrayList<Flow.Subscriber<Object>> subscribers = new ArrayList<>();

    public void subscribe(Flow.Subscriber<Object> subscriber) {
        this.subscribers.add(subscriber);
    }

    public void updateTimetable(Timetable timetable) {
        for (Flow.Subscriber<Object> subscriber : subscribers) {
            subscriber.onNext(timetable);
        }
    }
}
