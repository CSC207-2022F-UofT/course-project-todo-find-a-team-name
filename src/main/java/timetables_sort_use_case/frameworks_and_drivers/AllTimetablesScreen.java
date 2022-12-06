package timetables_sort_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.*;

import entities.*;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
import timetables_sort_use_case.application_business.TimetablesSortInteractor;
import timetables_sort_use_case.interface_adapters.AllTimetablesView;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;
import timetables_sort_use_case.interface_adapters.TimetablesSortPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;


public class AllTimetablesScreen extends JPanel implements ActionListener, AllTimetablesView {

    private JFrame frame;
    private TimetableViewModel[] timetableViewModels;
    private TimetableView[] ttViews;
    private final JPanel timetablesPanel;
    private TimetablesSortMenu timetablesSortMenu;
    private TimetablesSortController controller;
    public AllTimetablesScreen(JFrame frame, TimetablesSortController controller) {
        this.frame = frame;
        this.controller = controller;
        this.ttViews = null;
        this.setLayout(new BorderLayout());
        JPanel timetablesPanel = new JPanel();
        this.timetablesPanel = timetablesPanel;
        this.timetablesSortMenu = null;

        JPanel top = new JPanel();
        top.setSize(100, 100);
        JButton sort = new JButton("Sort");
        JButton mainMenu = new JButton("Main Menu");
        top.add(sort);
        top.add(mainMenu);
        sort.addActionListener(this);
        mainMenu.addActionListener(this);

        timetablesPanel.setLayout(new GridLayout(0, 2, 10, 10));
        timetablesPanel.setSize(500, 500);
        JScrollPane scrollPane = new JScrollPane(timetablesPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);


        this.add(top, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns User to MainMenuUI if mainMenu is clicked, sends User to sorter screen if sort is clicked,
     * opens TimeTableUI of the given timetable if a timetable button is clicked.
     */
    public void actionPerformed(ActionEvent e) {
        System.out.println("Clicked " + e.getActionCommand());

        if (e.getActionCommand().equals("Sort")) {
            openTimetablesSortMenu();
        } else if (e.getActionCommand().equals("mainMenu")) {

        } else {
            int i = e.getActionCommand().length() - 1;
            TimetableViewModel timetable = this.timetableViewModels[i];

        }
    }

    public void openTimetablesSortMenu() {
        if (timetablesSortMenu != null) {
            timetablesSortMenu.setVisible(false);
            frame.remove(timetablesSortMenu);
        }
        timetablesSortMenu = new TimetablesSortMenu(this.frame, this);
        frame.add(timetablesSortMenu);
        this.setVisible(false);
        timetablesSortMenu.setVisible(true);
        this.frame.pack();
    }

    public void updateTimetables(TimetableViewModel[] timetableViewModels) {
        this.timetableViewModels = timetableViewModels;
        if (ttViews == null) {
            ttViews = new TimetableView[timetableViewModels.length];
            for (int i = 0; i < timetableViewModels.length; i++) {
                ttViews[i] = new TimetableView(600, 350, timetableViewModels[i]);
                JButton btn = new JButton("Timetable " + i);
                btn.addActionListener(this);
                JPanel container = new JPanel();
                container.setLayout(new BorderLayout());
                container.setSize(600, 400);
                container.add(ttViews[i], BorderLayout.CENTER);
                container.add(btn, BorderLayout.NORTH);
                timetablesPanel.add(container);
            }
        }
        for (int i = 0; i < ttViews.length; i++) {
            ttViews[i].setVisible(false);
            ttViews[i].updateViewModel(timetableViewModels[i]);
            ttViews[i].setVisible(true);
        }
    }

    public void sort(String timebutton, String breakButton) {
        controller.sort(timebutton, breakButton);
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
        TimetableViewModel[] timetableViewModels = new TimetableViewModel[10];

        for (int i = 0; i < 10; i++) {
            timetableViewModels[i] = timetableViewModel;
        }

        Block block1 = new Block("MO", "11:00", "12:00", "");
        Block block2 = new Block("FR", "11:00", "12:00", "");
        List<Block> blocks1 = new ArrayList<>();
        blocks1.add(block1);
        blocks1.add(block2);

        Block block3 = new Block("WE", "11:00", "12:00", "");
        List<Block> blocks2 = new ArrayList<>();
        blocks2.add(block3);

        Block block4 = new Block("TU", "16:00", "17:00", "");
        Block block5 = new Block("FR", "16:00", "17:00", "");
        List<Block> blocks3 = new ArrayList<>();
        blocks3.add(block4);
        blocks3.add(block5);

        Block block6 = new Block("MO", "14:00", "16:00", "");
        List<Block> blocks4 = new ArrayList<>();
        blocks4.add(block6);

        Section s1 = new Section("LEC0101", "", blocks1);
        Section s2 = new Section("TUT0101", "", blocks2);

        Section s3 = new Section("LEC0401", "", blocks3);
        Section s4 = new Section("TUT0301", "", blocks4);

        List<Section> sections1 = new ArrayList<>();
        sections1.add(s1);
        sections1.add(s2);
        List<Section> sections2 = new ArrayList<>();
        sections2.add(s3);
        sections2.add(s4);

        TimetableCourse c1 = null;
        try {
            c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
        TimetableCourse c2 = null;
        try {
            c2 = new TimetableCourse("some other title", sections2, "", "CSC209H1", "");
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        ArrayList<TimetableCourse> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        Timetable timetable = new Timetable(courses, "F");

        Timetable[] timetables = new Timetable[10];

        for (int i = 0; i < 10; i++) {
            timetables[i] = timetable;
        }

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        TimetablesSortPresenter presenter = new TimetablesSortPresenter();
        TimetablesSortInteractor interactor = new TimetablesSortInteractor(presenter);
        TimetablesSortController controller = new TimetablesSortController(interactor);
        interactor.setTimetables(timetables);
        RetrieveTimetableInteractor retrieveTimetableInteractor = new RetrieveTimetableInteractor();
        interactor.setRetrieveInteractor(retrieveTimetableInteractor);
        AllTimetablesScreen timetablesScreen = new AllTimetablesScreen(frame, controller);
        timetablesScreen.updateTimetables(timetableViewModels);
        presenter.setView(timetablesScreen);
        frame.add(timetablesScreen);
        frame.pack();
        frame.setVisible(true);
    }
}

