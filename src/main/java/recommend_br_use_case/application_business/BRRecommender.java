package recommend_br_use_case.application_business;

import entities.*;
import generate_timetable_course_use_case.TimetableCourseGenerator;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;

/**
 * Class that can give sorted list of timetable courses contained in one of the given sessions,
 * with specified breadth categories that do not conflict with the given timetable.
 * The list is sorted based on the given TargetTimeCourseComparator.
 */
public class BRRecommender {

    private final Timetable timetable;
    private final Session session;
    private final HashSet<String> brCategoriesSelected;
    private final Comparator<Course> timetableCourseComparator;

    /**
     * Construct BRRecommender given timetable, sessions, breadth categories, and comparator
     *
     * @param timetable timetable entity used to check time conflict
     * @param session session for full year
     * @param brCategoriesSelected breadth categories
     * @param timetableCourseComparator comparator used to sort the result of recommendation
     */
    public BRRecommender(Timetable timetable, Session session, HashSet<String> brCategoriesSelected,
                         Comparator<Course> timetableCourseComparator){
        this.timetable = timetable;
        this.session = session;
        this.brCategoriesSelected = brCategoriesSelected;
        this.timetableCourseComparator = timetableCourseComparator;

    }

    /**
     * Returns sorted list of timetable courses contained in one of the sessions (fSession, sSession, and ySession),
     * with breadth category in brCategoriesSelected and do not conflict with the timetable.
     * The list is sorted based on the given TargetTimeCourseComparator.
     *
     * @return list of timetable courses each contained in one of fSession, sSession, or ySession,
     * with breadth category in brCategoriesSelected and do not conflict with the timetable, sorted
     * based on timetableCourseComparator
     */
    public List<TimetableCourse> recommendBr(){
        List<TimetableCourse> result = new ArrayList<>();
        for (CalendarCourse course : session.allCoursesSession()) {
            if (brCategoriesSelected.contains(course.getBreadth())) {
                List<Section> lectures = new ArrayList<>();
                List<Section> tutorials = new ArrayList<>();
                List<Section> practicals = new ArrayList<>();

                for (Section section : course.getSections()) {
                    if (!timetable.isConflicted(section)) {
                        if (section.isLecture())
                            lectures.add(section);
                        else if (section.isTutorial())
                            tutorials.add(section);
                        else if (section.isPractical())
                            practicals.add(section);
                    }
                }

                if (course.hasLecture() && lectures.isEmpty() ||
                        course.hasTutorial() && tutorials.isEmpty() ||
                        course.hasPractical() && practicals.isEmpty())
                    continue;

                result.addAll(new TimetableCourseGenerator(course).generateAllTimetableCourses(lectures, tutorials,
                        practicals));

            }
        }

        if (timetableCourseComparator != null)
            result.sort(timetableCourseComparator);

        return result;
    }

}
