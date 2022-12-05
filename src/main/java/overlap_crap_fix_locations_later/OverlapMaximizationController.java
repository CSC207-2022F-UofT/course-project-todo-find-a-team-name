package overlap_crap_fix_locations_later;

import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

public class OverlapMaximizationController implements OverlapMaxInputBoundary {

    private final TimetableMatchInputBoundary timetableMatcher;

    OverlapMaximizationController(TimetableMatchInputBoundary timeTableMatcher) {
        this.timetableMatcher = timeTableMatcher;
    }

    public TimetableModel getBestMatchingTimetable() {
        return timetableMatcher.determineBestMatchingTimetable();
    }

    // TODO: For the moment, this is a string for testing. Change it later.
    public String selectedTimetable;
}
