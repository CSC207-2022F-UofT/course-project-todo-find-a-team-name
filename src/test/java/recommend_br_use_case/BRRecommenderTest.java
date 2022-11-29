package recommend_br_use_case;

import entities.*;
import fileio_use_case.SessionGatewayInteractor;
import fileio_use_case.SessionStorerInteractor;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import recommend_br_use_case.application_business.BRRecommender;
import recommend_br_use_case.application_business.TargetTimeCourseComparator;
import screens.feature_6_frameworks_drivers.SessionGateway;

import java.io.IOException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class BRRecommenderTest {

    @Test
    void testRecommendBr() throws IOException, ParseException {
        // Load session from test file
        SessionGatewayInteractor convertFile = new SessionGatewayInteractor("src/main/resources/test_session_data.json");
        String jsonToStr = convertFile.fileToString();
        HashMap<String, CalendarCourse> result = convertFile.readFromFile(jsonToStr);
        SessionStorerInteractor allSessions = convertFile.creatingSessionsFromFile(result);
        Session session = allSessions.getSession("F");

        // Construct timetable course
        ArrayList<TimetableCourse> courses = new ArrayList<>();
        List<TimetableCourse> expected = new ArrayList<>();

        CalendarCourse calCSC207 = session.getCalendarCourse("CSC207H1");
        CalendarCourse calCSC236 = session.getCalendarCourse("CSC207H1");
        CalendarCourse calSTA247 = session.getCalendarCourse("STA247H1");
        CalendarCourse calSLA106 = session.getCalendarCourse("SLA106H1");
        CalendarCourse calENG220 = session.getCalendarCourse("ENG220H1");
        CalendarCourse calESS205 = session.getCalendarCourse("ESS205H1");
        CalendarCourse calBIO255 = session.getCalendarCourse("BIO255H1");


        try {
            courses.add(createTimetableCourse(calCSC207, "LEC-0401", "TUT-0301", ""));
            courses.add(createTimetableCourse(calCSC236, "LEC-0101", "TUT-0103", ""));
            courses.add(createTimetableCourse(calSTA247, "LEC-0201", "TUT-0202", ""));

            expected.add(createTimetableCourse(calSLA106, "LEC-5101", "", ""));
            expected.add(createTimetableCourse(calENG220, "LEC-0101", "", ""));
            expected.add(createTimetableCourse(calESS205, "LEC-5101", "", ""));
            expected.add(createTimetableCourse(calBIO255, "LEC-0101", "", "PRA-0101"));
            expected.add(createTimetableCourse(calBIO255, "LEC-5101", "", "PRA-0101"));

        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        Timetable timetable = new Timetable(courses, "F");
        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("1");
        brCategoriesSelected.add("4");
        TargetTimeCourseComparator courseComparator = new TargetTimeCourseComparator(0);
        expected.sort(courseComparator);

        BRRecommender brRecommender = new BRRecommender(timetable, session, brCategoriesSelected, courseComparator);

        System.out.println(timetable.isConflicted(calBIO255.getSections().get(1)));
        System.out.println(calBIO255.getSections().get(1));
        assertEquals(expected, brRecommender.recommendBr());

    }

    private TimetableCourse createTimetableCourse(CalendarCourse calCourse, String lecture, String tutorial,
                                                  String practical) throws InvalidSectionsException {
        HashSet<String> sectionCodes = new HashSet<>();
        if (!lecture.isEmpty())
            sectionCodes.add(lecture);
        if (!tutorial.isEmpty())
            sectionCodes.add(tutorial);
        if (!practical.isEmpty())
            sectionCodes.add(practical);

        List<Section> sections = new ArrayList<>();
        for (Section section : calCourse.getSections()){
            if (sectionCodes.contains(section.getCode())){
                sections.add(section);
            }
        }
        return new TimetableCourse(calCourse.getTitle(), sections, calCourse.getCourseSession(),
                calCourse.getCourseCode(), calCourse.getBreadth());
    }

}