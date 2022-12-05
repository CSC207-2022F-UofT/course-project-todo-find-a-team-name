package overlap_crap_fix_locations_later;

import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class OverlapMaximizationController implements OverlapMaxInputBoundary {

    private final TimetableMatchInputBoundary timetableMatcher;
    private TimetableModel mainTable;
    private List<TimetableModel> timetables;

    private Flow.Publisher publisher;

    OverlapMaximizationController(TimetableMatchInputBoundary timeTableMatcher, Flow.Publisher publisher) {
        this.timetableMatcher = timeTableMatcher;
        this.publisher = publisher;
        this.mainTable = null;
        this.timetables = null;

        publisher.subscribe(this);
    }

    public TimetableModel getBestMatchingTimetable() {
        return timetableMatcher.determineBestMatchingTimetable(mainTable, timetables);
    }

    // TODO: For the moment, this is a string for testing. Change it later.
    public String selectedTimetable;

    /**
     * Set up to receive data from a subscription. Note that we expect 1 data bundle per InputDialog.
     **/
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(1);

    }

    @Override
    public void onNext(HashMap<OverlapInputDialogDataKeys, Object> items) {
        this.mainTable = (TimetableModel) items.get(OverlapInputDialogDataKeys.mainTable);
        this.timetables = (ArrayList<TimetableModel>) items.get(OverlapInputDialogDataKeys.candidateTimetables);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Hey, we got an error from the OverlapMaximization's associated publisher. Check it out.");
    }

    @Override
    public void onComplete() {
        System.out.println("Completion called!");
    }

}
