package timetables_sort_use_case.application_business;

import entities.Timetable;
import entities.TimetableCourse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

import java.util.ArrayList;
import java.util.concurrent.Flow;
import java.util.List;

/**
 * A suite of tests to confirm that AllTimetablesPublisher properly updates all its subscribers
 */
public class AllTimetablesInteractorTest {

    private AllTimetablesInteractor publisher;
    private TestSubscriberUser User;
    private Flow.Subscriber<Object> subscriber;
    private Timetable emptyTimetable;

    @BeforeEach
    void setUp(){
         this.publisher = new AllTimetablesInteractor();
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
         ArrayList<TimetableCourse> emptyCourselist = new ArrayList<>();
         Timetable emptyTimetable = new Timetable(emptyCourselist, "F");
         List<Timetable> updatedTimetables = new ArrayList<>();
         updatedTimetables.add(emptyTimetable);
         publisher.onNext(updatedTimetables);
         this.emptyTimetable = emptyTimetable;
    }

    /**
     * A test showing that the interactor correctly updates its timetables and correctly passes to subscribers
     */
    @Test
    void UpdateSubscribers() {

        publisher.subscribe(subscriber);
        publisher.updateSubscribers(0);
        Assertions.assertEquals(emptyTimetable, User.timetable);
    }
}
