package overlap_crap_fix_locations_later.application_business;

import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.SectionModel;


/**
 * A helper interactor responsible for calculating the # of hours in a section.
 * FIXME: Not sure if this deserves its own interactor, but I feel like if we ever changed how Section time is
 *  calculated (idk, maybe we start incorporating UofT time into the formal start), that might be a reason below... that is not
 *  the same as how we find the overlapping timeTable in principle.
 */
public class CalculateSectionHoursInteractor implements SectionHoursInputBoundary {

    public Double calculateHoursOfSection(SectionModel section) {
        double hoursAccumulator = 0.0;
        for (BlockModel block : section.getBlocks()) {
            hoursAccumulator += block.getEndTime() - block.getStartTime();
        }
        if (hoursAccumulator >= 24.0 || hoursAccumulator < 0.0) {
            throw new IllegalArgumentException("The program attempted to calculate the hours of a section that seems" +
                    "to have negative or greater than 24 hour length. Something may be wrong with the data.");
        }
        return hoursAccumulator;
    }
}
