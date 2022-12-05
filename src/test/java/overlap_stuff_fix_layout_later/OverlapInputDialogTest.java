package overlap_stuff_fix_layout_later;

import entities.*;
import org.junit.Before;
import org.junit.Test;
import overlap_crap_fix_locations_later.OverlapInputDialog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Flow;

/** Consists of tests for the Dialog and for the use case in general for the moment. **/
public class OverlapInputDialogTest {

    class TestSubscriber implements Flow.Subscriber {

        Timetable result;
        @Override
        public void onSubscribe(Flow.Subscription subscription) {
            // Do nothing?
        }

        @Override
        public void onNext(Object item) {
            this.result = (Timetable) item;
        }

        @Override
        public void onComplete() {
            System.out.println(result);
        }

        @Override
        public void onError(Throwable throwable) {

        }
    }

    private TestSubscriber testListener;

    private Block testBlock1;
    private Block testBlock2;
    private Block testBlock3;
    private Block testBlock4;

    private ArrayList<Block> testBlockList1;
    private ArrayList<Block> testBlockList2;
    private ArrayList<Block> testBlockList3;

    private Section testSection1;
    private Section testSection2;
    private Section testSection3;
    private TimetableCourse marioCourse;
    private TimetableCourse francoisCourse;
    private TimetableCourse intersectCourse;

    @Before
    public void initialiseTestTimetableCourses() throws InvalidSectionsException {

        testListener = new TestSubscriber();

        Block testBlock1 = new Block("Monday", "14:00", "15:00", "Castle Badr");
        Block testBlock2 = new Block("Tuesday", "15:00", "16:00", "Port Badr");
        Block testBlock3 = new Block("Wednesday", "16:00", "17:00", "Badr Tower");
        Block testBlock4 = new Block("Thursday", "17:00", "18:00", "Badr Base");

        // Initialise test sections.
        ArrayList<Block> testBlockList1 = new ArrayList<>(Arrays.asList(testBlock1, testBlock2, testBlock3));
        ArrayList<Block> testBlockList2 = new ArrayList<>(Arrays.asList(testBlock2, testBlock3, testBlock4));
        ArrayList<Block> testBlockList3 = new ArrayList<>(Arrays.asList(testBlock1, testBlock3, testBlock4));

        Section testSection1 = new Section("LEC0101", "Mario", testBlockList1);
        Section testSection2 = new Section("LEC0101", "Francois", testBlockList2);
        Section testSection3 = new Section("TUT0101", "Badr", testBlockList3);

        // Initialise test courses
        TimetableCourse marioCourse = new TimetableCourse("C1", Arrays.asList(testSection1, testSection3),
                "S", "CSC258", "1");

        TimetableCourse francoisCourse = new TimetableCourse("C2", List.of(testSection2),
                "S", "CSC236", "1");

        TimetableCourse intersectCourse = new TimetableCourse("C2", List.of(testSection3, testSection2),
                "S", "CSC200", "1");
    }

    @Test
    /** Test that the Use case correctly returns the sole given timetable if we look for the best overlap
     * with a timetable and itself.
     */
    public void testMatchVacuous(){

        ArrayList<Timetable> testTimetableList = new ArrayList<Timetable>();
        Timetable soleTimetable = new Timetable(new ArrayList<>(Arrays.asList(marioCourse)));
        testTimetableList.add(soleTimetable);

        // Make the input dialog.
        OverlapInputDialog dialog = new OverlapInputDialog(testTimetableList);
        dialog.subscribe(testListener);
        dialog.pack();
        dialog.setVisible(true);
        assert testListener.result == soleTimetable;

        System.exit(0);


    }
}
