package recommend_br_use_case;

import entities.Block;
import org.junit.jupiter.api.Test;
import recommend_br_use_case.application_business.TargetTimeCourseComparator;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TargetTimeCourseComparatorTest {

    /**
     * Test whether compare is returning negative integer when distance between target time and average
     * start time of the first argument is less than second argument.
     */
    @Test
    void testCompareLess() {
        TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(14);
    }

    /**
     * Test whether compare is returning positive integer when distance between target time and average
     * start time of the first argument is less than second argument.
     */
    @Test
    void testCompareLarger() {
        TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(10);
    }

    /**
     * Test whether compare is returning 0 when distance between target time and average
     * start time of the first argument is less than second argument.
     */
    @Test
    void testCompareEqual() {
        TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(22);
    }
}