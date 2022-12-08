package overlap_crap_fix_locations_later.InputBoundaries;

import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.List;

/**
 * An input boundary representing a generic Controller for the Overlap Maximization use case.
 **/
public interface OverlapMaxInputBoundary {
    public TimetableModel getBestMatchingTimetable(OverlapTimetableViewModel mainTable,
                                                   List<OverlapTimetableViewModel> timetables);
}
