package overlap_crap_fix_locations_later.InputBoundaries;

import entities.Timetable;
import overlap_crap_fix_locations_later.OverlapInputDialogDataKeys;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * An input boundary representing a generic Controller for the Overlap Maximization use case.
 **/
public interface OverlapMaxInputBoundary extends Flow.Subscriber<HashMap<OverlapInputDialogDataKeys, Object>> {
    public TimetableModel getBestMatchingTimetable(Timetable mainTable, List<Timetable> timetables);

    @Override
    void onSubscribe(Flow.Subscription subscription);

    @Override
    void onNext(HashMap<OverlapInputDialogDataKeys, Object> item);

    @Override
    void onError(Throwable throwable);

    @Override
    void onComplete();
}
