package overlap_crap_fix_locations_later.InputBoundaries;

import retrieve_timetable_use_case.TimetableModel;

import java.util.HashMap;
import java.util.List;

public interface TimetableMatchInputBoundary {

    TimetableModel determineBestMatchingTimetable(TimetableModel mainTable, List<TimetableModel> timetables);

    HashMap<TimetableModel, Double> calculateTimetableOverlaps(TimetableModel mainTable, List<TimetableModel> timetables);
}
