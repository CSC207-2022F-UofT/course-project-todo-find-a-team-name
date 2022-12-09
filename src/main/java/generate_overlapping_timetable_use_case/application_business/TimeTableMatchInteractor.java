package generate_overlapping_timetable_use_case.application_business;

// TODO: Assuming timeTable is a list of timetableCourses. Note that the current code is kind of a standIn.

import entities.Timetable;
import generate_overlapping_timetable_use_case.interface_adapters.TimetableModelUnpacker;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class TimeTableMatchInteractor implements TimetableMatchInputBoundary, Flow.Publisher<Object> {
    /**
     * A use case interactor responsible for finding the best overlapping TimeTable for one 'main' TimeTable, from
     * a list of them.
     * We seek to maximize 'overlap'. Overlap is defined in terms of:
     * - Overlapping hours in the same section (to be with friends :3)
     * - Possibly HARD killing all constraints OR
     * - Possibly trying to prioritize ones where more hours maximize more constraints. LATER ON.
     * (Weight can simply be determined by time * (proportion of satisfied constraints).
     * <p>
     * This interactor also implements Flow.Publisher so that the best matching timetable can be sent off to the
     * interactors of other use cases, for use as they see fit.
     */

    private final OverlapOutputBoundary presenter;
    private final SectionHoursInputBoundary sectionHoursCalculator;

    private final ArrayList<Flow.Subscriber<Object>> subscribers = new ArrayList<>();

    public TimeTableMatchInteractor(SectionHoursInputBoundary sectionHoursCalculator, OverlapOutputBoundary presenter) {
        // Initialise what we know at compile-time. Timetables and the mainTable must be passed in later.
        this.sectionHoursCalculator = sectionHoursCalculator;
        this.presenter = presenter;
    }

    /**
     * Return the overlap value of a timeTable with the main one.
     * TODO: Extract a method since this is very deeply nested.
     */
    private Double calculateTimetableOverlap(TimetableModel mainTable, TimetableModel candidate) {
        List<CourseModel> mainCourses = mainTable.getCourses();
        List<CourseModel> candidateCourses = candidate.getCourses();

        Double totalOverlapWeightedHrs = 0.0;
        // Although this is terribly inefficient, note that we have at most, like... 10 courses. Of 10 sections each.
        // Section's don't store a reference to their parent course, so this is actually necessary D:
        // Compare every section of 2 courses...
        for (CourseModel mainCourse : mainCourses) {
            for (CourseModel candidateCourse : candidateCourses) {
                for (SectionModel mainSection : mainCourse.getSections()) {
                    for (SectionModel candidateSection: candidateCourse.getSections()){
                        if (mainSection == candidateSection){
                            // If they're in the same section in the same course, the two people can go together :).
                            // Hey, what if we used a companion object to not have to make a new one each time? Or just made this into an object.

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

    /**
     * Return a new HashMap connecting Timetables to their Overlap value.
     */
    public HashMap<TimetableModel, Double> calculateTimetableOverlaps(TimetableModel mainTimetable,
                                                                      List<TimetableModel> timetables) {
        HashMap<TimetableModel, Double> timetableOverlapMap = new HashMap<>();
        for (TimetableModel timetable : timetables) {
            timetableOverlapMap.put(timetable, calculateTimetableOverlap(mainTimetable, timetable));
        }
        return timetableOverlapMap;
    }

    /**
     * Push ONLY the best overlapping timetable with the main one to the presenter.
     * Order is arbitrary if there is a tie. For multiple
     * outputs, use calculateTimetableOverlaps and just take the first few if you want.
     * Send the best overlapping timetable to the subscribers too.
     * Note: the return value is intended to be used for testing convenience ONLY.
     * Don't actually rely on this to return stuff, dummy!!!
     **/
    public TimetableModel determineBestMatchingTimetable(TimetableModel mainTimetable,
                                                         List<TimetableModel> timetables) {
        TimetableModel bestTimetable = null;
        Double bestScore = -1.0;
        for (TimetableModel timetable : timetables) {
            Double calculatedOverlap = calculateTimetableOverlap(mainTimetable, timetable);
            if (calculatedOverlap > bestScore) {
                bestTimetable = timetable;
                bestScore = calculateTimetableOverlap(mainTimetable, timetable);
            }
        }
        // This is basically a loop invariant, we're safe.
        assert bestTimetable != null;

        presenter.passBestTimetable(bestTimetable);

        Timetable rawTimetable = TimetableModelUnpacker.unpackTimetable(bestTimetable);

        // Also send the raw best Timetable off to the subscribers.
        for (Flow.Subscriber<Object> subscriber : subscribers) {
            subscriber.onNext(rawTimetable);
        }

        return bestTimetable;
    }

    /**
     * Subscribe a subscriber to this interactor so that it can be updated with new bestMatchingTimetables
     * when this interactor is done calculating one.
     **/
    @Override
    public void subscribe(Flow.Subscriber<? super Object> subscriber) {
        subscribers.add(subscriber);
    }
}
