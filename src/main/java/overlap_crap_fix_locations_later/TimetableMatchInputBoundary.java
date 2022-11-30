package overlap_crap_fix_locations_later;

import retrieve_timetable_use_case.TimetableModel;

import java.util.HashMap;

public interface TimetableMatchInputBoundary {

    TimetableModel determineBestMatchingTimetable();

    HashMap<TimetableModel, Double> calculateTimetableOverlaps();
}
