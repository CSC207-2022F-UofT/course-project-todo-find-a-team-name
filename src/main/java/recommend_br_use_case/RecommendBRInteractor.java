package recommend_br_use_case;

import entities.*;


import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Class that interacts with other classes to recommend breadth courses,
 * based on the given the request model and output the result to presenter
 * to prepare appropriate view.
 */
public class RecommendBRInteractor implements RecommendBRInputBoundary{

    private final IDummySessionGateway sessionGateway;
    private final IDummyTimetableGateway timetableGateway;
    private final RecommendBROutputBoundary presenter;

    /**
     * Constructs RecommendBRInteractor based on the given SessionGateway, TimetableGateway, and
     * RecommendBROutputBoundary (presenter)
     *
     * @param sessionGateway gateway for the session
     * @param timetableGateway gateway for the timetable
     * @param presenter presenter used to prepare appropriate view
     */
    public RecommendBRInteractor(IDummySessionGateway sessionGateway, IDummyTimetableGateway timetableGateway, RecommendBROutputBoundary presenter){
        this.sessionGateway = sessionGateway;
        this.timetableGateway = timetableGateway;
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
        Timetable timetable = timetableGateway.get(requestModel.getTimetableId());

        Session[] sessions = sessionGateway.get();

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

        BRRecommender brRecommender = new BRRecommender(timetable, sessions[0], sessions[1], sessions[2],
                requestModel.getBrCategoriesSelected(), courseComparator);

        List<TimetableCourse> recommendedCourses = brRecommender.recommendBr();

        if (recommendedCourses.size() == 0){
            presenter.prepareFailView("No matching courses found!");
            return;
        }

        List<BRCourseResponseModel> courseModels = new ArrayList<>();

        for (TimetableCourse course : recommendedCourses){

            BRSectionResponseModel lectureModel = null;
            if (course.getLecture() != null) {
                List<BRBlockResponseModel> lectureBlockModels = new ArrayList<>();
                for (Block lectureBlock : course.getLecture().getBlocks()) {
                    lectureBlockModels.add(new BRBlockResponseModel(lectureBlock.getDay(), lectureBlock.getStartTime(), lectureBlock.getEndTime()));
                }
                lectureModel = new BRSectionResponseModel(course.getLecture().getCode(), lectureBlockModels);
            }

            BRSectionResponseModel tutorialModel = null;
            if (course.getTutorial() != null) {
                List<BRBlockResponseModel> tutorialBlockModels = new ArrayList<>();
                for (Block tutorialBlock : course.getTutorial().getBlocks()) {
                    tutorialBlockModels.add(new BRBlockResponseModel(tutorialBlock.getDay(), tutorialBlock.getStartTime(), tutorialBlock.getEndTime()));
                }
                tutorialModel = new BRSectionResponseModel(course.getTutorial().getCode(), tutorialBlockModels);
            }

            BRSectionResponseModel practicalModel = null;
            if (course.getPractical() != null) {
                List<BRBlockResponseModel> practicalBlockModels = new ArrayList<>();
                for (Block practicalBlock : course.getPractical().getBlocks()) {
                    practicalBlockModels.add(new BRBlockResponseModel(practicalBlock.getDay(), practicalBlock.getStartTime(), practicalBlock.getEndTime()));
                }
                practicalModel = new BRSectionResponseModel(course.getPractical().getCode(), practicalBlockModels);
            }

            courseModels.add(new BRCourseResponseModel(course.getCourseCode(), course.getTitle(), course.getBreadth(), lectureModel, tutorialModel, practicalModel));
        }

        presenter.prepareSuccessView(new RecommendBRResponseModel(courseModels));
    }
}
