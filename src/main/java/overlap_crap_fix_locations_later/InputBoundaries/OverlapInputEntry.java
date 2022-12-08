package overlap_crap_fix_locations_later.InputBoundaries;

import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;

import java.util.List;

/**
 * An input allowing a dialog to receive TimetableViewModels from a presenter, stashing them somewhere
 * in an attribute or something.
 */

public interface OverlapInputEntry {

    /**
     * Stash a list of OverlapTimetableViewModels somewhere in this Dialog to be used later.
     **/
    public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels);

}
