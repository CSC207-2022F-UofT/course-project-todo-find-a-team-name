package recommend_br_use_case;

import entities.Block;
import entities.Course;
import entities.Section;

import java.util.Comparator;

/**
 * Class used to sort courses by its distance between average time and the specified target time
 */
public class TargetTimeCourseComparator implements Comparator<Course> {

    private final double targetTime;

    /**
     * Constructs TargetTimeCourseComparator given target time
     *
     * @param targetTime target time from 0 to 24 inclusive used to compute distance to average time of the course
     */
    public TargetTimeCourseComparator(double targetTime){
        this.targetTime = targetTime;
    }

    /**
     * Compares its two arguments for order.
     * Returns a negative integer, zero, or a positive integer as the distance between average start time of c1
     * and targetTime is less than, equal to, or greater than the distance between average start time of c2 and
     * targetTime.
     *
     * @param c1 the first course to be compared.
     * @param c2 the second course to be compared.
     * @return a negative integer, zero, or a positive integer as the distance between average start time of c1
     * and targetTime is less than, equal to, or greater than the distance between average start time of c2 and
     * targetTime.
     */
    @Override
    public int compare(Course c1, Course c2) {
        return Double.compare(Math.abs(getCourseAverageStartTime(c1) - targetTime),
                Math.abs(getCourseAverageStartTime(c2) - targetTime));
    }

    /**
     * Returns average start time of all blocks contained in the given course
     *
     * @param course course used to compute average start time
     * @return average start time of all blocks contained in the given course
     */
    private double getCourseAverageStartTime(Course course){
        double result = 0;
        int num = 0;
        for (Section section : course.getSections()){
            for (Block block : section.getBlocks()) {
                result += block.getStartTime();
                num += 1;
            }
        }
        return result / num;
    }

}
