package overlap_crap_fix_locations_later;

// TODO: Assuming timeTable is a list of timetableCourses. Note that the current code is kind of a standIn.

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;

/** A helper interactor responsible for calculating the # of hours in a section.
 * FIXME: Not sure if this deserves its own interactor, but I feel like if we ever changed how Section time is
 *  calculated (idk, maybe we start incorporating UofT time into the formal start), that might be a reason below... that is not
 *  the same as how we find the overlapping timeTable in principle. */
class TimeTableMatchCalculateSectionHoursInteractor {

    public Double calculateHoursOfSection(Section section){
        double hoursAccumulator = 0.0;
        for (Block block : section.getBlocks()){
            hoursAccumulator += block.getEndTime() - block.getStartTime();
        }
        return hoursAccumulator;
    }
}

public class TimeTableMatchInteractor {
    /** A use case interactor responsible for finding the best overlapping TimeTable for one 'main' TimeTable, from
     * a list of them.
     * We seek to maximize 'overlap'. Overlap is defined in terms of:
     * - Overlapping hours in the same section (to be with friends :3)
     * - Possibly HARD killing all constraints OR
     * - Possibly trying to prioritize ones where more hours maximize more constraints. LATER ON.
     * (Weight can simply be determined by time * (proportion of satisfied constraints).
     */
    private final ArrayList<Timetable> timetables;
    private final Timetable mainTable;

    private final ArrayList<Constraint> constraints;
    private final Boolean lightConstraints;

    TimeTableMatchInteractor(ArrayList<Timetable> timetables, Timetable mainTable, Boolean lightConstraints,
    ArrayList<Constraint> constraints){
        this.timetables = timetables;
        this.mainTable = mainTable;
        this.lightConstraints = lightConstraints;
        this.constraints = constraints;
    }

    /** Return the overlap value of a timeTable with the main one. */
    private Double calculateTimetableOverlap(Timetable candidate){
        // ArrayList<TimetableCourse> mainCourses = mainTable.getCourseList();
        ArrayList<TimetableCourse> mainCourses = new ArrayList<>();
        // ArrayList<TimetableCourse> candidateCourses = candidate.getCourseList();
        ArrayList<TimetableCourse> candidateCourses = new ArrayList<>();

        Double totalOverlapWeightedHrs = 0.0;
        // Although this is terribly inefficient, note that we have at most, like... 10 courses. Of 10 sections each.
        // Section's don't store a reference to their parent course, so this is actually necessary D:
        // Compare every section of 2 courses...
        for (TimetableCourse mainCourse : mainCourses){
            for (TimetableCourse candidateCourse : candidateCourses){
                for (Section mainSection: mainCourse.getSections()){
                    for (Section candidateSection: candidateCourse.getSections()){
                        if (mainSection == candidateSection){
                            // If they're in the same section in the same course, the two people can go together :).
                            // Hey, what if we used a companion object to not have to make a new one each time? Or just made this into an object.
                            Double thisOverlapWeightedHrs =
                                    new TimeTableMatchCalculateSectionHoursInteractor().calculateHoursOfSection(candidateSection);
                            totalOverlapWeightedHrs += thisOverlapWeightedHrs;
                            // TODO: Add in functionality for allowing 'soft constraint' overlap.
                            //  Later on this would probably involve calling Hans' part with 0 constraints and then
                            //  considering entered ones later.
                        }
                    }
                }
            }
        }
        return totalOverlapWeightedHrs;
    }

    /** Return a new HashMap connecting Timetables to their Overlap value.
     */
    public HashMap<Timetable, Double> calculateTimetableOverlaps(){
        HashMap<Timetable, Double> timetableOverlapMap = new HashMap<>();
        for (Timetable timetable : timetables){
            timetableOverlapMap.put(timetable, calculateTimetableOverlap(timetable));
        }
        return timetableOverlapMap;
    }

    /** Return ONLY the best overlapping timetable with the main one. Order is arbitrary if there is a tie. For multiple
     * outputs, use calculateTimetableOverlaps and just take the first few. **/
    public Timetable determineBestMatchingTimetable(){
        Timetable bestTimetable = null;
        Double bestScore = -1.0;
        for (Timetable timetable : timetables){
            Double calculatedOverlap = calculateTimetableOverlap(timetable);
            if (calculatedOverlap > bestScore){
                bestTimetable = timetable;
                bestScore = calculateTimetableOverlap(timetable);
            }
        }
        return bestTimetable;
    }



}
