package overlap_crap_fix_locations_later.InputBoundaries;

import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;

import java.util.List;

/**
 * An input barrier allowing a dialog to receive TimetableViewModels from a presenter, stashing them somewhere
 * in an attribute or something.
 */

public interface OverlapInputEntryViewModel {

    /**
     * Stash a list of OverlapTimetableViewModels somewhere in this Dialog to be used later.
     **/
    public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels);

    /**
     * Stash the bestMatchingTimetable in this Dialog to initialize with Kai's thing :).
     **/
    public void stashBestMatchingTimetable(TimetableViewModel timetableViewModel);

}
