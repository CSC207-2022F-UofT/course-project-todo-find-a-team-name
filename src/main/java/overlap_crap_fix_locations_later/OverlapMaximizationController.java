package overlap_crap_fix_locations_later;

import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModelToModelConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class OverlapMaximizationController implements OverlapMaxInputBoundary {

    private final TimetableMatchInputBoundary timetableMatcher;
    private TimetableModel mainTable;
    private List<TimetableModel> timetables;

    OverlapMaximizationController(TimetableMatchInputBoundary timeTableMatcher) {
        this.timetableMatcher = timeTableMatcher;
        this.mainTable = null;
        this.timetables = null;
    }

    /**
     * Prompt the use case interactor to calculate the best matching timetable.
     * Convert the weird viewModels we start out with to TimetableModels that the Interactor knows how to use.
     *
     * @param mainTable
     * @param timetables
     */
    @Override
    public void getBestMatchingTimetable(OverlapTimetableViewModel mainTable,
                                         List<OverlapTimetableViewModel> timetables) {

        // Convert the weird viewModels to actual TimetablModels for the UCI.
        TimetableModel actualMainTable = OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableViewModelToModel(mainTable);

        ArrayList<TimetableModel> actualTimetables = new ArrayList<>();
        for (OverlapTimetableViewModel timetableViewModel : timetables) {
            actualTimetables.add(OverlapTimetableViewModelToModelConverter.convertOverlapTimetableViewModelToModel(
                    timetableViewModel
            ));
        }

        // Make it work!
        timetableMatcher.determineBestMatchingTimetable(actualMainTable, actualTimetables);
    }
}
