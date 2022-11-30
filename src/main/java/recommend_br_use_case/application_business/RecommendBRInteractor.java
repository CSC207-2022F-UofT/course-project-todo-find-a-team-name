package recommend_br_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.EntityConverter;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that interacts with other classes to recommend breadth courses,
 * based on the given the request model and output the result to presenter
 * to prepare appropriate view.
 */
public class RecommendBRInteractor implements RecommendBRInputBoundary {

    private Session fallSession = null;
    private Session winterSession = null;
    private Timetable timetable = null;
    private final RecommendBROutputBoundary presenter;
    private final CourseComparatorFactory courseComparatorFactory;

    /**
     * Constructs RecommendBRInteractor based on the given SessionGateway, TimetableGateway, and
     * RecommendBROutputBoundary (presenter)
     *
     * @param presenter presenter used to prepare appropriate view
     * @param courseComparatorFactory c
     */
    public RecommendBRInteractor(RecommendBROutputBoundary presenter, CourseComparatorFactory courseComparatorFactory){
        this.presenter = presenter;
        this.courseComparatorFactory = courseComparatorFactory;
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

        if (timetable == null) {
            presenter.prepareFailView("Timetable not loaded yet!");
            return;
        }

        Session session = null;
        if (timetable.getSessionType().equals("F")){
            session = fallSession;
        } else if (timetable.getSessionType().equals("S")){
            session = winterSession;
        }

        if (session == null) {
            presenter.prepareFailView("Session not loaded yet!");
            return;
        }

        Comparator<Course> courseComparator = courseComparatorFactory.createComparator(requestModel.getPreferredTime());

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

    /**
     * Sets the fall session contained in this class to the given Session entity
     *
     * @param fSession new fall session
     */
    public void setFallSession(Session fSession) {
        this.fallSession = fSession;
    }

    /**
     * Sets the winter session contained in this class to the given Session entity
     *
     * @param sSession new winter session
     */
    public void setWinterSession(Session sSession) {
        this.winterSession = sSession;
    }

    /**
     * Sets the timetable contained in this class to the given Timetable entity
     *
     * @param timetable new timetable entity
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}
