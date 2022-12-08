package generate_overlapping_timetable_use_case.interface_adapters;

import generate_overlapping_timetable_use_case.application_business.TimetableMatchInputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class OverlapMaximizationController {

    private final TimetableMatchInputBoundary timetableMatcher;

    public OverlapMaximizationController(TimetableMatchInputBoundary timeTableMatcher) {
        this.timetableMatcher = timeTableMatcher;
    }

    /**
     * Prompt the use case interactor to calculate the best matching timetable.
     * Convert the weird viewModels we start out with to TimetableModels that the Interactor knows how to use.
     * <p>
     * Return value should NOT be used in regular code. It is ONLY for convenience in testing.
     *
     * @param mainTable  - the target timetable to match against
     * @param timetables - candidates to match with main table.
     */
    public TimetableModel getBestMatchingTimetable(OverlapTimetableViewModel mainTable,
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
        return timetableMatcher.determineBestMatchingTimetable(actualMainTable, actualTimetables);
    }
}
