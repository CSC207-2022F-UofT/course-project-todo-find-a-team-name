package recommend_br_use_case;

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
public class RecommendBRInteractor implements RecommendBRInputBoundary{

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
        Session session = null;
        if (timetable.getSessionType().equals("F")){
            session = fSession;
        } else if (timetable.getSessionType().equals("S")){
            session = sSession;
        }

        if (session == null)
            presenter.prepareFailView("Session not loaded yet!");
        else if (timetable == null)
            presenter.prepareFailView("Timetable not loaded yet!");

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

        session = new Session(timetable.getSessionType());
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
