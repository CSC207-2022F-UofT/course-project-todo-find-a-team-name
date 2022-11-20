package screens;

import recommend_br_use_case.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Presenter for recommend BR use case that is responsible to format given data to display it in view
 */
public class RecommendBRPresenter implements RecommendBROutputBoundary {

    IRecommendBRView view;

    /**
     * Constructs RecommendBRPresenter given IRecommendBRView (view for recommend BR use case)
     *
     * @param view class used to display information
     */
    public RecommendBRPresenter(IRecommendBRView view){
        this.view = view;
    }

    /**
     * Perform formatting to convert given RecommendBRResponseModel to RecommendBRViewModel, and pass that to
     * IView to display the data contained in responseModel to the user
     *
     * @param responseModel data formatted to prepare for success view
     */
    @Override
    public void prepareSuccessView(RecommendBRResponseModel responseModel) {
        List<BRCourseViewModel> courseViewModels = new ArrayList<>();
        for (BRCourseResponseModel courseModel : responseModel.getCourses()){
            String brCategory = formatBrCategory(courseModel.getBrCategory());
            String lectureCode = courseModel.getLectureModel() == null ? null : courseModel.getLectureModel().getCode();
            String tutorialCode = courseModel.getTutorialModel() == null ? null : courseModel.getTutorialModel().getCode();
            String practicalCode = courseModel.getPracticalModel() == null ? null : courseModel.getPracticalModel().getCode();

            courseViewModels.add(new BRCourseViewModel(courseModel.getCode(), courseModel.getTitle(),
                    brCategory, lectureCode, tutorialCode, practicalCode,
                    createBlockInfos(courseModel.getLectureModel()),
                    createBlockInfos(courseModel.getTutorialModel()),
                    createBlockInfos(courseModel.getPracticalModel())));
        }
        RecommendBRViewModel viewModel = new RecommendBRViewModel(courseViewModels);
        view.showSuccessView(viewModel);

    }

    /**
     * Display message to the user
     *
     * @param message text presented to the view
     */
    @Override
    public void prepareFailView(String message) {
        view.showFailView(message);
    }

    /**
     * Format breadth category  represented as "1", "2", "3", "4", and "5" to "Creative and Cultural Representations"
     * "Thought, Belief, and Behaviour", "Society and Its Institutions", "Living Things and Their Environment",
     * and "The Physical and Mathematical Universes", respectively.
     *
     * @param brCategory breadth category  represented as "1", "2", "3", "4", and "5"
     * @return breadth category represented as its full text (e.g. "Creative and Cultural Representations")
     */
    private static String formatBrCategory(String brCategory){
        String newBrCategory = "";
        switch (brCategory){
            case "1":
                newBrCategory = "Creative and Cultural Representations";
                break;
            case "2":
                newBrCategory = "Thought, Belief, and Behaviour";
                break;
            case "3":
                newBrCategory = "Society and Its Institutions";
                break;
            case "4":
                newBrCategory = "Living Things and Their Environment";
                break;
            case "5":
                newBrCategory = "The Physical and Mathematical Universes";
                break;
        }
        return newBrCategory;
    }

    /**
     * Returns list of block information from the given BRSectionResponseModel
     * Each string is formatted as:
     *      "[day], [start time] ~ [end time]"
     * day is written as Monday, Tuesday, and so on.
     * start time and end time is written as hh:mm
     *
     * @param sectionResponseModel response model representing section
     * @return list of block information from the given BRSectionResponseModel
     */
    private static List<String> createBlockInfos(BRSectionResponseModel sectionResponseModel){
        if (sectionResponseModel == null)
            return new ArrayList<>();

        List<String> blockInfos = new ArrayList<>();
        for (BRBlockResponseModel blockResponseModel : sectionResponseModel.getBrBlockResponseModels()){

            String day = intToStringDay(blockResponseModel.getDay());
            String startTime = doubleToStringTime(blockResponseModel.getStartTime());
            String endTime = doubleToStringTime(blockResponseModel.getEndTime());

            blockInfos.add(day + ", " + startTime + " ~ " + endTime);
        }

        return blockInfos;
    }

    /**
     * Returns time as a string formatted as "hh:mm" from the given time represented
     * in double
     *
     * @param doubleTime time represented as double (e.g. 13:30 is represented as 13.5)
     * @return time represented as String as "hh:mm" corresponding to given time
     */
    private static String doubleToStringTime(double doubleTime){
        int hour = (int) doubleTime;
        String min = String.valueOf((int) ((doubleTime - hour) * 100));
        return hour + ":" + (min.length() == 1 ? "0":"") + min;
    }

    /**
     * Returns day represented by String formatted as "Monday", "Tuesday", and so on
     * from the given day represented by integer from 0 to 4
     *
     * @param intDay day represented as an integer from 0 to 4
     * @return day as a String formatted as "Monday", "Tuesday", and so on.
     */
    private static String intToStringDay(int intDay){
        String stringDay = "";
        switch (intDay){
            case 0:
                stringDay = "Monday";
                break;
            case 1:
                stringDay = "Tuesday";
                break;
            case 2:
                stringDay = "Wednesday";
                break;
            case 3:
                stringDay = "Thursday";
                break;
            case 4:
                stringDay = "Friday";
                break;
        }
        return stringDay;
    }

    /**
     * Set view of this presenter
     *
     * @param view new view object
     */
    public void setView(IRecommendBRView view) {
        this.view = view;
    }

}
