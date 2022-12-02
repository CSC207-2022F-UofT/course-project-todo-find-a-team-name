package screens;

import display_timetable_use_case.interface_adapters.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AllTimetablesScreen extends JPanel implements ActionListener {

    private JFrame frame;
    private final AllTimetablesController AllTimetablesController;
    private TimetableViewModel[] timetables;
    private EditTimetableScreen editTimetableScreen;
    public AllTimetablesScreen(JFrame frame, AllTimetablesController controller, TimetableViewModel[] timetables) {
        this.AllTimetablesController = controller;
        this.timetables = timetables;
        this.setLayout(new BorderLayout());
        JPanel top = new JPanel();
        top.setSize(100, 100);
        JButton sort = new JButton("Sort");
        JButton mainMenu = new JButton("Main Menu");
        top.add(sort);
        top.add(mainMenu);
        sort.addActionListener(this);
        mainMenu.addActionListener(this);
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0, 2, 10, 10));
        panel.setSize(500, 500);

        for (int i = 0; i < timetables.length; i++) {
            TimetableView timetable = new TimetableView(600, 350, timetables[i]);
            JButton btn = new JButton("Timetable " + i);
            btn.addActionListener(this);
            JPanel cont = new JPanel();
            cont.setLayout(new BorderLayout());
            cont.setSize(600, 400);
            cont.add(timetable, BorderLayout.CENTER);
            cont.add(btn, BorderLayout.NORTH);
            panel.add(cont);
        }
        JScrollPane scrollPane = new JScrollPane(panel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        this.add(top, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns User to Main Menu if mainMenu is clicked, sends User to sorter screen if sort is clicked,
     * opens TimeTableUI of the given timetable if a timetable button is clicked.
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked " + e.getActionCommand());

        if (e.getActionCommand().equals("sort")) {

        } else if (e.getActionCommand().equals("mainMenu")) {

        } else {
            int i = e.getActionCommand().length() - 1;
            TimetableViewModel timetable = this.timetables[i];

        }
    }
    /**
     * THIS IS FOR TESTING PURPOSES ONLY
     */
    public static void main(String[] args) {
        java.util.List<TimetableViewCourseModel> courseData = new ArrayList<>();
        java.util.List<TimetableViewSectionModel> sectionModels1 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels1 = new ArrayList<>();
        blockModels1.add(new TimetableViewBlockModel(0, 11, 12));
        blockModels1.add(new TimetableViewBlockModel(4, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("LEC0101", blockModels1));

        java.util.List<TimetableViewBlockModel> blockModels2 = new ArrayList<>();
        blockModels2.add(new TimetableViewBlockModel(2, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("TUT0101", blockModels2));

        courseData.add(new TimetableViewCourseModel("CSC236H1", sectionModels1));


        java.util.List<TimetableViewSectionModel> sectionModels2 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels3 = new ArrayList<>();
        blockModels3.add(new TimetableViewBlockModel(1, 16, 17));
        blockModels3.add(new TimetableViewBlockModel(4, 16, 17));
        sectionModels2.add(new TimetableViewSectionModel("LEC0401", blockModels3));

        List<TimetableViewBlockModel> blockModels4 = new ArrayList<>();
        blockModels4.add(new TimetableViewBlockModel(0, 14, 16));
        sectionModels2.add(new TimetableViewSectionModel("TUT0301", blockModels4));

        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));

        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
        TimetableViewModel[] timetables = new TimetableViewModel[10];

        for (int i = 0; i < 10; i++) {
            timetables[i] = timetableViewModel;
        }



        JFrame frame = new JFrame();
        AllTimetablesController allTimetablesController = new AllTimetablesController();
        AllTimetablesScreen allTimetablesScreen = new AllTimetablesScreen(frame, allTimetablesController, timetables);
        frame.add(allTimetablesScreen);
        frame.pack();
        frame.setVisible(true);
    }
}

