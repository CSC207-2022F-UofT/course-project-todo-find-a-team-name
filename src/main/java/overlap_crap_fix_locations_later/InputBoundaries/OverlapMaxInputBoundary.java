package overlap_crap_fix_locations_later.InputBoundaries;

import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

/**
 * An input boundary representing a generic Controller for the Overlap Maximization use case.
 **/
public interface OverlapMaxInputBoundary {
    /**
     * Prompt the UCIs to calculate the best matching timeTable. Convert the arguments so the UCIs understand the damn
     * thing.
     * <p>
     * Note: DO NOT RELY ON THE RETURNED VALUE OUTSIDE OF A TEST. IT'LL EVENTUALLY PASS STUFF TO A PRESENTER.
     * THE RETURN VALUE IS FOR TESTING.
     *
     * @param mainTable  - representing the timetable to match against.
     * @param timetables - representing candidate timetables given to us by the generator.
     */
    public TimetableModel getBestMatchingTimetable(OverlapTimetableViewModel mainTable,
                                                   List<OverlapTimetableViewModel> timetables);
}
