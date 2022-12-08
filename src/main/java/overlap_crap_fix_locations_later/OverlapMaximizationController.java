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

    @Override
    public TimetableModel getBestMatchingTimetable(OverlapTimetableViewModel mainTable,
                                                   List<OverlapTimetableViewModel> timetables) {

        TimetableModel actualMainTable = OverlapTimetableViewModelToModelConverter
                .convertOverlapTimetableViewModelToModel(mainTable);

        ArrayList<TimetableModel> actualTimetables = new ArrayList<>();
        for (OverlapTimetableViewModel timetableViewModel : timetables) {
            actualTimetables.add(OverlapTimetableViewModelToModelConverter.convertOverlapTimetableViewModelToModel(
                    timetableViewModel
            ));
        }

        return timetableMatcher.determineBestMatchingTimetable(actualMainTable, actualTimetables);
    }
}
