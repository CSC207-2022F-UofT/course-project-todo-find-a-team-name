import edit_timetable_use_case.AddCourseInteractor;
import edit_timetable_use_case.EditTimetableController;
import edit_timetable_use_case.RemoveCourseInteractor;
import entities.*;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import screens.*;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

// This for testing purposes only during development
// TODO: remove this class
public class RecommendBRTestMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        RecommendBRPresenter presenter = new RecommendBRPresenter(null);
        RecommendBRInteractor interactor = new RecommendBRInteractor(presenter);
        RecommendBRController controller = new RecommendBRController(interactor);

        AddCoursePresenter addCoursePresenter = new AddCoursePresenter();
        RemoveCoursePresenter removeCoursePresenter = new RemoveCoursePresenter();

        AddCourseInteractor addCourseInteractor = new AddCourseInteractor(addCoursePresenter);
        Timetable timetable = new Timetable(new ArrayList<>(), "F");
        addCourseInteractor.setTimetable(timetable);

        RemoveCourseInteractor removeCourseInteractor = new RemoveCourseInteractor(removeCoursePresenter);

        EditTimetableController editTimetableController = new EditTimetableController(removeCourseInteractor, addCourseInteractor);
        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, controller, editTimetableController);
        presenter.setView(recommendBRWindow);
        EditTimetableScreen timetableView = new EditTimetableScreen(frame, editTimetableController);
        timetableView.setBRWindow(recommendBRWindow);
        removeCoursePresenter.setView(timetableView);
        addCoursePresenter.setView(timetableView);
        addCourseInteractor.setSession(generateSession());

        frame.add(timetableView);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    private static Session generateSession(){
        Session session = new Session("F");
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
        return session;
    }
}
