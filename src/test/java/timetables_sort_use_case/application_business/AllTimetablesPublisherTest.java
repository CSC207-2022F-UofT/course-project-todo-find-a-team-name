package timetables_sort_use_case.application_business;

import entities.Timetable;
import entities.TimetableCourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * A suite of tests to confirm that AllTimetablesPublisher properly updates all its subscribers
 */
public class AllTimetablesPublisherTest {

    private AllTimetablesPublisher publisher;
    private TestSubscriberUser User;
    private Flow.Subscriber<Object> subscriber;

    @BeforeEach
    void setUp(){
         this.publisher = new AllTimetablesPublisher();
         this.subscriber = new Flow.Subscriber<Object>() {
             private Timetable timetable;
             @Override
             public void onSubscribe(Flow.Subscription subscription) {

             }

             @Override
             public void onNext(Object item) {
                 User.timetable = (Timetable) item;
             }

             @Override
             public void onError(Throwable throwable) {

             }

             @Override
             public void onComplete() {

             }
         };
         this.User = new TestSubscriberUser(subscriber);
    }

    /**
     * A test showing that the publisher correctly passes Timetable to subscribers
     */
    @Test
    void UpdateTimetable() {
        ArrayList<TimetableCourse> emptyCourselist = new ArrayList<>();
        Timetable emptyTimetable = new Timetable(emptyCourselist, "F");
        publisher.subscribe(subscriber);
        publisher.updateTimetable(emptyTimetable);
        Assertions.assertEquals(emptyTimetable, User.timetable);
    }
}
