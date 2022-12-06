package recommend_br_use_case.application_business;


import entities.Course;

import java.util.Comparator;

/**
 * Interface used to create Comparator for Timetable
 */
public interface CourseComparatorFactory {
    /**
     * Create and return course comparator based on the given preferred time
     *
     * @param preferredTime String representation of preferred time
     * @return course comparator generated from the given preferred time
     */
    Comparator<Course> createComparator(String preferredTime);
}
