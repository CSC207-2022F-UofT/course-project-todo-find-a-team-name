import entities.Block;
import entities.CalendarCourse;
import entities.Section;
import entities.Session;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// This method for testing purposes only during development
// TODO: remove this class
public class RecommendBRTestMain {
    public static void main(String[] args) throws IOException, ParseException {
    /*public static void main(String[] args) {
        JFrame frame = new JFrame();

        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter();
        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter, courseComparatorFactory);
        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter(null);
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter);
        RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);

        AddCoursePresenter addCoursePresenter = new AddCoursePresenter();
        AddCourseInteractor addCourseInteractor = new AddCourseInteractor(addCoursePresenter);
        RemoveCoursePresenter removeCoursePresenter = new RemoveCoursePresenter();
        RemoveCourseInteractor removeCourseInteractor = new RemoveCourseInteractor(removeCoursePresenter);
        EditTimetableController editTimetableController = new EditTimetableController(removeCourseInteractor, addCourseInteractor, editCourseInteractor);

        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, recommendBRController, editTimetableController);
        EditTimetableScreen editTimetableScreen = new EditTimetableScreen(frame, editTimetableController);
        editTimetableScreen.setBRWindow(recommendBRWindow);
        editTimetableScreen.updateTimetable(new TimetableViewModel(new ArrayList<>()));

        recommendBRPresenter.setView(recommendBRWindow);
        addCoursePresenter.setView(editTimetableScreen);
        removeCoursePresenter.setView(editTimetableScreen);

        Timetable timetable = new Timetable(new ArrayList<>(), "F");
        addCourseInteractor.setTimetable(timetable);
        removeCourseInteractor.setTimetable(timetable);
        recommendBRInteractor.setTimetable(timetable);

        SessionGateway sessionGateway = new SessionGateway();
        Session fSession = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");

        addCourseInteractor.setSession(fSession);
        recommendBRInteractor.setFallSession(fSession);

        frame.add(editTimetableScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
