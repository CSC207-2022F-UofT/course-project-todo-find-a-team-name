package recommend_br_use_case.application_business;

import entities.Course;

import java.util.Comparator;

/**
 * Factory for TargetTimeCourseComparator
 */
public class TargetTimeCourseComparatorFactory implements CourseComparatorFactory{

    public static final double BALANCED_TARGET_TIME = 14;

    /**
     * Generate and returns TargetTimeCourseComparator based on the given preferred time.
     * If preferred time is "early" return TargetTimeCourseComparator with target time set to 0.
     * If preferred time is "balanced" return TargetTimeCourseComparator with target time set to BALANCED_TARGET_TIME.
     * If preferred time is "late" return TargetTimeCourseComparator with target time set to 24.
     * Otherwise, return null.
     *
     * @param preferredTime String representation of preferred time
     * @return TargetTimeCourseComparator based on the given preferred time
     */
    @Override
    public Comparator<Course> createComparator(String preferredTime) {
        Comparator<Course> courseComparator;
        switch (preferredTime){
            case "early":
                courseComparator = new TargetTimeCourseComparator(0);
                break;
            case "balanced":
                courseComparator = new TargetTimeCourseComparator(BALANCED_TARGET_TIME);
                break;
            case "late":
                courseComparator = new TargetTimeCourseComparator(24);
                break;
            default:
                courseComparator = null;
        }
        return courseComparator;
    }
}
