package recommend_br_use_case.application_business;

import entities.*;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;
import org.junit.jupiter.api.Test;
import retrieve_timetable_use_case.application_business.CourseModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static retrieve_timetable_use_case.application_business.EntityConverter.generateCourseResponse;

/**
 * Test class used for testing RecommendBRInteractor
 */
class RecommendBRInteractorTest {


    /**
     * Check whether recommendBr passes correct response model to RecommendBROutputBoundary.prepareSuccessView
     * when session and timetable is loaded properly and there are some courses in session that matches the input
     */
    @Test
    void testRecommendBrSuccess() {
        SessionGateway sessionGateway = new SessionGateway();
        Session session;
        try {
            session = sessionGateway.readFromFile("src/main/resources/test_session_data.json", "F");
        } catch (ParseException | IOException | InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        ArrayList<TimetableCourse> courses = new ArrayList<>();
        CalendarCourse calCSC207 = session.getCalendarCourse("CSC207H1");
        CalendarCourse calCSC236 = session.getCalendarCourse("CSC236H1");
        CalendarCourse calSTA247 = session.getCalendarCourse("STA247H1");
        CalendarCourse calSLA106 = session.getCalendarCourse("SLA106H1");
        CalendarCourse calENG220 = session.getCalendarCourse("ENG220H1");
        CalendarCourse calESS205 = session.getCalendarCourse("ESS205H1");
        CalendarCourse calBIO120 = session.getCalendarCourse("BIO120H1");

        List<CourseModel> courseModels = new ArrayList<>();
        try {
            courses.add(createTimetableCourse(calCSC207, "LEC-0401", "TUT-0301", ""));
            courses.add(createTimetableCourse(calCSC236, "LEC-0101", "TUT-0103", ""));
            courses.add(createTimetableCourse(calSTA247, "LEC-0201", "TUT-0202", ""));

            courseModels.add(generateCourseResponse(createTimetableCourse(calENG220, "LEC-0101", "", "")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-0201")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-0202")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-0201")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-0202")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-0201")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-0202")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-5101")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0101", "", "PRA-5102")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-5101")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2000", "", "PRA-5102")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-5101")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-2002", "", "PRA-5102")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-0201")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-0202")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calSLA106, "LEC-5101", "", "")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calESS205, "LEC-5101", "", "")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-5101")));
            courseModels.add(generateCourseResponse(createTimetableCourse(calBIO120, "LEC-0201", "", "PRA-5102")));

        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        RecommendBRResponseModel expected = new RecommendBRResponseModel(courseModels);

        Timetable timetable = new Timetable(courses, "F");
        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("1");
        brCategoriesSelected.add("4");

        RecommendBROutputBoundary dummyPresenter = new RecommendBROutputBoundary() {
            @Override
            public void prepareSuccessView(RecommendBRResponseModel responseModel) {
                assertEquals(expected, responseModel);
            }

            @Override
            public void prepareFailView(String message) {
                fail("prepareFailView(" + message + ") should not be called.");
            }
        };

        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(dummyPresenter, courseComparatorFactory);

        RecommendBRRequestModel requestModel = new RecommendBRRequestModel(brCategoriesSelected, "early");
        recommendBRInteractor.onNext(session);
        recommendBRInteractor.onNext(timetable);
        recommendBRInteractor.recommendBr(requestModel);
    }

    /**
     * Check whether recommendBr passes correct message to RecommendBROutputBoundary.prepareFailView when session
     * matching the timetable session type is not loaded
     */
    @Test
    void testRecommendBrSessionNotLoaded() {

        Timetable timetable = new Timetable(new ArrayList<>(), "F");
        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("3");
        brCategoriesSelected.add("2");

        RecommendBROutputBoundary dummyPresenter = new RecommendBROutputBoundary() {
            @Override
            public void prepareSuccessView(RecommendBRResponseModel responseModel) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Session not loaded yet!", message);
            }
        };

        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(dummyPresenter, courseComparatorFactory);

        RecommendBRRequestModel requestModel = new RecommendBRRequestModel(brCategoriesSelected, "balanced");
        recommendBRInteractor.setTimetable(timetable);
        recommendBRInteractor.recommendBr(requestModel);
    }

    /**
     * Check whether recommendBr passes correct message to RecommendBROutputBoundary.prepareFailView when timetable
     * is not loaded
     */
    @Test
    void testRecommendBrTimetableNotLoaded() {

        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("3");
        brCategoriesSelected.add("2");

        RecommendBROutputBoundary dummyPresenter = new RecommendBROutputBoundary() {
            @Override
            public void prepareSuccessView(RecommendBRResponseModel responseModel) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("Timetable not loaded yet!", message);
            }
        };

        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(dummyPresenter, courseComparatorFactory);

        RecommendBRRequestModel requestModel = new RecommendBRRequestModel(brCategoriesSelected, "balanced");
        recommendBRInteractor.onNext(new Session("F"));
        recommendBRInteractor.recommendBr(requestModel);
    }

    /**
     * Check whether recommendBr passes correct message to RecommendBROutputBoundary.prepareFailView when there are
     * no coursees in session that matches the given input
     */
    @Test
    void testRecommendBrNoMatchingCourses() {

        HashSet<String> brCategoriesSelected = new HashSet<>();
        brCategoriesSelected.add("3");
        brCategoriesSelected.add("2");

        RecommendBROutputBoundary dummyPresenter = new RecommendBROutputBoundary() {
            @Override
            public void prepareSuccessView(RecommendBRResponseModel responseModel) {
                fail("prepareSuccessView should not be called.");
            }

            @Override
            public void prepareFailView(String message) {
                assertEquals("No matching courses found!", message);
            }
        };

        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(dummyPresenter, courseComparatorFactory);

        RecommendBRRequestModel requestModel = new RecommendBRRequestModel(brCategoriesSelected, "balanced");


        Session fallSession = new Session("F");
        fallSession.addCourse(new CalendarCourse("testCourse", new ArrayList<>(), "F", "TEST1", "1"));
        fallSession.addCourse(new CalendarCourse("testCourse2", new ArrayList<>(), "F", "TEST2", "4"));
        fallSession.addCourse(new CalendarCourse("testCourse3", new ArrayList<>(), "F", "TEST3", "1"));
        recommendBRInteractor.onNext(fallSession);
        recommendBRInteractor.setTimetable(new Timetable(new ArrayList<>(), "F"));
        recommendBRInteractor.recommendBr(requestModel);
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