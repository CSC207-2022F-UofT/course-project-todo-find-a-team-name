import entities.*;
import recommend_br_use_case.IDummySessionGateway;
import recommend_br_use_case.IDummyTimetableGateway;
import recommend_br_use_case.RecommendBRInteractor;
import screens.RecommendBRController;
import screens.RecommendBRPresenter;
import screens.RecommendBRWindow;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class RecommendBRTestMain {
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        IDummySessionGateway sessionGateway = sessionType -> {
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
        };

        IDummyTimetableGateway timetableGateway = timetableId -> new Timetable(new ArrayList<>());

        RecommendBRPresenter presenter = new RecommendBRPresenter(null);
        RecommendBRInteractor interactor = new RecommendBRInteractor(sessionGateway, timetableGateway, presenter);
        RecommendBRController controller = new RecommendBRController(interactor);
        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, controller);
        presenter.setView(recommendBRWindow);

        JButton button = new JButton("Recommend BR");
        button.addActionListener(e -> recommendBRWindow.showInputViw(""));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(button);
        frame.pack();
        frame.setVisible(true);
    }
}
