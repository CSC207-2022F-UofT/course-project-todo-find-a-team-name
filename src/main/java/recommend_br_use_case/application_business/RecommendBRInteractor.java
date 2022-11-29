package recommend_br_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.EntityConverter;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that interacts with other classes to recommend breadth courses,
 * based on the given the request model and output the result to presenter
 * to prepare appropriate view.
 */
public class RecommendBRInteractor implements RecommendBRInputBoundary {

    private Session fSession = null;
    private Session sSession = null;
    private Timetable timetable = null;
    private final RecommendBROutputBoundary presenter;

    /**
     * Constructs RecommendBRInteractor based on the given SessionGateway, TimetableGateway, and
     * RecommendBROutputBoundary (presenter)
     *
     * @param presenter presenter used to prepare appropriate view
     */
    public RecommendBRInteractor(RecommendBROutputBoundary presenter){
        this.presenter = presenter;
    }

    /**
     * Generate list of TimetableCourse matching the request model, using BRRecommender,
     * and output the result to presenter.
     * If there are no matching courses based on the request model, ask presenter to prepare fail view
     * Otherwise, prepare success view.
     *
     * @param requestModel data used to recommend breadth courses
     */
    @Override
    public void recommendBr(RecommendBRRequestModel requestModel) {

        // TODO: Remove this
        timetable = new Timetable(new ArrayList<>(), "F");

        if (timetable == null) {
            presenter.prepareFailView("Timetable not loaded yet!");
            return;
        }

        Session session = null;
        if (timetable.getSessionType().equals("F")){
            session = fSession;
        } else if (timetable.getSessionType().equals("S")){
            session = sSession;
        }

        // TODO: Remove this
        session = new Session("F");
        for (int i = 8; i < 21; i++){
            List<Block> blocks = new ArrayList<>();
            List<Section> sections1 = new ArrayList<>();

            blocks.add(new Block("MO", i + ":00", i + ":00", "room1"));
            blocks.add(new Block("MO", i + ":00", i + ":00", "room2"));
            blocks.add(new Block("TU", i + ":00", i + ":00", "room2"));
            blocks.add(new Block("TH", i + ":00", i + ":00", "room3"));

            sections1.add(new Section("LEC0101", "Kai", blocks));
            sections1.add(new Section("LEC0201", "Kai", blocks));
            sections1.add(new Section("LEC0301", "Kai", blocks));
            sections1.add(new Section("TUT0101", "Kai", blocks));
            sections1.add(new Section("TUT0201", "Kai", blocks));
            sections1.add(new Section("PRA0301", "Kai", blocks));
            sections1.add(new Section("PRA0401", "Kai", blocks));

            session.addCourse(new CalendarCourse("courseF", sections1, "F", "COSF" + i, "1"));
        }

        if (session == null) {
            presenter.prepareFailView("Session not loaded yet!");
            return;
        }


        Comparator<Course> courseComparator;
        switch (requestModel.getPreferredTime()){
            case "early":
                courseComparator = new TargetTimeCourseComparator(0);
                break;
            case "balanced":
                courseComparator = new TargetTimeCourseComparator(14);
                break;
            case "late":
                courseComparator = new TargetTimeCourseComparator(24);
                break;
            default:
                courseComparator = null;
        }

        BRRecommender brRecommender = new BRRecommender(timetable, session,
                requestModel.getBrCategoriesSelected(), courseComparator);

        List<TimetableCourse> recommendedCourses = brRecommender.recommendBr();

        if (recommendedCourses.size() == 0){
            presenter.prepareFailView("No matching courses found!");
            return;
        }

        List<CourseModel> courseModels = new ArrayList<>();

        for (TimetableCourse course : recommendedCourses){
            courseModels.add(EntityConverter.generateCourseResponse(course));
        }

        presenter.prepareSuccessView(new RecommendBRResponseModel(courseModels));
    }
}
