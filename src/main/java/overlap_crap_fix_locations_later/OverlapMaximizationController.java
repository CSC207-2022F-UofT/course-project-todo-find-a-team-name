package overlap_crap_fix_locations_later;

import java.util.ArrayList;
import java.util.concurrent.Flow;

public class OverlapMaximizationController implements Flow.Subscriber {

    public void performUseCase(){
        ArrayList<String> testItems = new ArrayList<>();

    }

    public static void main(String[] args) {
        OverlapInputDialog dialog = new OverlapInputDialog(new String[]{"A", "B", "C"});
        dialog.subscribe(new OverlapMaximizationController());
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

    // TODO: For the moment, this is a string for testing. Change it later.
    public String selectedTimetable;
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(1);

    }

    @Override
    public void onNext(Object item) {
        selectedTimetable = (String) item;
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Hey, we got an error from the OverlapMaximization's associated publisher. Check it out.");
    }

    @Override
    /** Runs the rest of the use case once we actually have the data. This will end up being called from the
     * ScheduleSelectInterface interface that we're calling from.
     */
    public void onComplete() {
        System.out.println(selectedTimetable);
        // Do nothing. I mean, we only needed one event.
    }
}
