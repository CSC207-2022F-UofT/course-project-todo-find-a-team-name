package overlap_crap_fix_locations_later.interface_adapters;

import display_timetable_use_case.interface_adapters.TimetableViewModel;

import java.util.List;

/**
 * An input barrier allowing a dialog to receive TimetableViewModels from a presenter, stashing them somewhere
 * in an attribute or something.
 */

public interface OverlapInputView {

    /**
     * Stash a list of OverlapTimetableViewModels somewhere in this Dialog to be used later.
     **/
    public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels);

    /**
     * Stash the bestMatchingTimetable in this Dialog to initialize with Kai's thing :).
     **/
    public void stashBestMatchingTimetable(TimetableViewModel timetableViewModel);

}
