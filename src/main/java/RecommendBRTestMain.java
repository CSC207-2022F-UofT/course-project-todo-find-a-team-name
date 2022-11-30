import edit_timetable_use_case.AddCourseInteractor;
import edit_timetable_use_case.EditTimetableController;
import edit_timetable_use_case.RemoveCourseInteractor;
import entities.*;
import recommend_br_use_case.application_business.CourseComparatorFactory;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import screens.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// This method for testing purposes only during development
// TODO: remove this class
public class RecommendBRTestMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter(null);
        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter, courseComparatorFactory);
        RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);

        AddCoursePresenter addCoursePresenter = new AddCoursePresenter();
        AddCourseInteractor addCourseInteractor = new AddCourseInteractor(addCoursePresenter);
        RemoveCoursePresenter removeCoursePresenter = new RemoveCoursePresenter();
        RemoveCourseInteractor removeCourseInteractor = new RemoveCourseInteractor(removeCoursePresenter);
        EditTimetableController editTimetableController = new EditTimetableController(removeCourseInteractor, addCourseInteractor);

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

        Session fSession = generateSession();
        addCourseInteractor.setSession(fSession);
        recommendBRInteractor.setFallSession(fSession);

        frame.add(editTimetableScreen);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static Session generateSession(){
        Session session = new Session("F");
        for (int i = 8; i < 21; i++){
            List<Block> blocks = new ArrayList<>();
            List<Section> sections1 = new ArrayList<>();

            blocks.add(new Block("MO", i + ":00", (i + 1) + ":00", "room1"));
            blocks.add(new Block("TU", i + ":00", (i + 1) + ":00", "room2"));
            blocks.add(new Block("TH", i + ":00", (i + 1) + ":00", "room3"));

            sections1.add(new Section("LEC0101", "Kai", blocks));
            sections1.add(new Section("LEC0201", "Kai", blocks));
            sections1.add(new Section("LEC0301", "Kai", blocks));
            sections1.add(new Section("TUT0101", "Kai", blocks));
            sections1.add(new Section("TUT0201", "Kai", blocks));
            sections1.add(new Section("PRA0301", "Kai", blocks));
            sections1.add(new Section("PRA0401", "Kai", blocks));

            session.addCourse(new CalendarCourse("courseF", sections1, "F", "COS-" + i,
                    "1"));
        }
        return session;
    }
}
