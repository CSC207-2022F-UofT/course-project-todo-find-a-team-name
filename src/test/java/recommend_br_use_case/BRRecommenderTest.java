package recommend_br_use_case;

import entities.*;
import fileio_use_case.SessionGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import recommend_br_use_case.application_business.BRRecommender;
import recommend_br_use_case.application_business.TargetTimeCourseComparator;

import java.io.IOException;
import java.sql.Time;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BRRecommenderTest {

    /**
     * Test whether BRRecommender returns correct list of timetable courses, if...
     *      - the given timetable has CSC207H1, CSC247H1, and STA247 (from the test_session_data.json)
     *      - the given Session is loaded from "src/main/resources/test_session_data.json"
     *      - "1" and "4" is selected for breadth categories
     *      - new TargetTimeCourseComparator(0) is given
     */
    @Test
    void testRecommendBr(){
        // Load session from test file
        SessionGateway sessionGateway = new SessionGateway();
        Session session;
        try {
            session = sessionGateway.extractSession(
                    sessionGateway.readFromFile("src/main/resources/test_session_data.json"),
                    "F"
            );
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        // Construct timetable courses
        ArrayList<TimetableCourse> courses = new ArrayList<>();
        List<TimetableCourse> expected = new ArrayList<>();
        CalendarCourse calCSC207 = session.getCalendarCourse("CSC207H1");
        CalendarCourse calCSC236 = session.getCalendarCourse("CSC236H1");
        CalendarCourse calSTA247 = session.getCalendarCourse("STA247H1");
        CalendarCourse calSLA106 = session.getCalendarCourse("SLA106H1");
        CalendarCourse calENG220 = session.getCalendarCourse("ENG220H1");
        CalendarCourse calESS205 = session.getCalendarCourse("ESS205H1");
        CalendarCourse calBIO120 = session.getCalendarCourse("BIO120H1");

        try {
            courses.add(createTimetableCourse(calCSC207, "LEC-0401", "TUT-0301", ""));
            courses.add(createTimetableCourse(calCSC236, "LEC-0101", "TUT-0103", ""));
            courses.add(createTimetableCourse(calSTA247, "LEC-0201", "TUT-0202", ""));

            expected.add(createTimetableCourse(calENG220, "LEC-0101", "", ""));
            expected.add(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-0201"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-0202"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-0201"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-0202"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-0201"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-0202"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-5101"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-5102"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-5101"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-5102"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-5101"));
            expected.add(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-5102"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-0201"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-0202"));
            expected.add(createTimetableCourse(calSLA106, "LEC-5101", "", ""));
            expected.add(createTimetableCourse(calESS205, "LEC-5101", "", ""));
            expected.add(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-5101"));
            expected.add(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-5102"));

        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        Timetable timetable = new Timetable(courses, "F");
        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("1");
        brCategoriesSelected.add("4");
        TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(0);
        BRRecommender brRecommender = new BRRecommender(timetable, session, brCategoriesSelected, courseComparator);
        List<TimetableCourse> result = brRecommender.recommendBr();
        assertEquals(expected, result);
    }

    /**
     * Create timetable course from the given calendar course and lecture code, tutorial code, and practical code.
     * The returned timetable course will have same title, course session, course code, and breadth category as the
     * given calendar course. However, it will only contain sections corresponding to the given lecture code, tutorial
     * code, and practical code.
     *
     * @param calCourse calendar course
     * @param lectureCode lecture code
     * @param tutorialCode tutorial code
     * @param practicalCode practical code
     * @return timetable course generated from the given calendar course and lecture, tutorial, and practical code.
     * @throws InvalidSectionsException when section codes are invalid (e.g. two tutorials)
     */
    private TimetableCourse createTimetableCourse(CalendarCourse calCourse, String lectureCode, String tutorialCode,
                                                  String practicalCode) throws InvalidSectionsException {
        List<Section> sections = new ArrayList<>();
        for (Section section : calCourse.getSections()){
            if (section.getCode().equals(lectureCode) || section.getCode().equals(tutorialCode)
                    || section.getCode().equals(practicalCode)){
                sections.add(section);
            }
        }
        return new TimetableCourse(calCourse.getTitle(), sections, calCourse.getCourseSession(),
                calCourse.getCourseCode(), calCourse.getBreadth());
    }

}