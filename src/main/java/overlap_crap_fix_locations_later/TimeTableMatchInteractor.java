package overlap_crap_fix_locations_later;

// TODO: Assuming timeTable is a list of timetableCourses. Note that the current code is kind of a standIn.

import overlap_crap_fix_locations_later.InputBoundaries.SectionHoursInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import retrieve_timetable_use_case.BlockModel;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.SectionModel;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class TimeTableMatchInteractor implements TimetableMatchInputBoundary, Flow.Subscriber<HashMap<OverlapInputDialogDataKeys, Object>> {
    /**
     * A use case interactor responsible for finding the best overlapping TimeTable for one 'main' TimeTable, from
     * a list of them.
     * We seek to maximize 'overlap'. Overlap is defined in terms of:
     * - Overlapping hours in the same section (to be with friends :3)
     * - Possibly HARD killing all constraints OR
     * - Possibly trying to prioritize ones where more hours maximize more constraints. LATER ON.
     * (Weight can simply be determined by time * (proportion of satisfied constraints).
     */
    private ArrayList<TimetableModel> timetables;
    private TimetableModel mainTable;

    private final SectionHoursInputBoundary sectionHoursCalculator;

    TimeTableMatchInteractor(SectionHoursInputBoundary sectionHoursCalculator) {
        // Initialise what we know at compile-time. Timetables and the mainTable must be passed in later.
        this.timetables = null;
        this.mainTable = null;
        this.sectionHoursCalculator = sectionHoursCalculator;
    }

    /**
     * Return the overlap value of a timeTable with the main one.
     */
    private Double calculateTimetableOverlap(TimetableModel candidate) {
        List<CourseModel> mainCourses = mainTable.getCourses();
        List<CourseModel> candidateCourses = candidate.getCourses();

        Double totalOverlapWeightedHrs = 0.0;
        // Although this is terribly inefficient, note that we have at most, like... 10 courses. Of 10 sections each.
        // Section's don't store a reference to their parent course, so this is actually necessary D:
        // Compare every section of 2 courses...
        for (CourseModel mainCourse  : mainCourses){
            for (CourseModel candidateCourse : candidateCourses){
                for (SectionModel mainSection: mainCourse.getSections()){
                    for (SectionModel candidateSection: candidateCourse.getSections()){
                        if (mainSection == candidateSection){
                            // If they're in the same section in the same course, the two people can go together :).
                            // Hey, what if we used a companion object to not have to make a new one each time? Or just made this into an object.

                            ArrayList<BlockModel> blockModels = new ArrayList<>();
                            Double thisOverlapWeightedHrs =
                                    sectionHoursCalculator.calculateHoursOfSection(candidateSection);

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
    public HashMap<TimetableModel, Double> calculateTimetableOverlaps(){
        HashMap<TimetableModel, Double> timetableOverlapMap = new HashMap<>();
        for (TimetableModel timetable : timetables){
            timetableOverlapMap.put(timetable, calculateTimetableOverlap(timetable));
        }
        return timetableOverlapMap;
    }

    /** Return ONLY the best overlapping timetable with the main one. Order is arbitrary if there is a tie. For multiple
     * outputs, use calculateTimetableOverlaps and just take the first few. **/
    public TimetableModel determineBestMatchingTimetable(){
        TimetableModel bestTimetable = null;
        Double bestScore = -1.0;
        for (TimetableModel timetable : timetables) {
            Double calculatedOverlap = calculateTimetableOverlap(timetable);
            if (calculatedOverlap > bestScore) {
                bestTimetable = timetable;
                bestScore = calculateTimetableOverlap(timetable);
            }
        }
        return bestTimetable;
    }


    /**
     * Set up to receive data from a subscription. Note that we expect 1 data bundle per InputDialog.
     **/
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(1);

    }

    @Override
    public void onNext(HashMap<OverlapInputDialogDataKeys, Object> items) {
        this.mainTable = (TimetableModel) items.get(OverlapInputDialogDataKeys.mainTable);
        this.timetables = (ArrayList<TimetableModel>) items.get(OverlapInputDialogDataKeys.candidateTimetables);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Hey, we got an error from the OverlapMaximization's associated publisher. Check it out.");
    }

    @Override
    public void onComplete() {
        System.out.println("Completion called!");
    }


}
