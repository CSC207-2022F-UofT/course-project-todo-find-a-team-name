package generate_overlapping_timetable_use_case.application_business;


import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.HashMap;
import java.util.List;

/**
 * An input boundary that describes an interactor that implements methods handling the computation of the
 * maximally overlapping Timetables. (Specifically, determining how much timetables overlap with the main target
 * timetable, and determining the best overlapping timetable). Note that best != "good", if all of our options
 * are badly overlapping.
 */
public interface TimetableMatchInputBoundary {

    /**
     * Determines the bestMatchingTimetable.
     * Don't depend on the return value! The output of this will make its way up the food chain back into the
     * InputDialog in the proper clean architecture manner. The return value is included to make tests less flaky
     * and dependent on fewer things (and easier to write).
     *
     * @param mainTable  - the target timetable to match against.
     * @param timetables - options we have to match with the target.
     * @return - the best matching TimetableModel (for unit tests only!).
     */
    TimetableModel determineBestMatchingTimetable(TimetableModel mainTable, List<TimetableModel> timetables);

    /**
     * This method is currently unused by everything except unit tests, but is in here to support extension of
     * future features that might generate the best [X] overlapping timetables. Determines the timetableOverlaps
     * of every timetable in the candidate list, so that they can be judged later.
     * <p>
     * DON'T DEPEND ON THE RETURN VALUE. THAT'S TO MAKE TESTS EASIER.
     * <p>
     * Does NOT pass data to the OverlapInputDialog since, well, I don't use it anywhere yet.
     *
     * @param mainTable  - the target timetable to match against.
     * @param timetables - options we have to match with the target.
     * @return - a mapping of TimetableModels to their overlapHours score as a Double.
     */
    HashMap<TimetableModel, Double> calculateTimetableOverlaps(TimetableModel mainTable, List<TimetableModel> timetables);
}
