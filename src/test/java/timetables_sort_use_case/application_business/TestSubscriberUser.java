package timetables_sort_use_case.application_business;

import entities.Timetable;

import java.util.concurrent.Flow;

public class TestSubscriberUser {
    public Timetable timetable;
    private Flow.Subscriber<Object> subscriber;

    public TestSubscriberUser(Flow.Subscriber<Object> subscriber) {
        this.subscriber = subscriber;
    }
}
