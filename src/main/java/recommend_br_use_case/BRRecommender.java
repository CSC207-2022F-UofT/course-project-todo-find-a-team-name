package recommend_br_use_case;

import entities.*;

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
    private final Session fSession;
    private final Session sSession;
    private final Session ySession;
    private final HashSet<String> brCategoriesSelected;
    private final Comparator<Course> timetableCourseComparator;

    /**
     * Construct BRRecommender given timetable, sessions, breadth categories, and comparator
     *
     * @param timetable timetable entity used to check time conflict
     * @param fSession session for fall
     * @param sSession session for winter
     * @param ySession session for full year
     * @param brCategoriesSelected breadth categories
     * @param timetableCourseComparator comparator used to sort the result of recommendation
     */
    public BRRecommender(Timetable timetable, Session fSession, Session sSession, Session ySession,
                         HashSet<String> brCategoriesSelected, Comparator<Course> timetableCourseComparator){
        this.timetable = timetable;
        this.fSession = fSession;
        this.sSession = sSession;
        this.ySession = ySession;
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
        List<CalendarCourse> allCalenderCourses = new ArrayList<>();
        allCalenderCourses.addAll(fSession.allCoursesSession());
        allCalenderCourses.addAll(sSession.allCoursesSession());
        allCalenderCourses.addAll(ySession.allCoursesSession());

        List<TimetableCourse> result = new ArrayList<>();
        for (CalendarCourse course : allCalenderCourses) {
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
                result.addAll(createAllPossibleTimetableCourse(course, lectures, tutorials, practicals));

            }
        }

        if (timetableCourseComparator != null)
            result.sort(timetableCourseComparator);

        return result;
    }

    /**
     * Returns list of all possible TimetableCourse corresponding to the given course
     * that can be generated from the given lectures, tutorials, and practicals
     *
     * @param course calendar course corresponding to all TimetableCourse generated
     * @param lectures all lectures in course
     * @param tutorials all tutorials in course
     * @param practicals all practicals in course
     * @return list of all possible TimetableCourse corresponding to the given course
     * that can be generated from the given lectures, tutorials, and practicals
     */
    private static List<TimetableCourse> createAllPossibleTimetableCourse(CalendarCourse course, List<Section> lectures,
                                                                               List<Section> tutorials,
                                                                               List<Section> practicals){
        List<List<Section>> sections = new ArrayList<>();
        sections.add(lectures);
        sections.add(tutorials);
        sections.add(practicals);
        try {
            ArrayList<TimetableCourse> temp = new ArrayList<>();
            createAllPossibleTimetableCourse(sections, temp, 0, new TimetableCourse(course.getTitle(),
                    new ArrayList<>(), course.getCourseSession(),
                    course.getCourseCode(), course.getBreadth()));
            return temp;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * TODO: document this code
     *
     * @param sections
     * @param result
     * @param depth
     * @param curr
     */
    private static void createAllPossibleTimetableCourse(List<List<Section>> sections,
                                                         List<TimetableCourse> result,
                                                         int depth, TimetableCourse curr){
        if (depth == sections.size()){
            result.add(curr);
            return;
        }

        if (sections.get(depth).size() == 0)
            createAllPossibleTimetableCourse(sections, result, depth + 1, curr);

        for (Section section : sections.get(depth)) {
            TimetableCourse course;
            try {
                course = new TimetableCourse(curr.getTitle(), curr.getSections(), curr.getCourseSession(),
                        curr.getCourseCode(), curr.getBreadth());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            course.setSection(section);
            createAllPossibleTimetableCourse(sections, result, depth + 1, course);
        }
    }
}
