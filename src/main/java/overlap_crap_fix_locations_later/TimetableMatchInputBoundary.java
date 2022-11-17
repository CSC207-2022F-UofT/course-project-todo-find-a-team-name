package overlap_crap_fix_locations_later;

import entities.Timetable;

import java.util.HashMap;

public interface TimetableMatchInputBoundary {

    public Timetable determineBestMatchingTimetable();

    public HashMap<Timetable, Double> calculateTimetableOverlaps();

}
