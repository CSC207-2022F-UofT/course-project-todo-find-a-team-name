package overlap_crap_fix_locations_later;

import entities.Constraint;
import entities.Timetable;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;

/** An input boundary representing a generic Controller for the Overlap Maximization use case. **/
public interface OverlapMaxInputBoundary {
    public TimetableModel getBestMatchingTimetable(ArrayList<Timetable> Timetables, Timetable mainTable, Boolean lightConstraints,
                                                   ArrayList<Constraint> constraints);
}
