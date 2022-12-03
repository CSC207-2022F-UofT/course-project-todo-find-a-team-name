package overlap_crap_fix_locations_later.InputBoundaries;

import retrieve_timetable_use_case.TimetableModel;

/** An input boundary representing a generic Controller for the Overlap Maximization use case. **/
public interface OverlapMaxInputBoundary {
    public TimetableModel getBestMatchingTimetable();
}
