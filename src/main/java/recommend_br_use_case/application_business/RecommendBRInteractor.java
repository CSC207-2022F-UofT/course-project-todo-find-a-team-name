package recommend_br_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.EntityConverter;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;

/**
 * Class that interacts with other classes to recommend breadth courses,
 * based on the given the request model and output the result to presenter
 * to prepare appropriate view.
 */
public class RecommendBRInteractor implements RecommendBRInputBoundary, Subscriber<Object>{

    private Session session;
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

        if (session == null) {
            presenter.prepareFailView("Session not loaded yet!");
            return;
        } else if (!session.getSessionType().equals(timetable.getSessionType())){
            presenter.prepareFailView("Timetable session is different! Timetable is "
                    + timetable.getSessionType() + " while Session is " + session.getSessionType());
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
     * Sets the session contained in this class to the given Session entity
     *
     * @param session new session
     */
    public void setSession(Session session) {
        this.session = session;
    }


    /**
     * Sets the timetable contained in this class to the given Timetable entity
     *
     * @param timetable new timetable entity
     */
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {}

    /**
     * Update session or timetable if the given item is Session or Timetable.
     * @param item the item
     */
    @Override
    public void onNext(Object item) {
        if (item instanceof Session){
            session = (Session) item;
        } else if (item instanceof Timetable){
            timetable = (Timetable) item;
        }
    }

    /**
     *
     * @param throwable the exception
     */
    @Override
    public void onError(Throwable throwable) {}

    /**
     *
     */
    @Override
    public void onComplete() {

    }
}
