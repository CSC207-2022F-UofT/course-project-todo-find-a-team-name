package timetables_sort_use_case.frameworks_and_drivers;

import display_timetable_use_case.frameworks_and_drivers.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.*;
import screens.MainUI;
import timetables_sort_use_case.interface_adapters.AllTimetablesController;
import timetables_sort_use_case.interface_adapters.AllTimetablesView;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// main imports
import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import display_timetable_use_case.application_business.DisplayTimetableInteractor;
import display_timetable_use_case.frameworks_and_drivers.DisplayTimetablePresenter;
import edit_timetable_use_case.application_business.AddCourseInteractor;
import edit_timetable_use_case.application_business.EditCourseInteractor;
import edit_timetable_use_case.application_business.RemoveCourseInteractor;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import entities.*;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import fileio_use_case.interface_adapters.SessionFileController;
import org.json.simple.parser.ParseException;
import recommend_br_use_case.application_business.CourseComparatorFactory;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;
import timetables_sort_use_case.application_business.*;
import timetables_sort_use_case.interface_adapters.TimetablesSortPresenter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *  An implementation of AllTimetablesView in JSwing.
 *  ttViews are the timetable views that the user browses through
 *  timetablesPanel contains all the ttViews
 */
public class AllTimetablesScreen extends JPanel implements ActionListener, AllTimetablesView {

    private final JFrame frame;
    private TimetableView[] ttViews;
    private final JPanel timetablesPanel;
    private TimetablesSortMenu timetablesSortMenu;
    private final TimetablesSortController controller;
    private final DisplayTimetableController displayTimetableController;
    private final AllTimetablesController allTimetablesController;
    private final MainUI mainUI;
    private final TimetableUI timetableUI;
    public AllTimetablesScreen(JFrame frame, MainUI mainUI, TimetableUI timetableUI,
                               TimetablesSortController controller,
                               DisplayTimetableController displayTimetableController,
                               AllTimetablesController allTimetablesController) {
        this.frame = frame;
        this.controller = controller;
        this.ttViews = null;
        this.setLayout(new BorderLayout());
        JPanel timetablesPanel = new JPanel();
        this.timetablesPanel = timetablesPanel;
        this.timetablesSortMenu = null;
        this.displayTimetableController = displayTimetableController;
        this.allTimetablesController = allTimetablesController;
        this.mainUI = mainUI;
        this.timetableUI = timetableUI;

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
        } else if (e.getActionCommand().equals("Main Menu")) {
            this.frame.getContentPane().removeAll();
            this.frame.add(mainUI);
            this.frame.revalidate();
        } else {
            System.out.println(e.getActionCommand());
            this.frame.getContentPane().removeAll();
            int i = e.getActionCommand().length() - 1;
            allTimetablesController.updateSubscribers(i);
            displayTimetableController.displayTimetable();
            this.frame.add(timetableUI);
            this.frame.revalidate();
        }
    }

    /**
     * prepare a TimeTablesSortMenu and display it instead of this
     */
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


    /**
     * Takes in an array of TimetableViewModels and updates previous ttViews with these timetables
     * gets set to visible at the end to show the change
     * @param timetableViewModels the updated timetables that we want to present
     */
    public void updateTimetables(TimetableViewModel[] timetableViewModels) {
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
            ttViews[i].updateViewModel(timetableViewModels[i]);
        }
        this.frame.add(this);
    }

    public void timetablesSort(String timeButton, String breakButton) {
        controller.timetablesSort(timeButton, breakButton);
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

        JFrame frame = new JFrame();

        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter();
        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter,
                courseComparatorFactory);
        RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);

        AddCoursePresenter addCoursePresenter = new AddCoursePresenter();
        AddCourseInteractor addCourseInteractor = new AddCourseInteractor(addCoursePresenter);
        EditCoursePresenter editCoursePresenter = new EditCoursePresenter();
        EditCourseInteractor editCourseInteractor = new EditCourseInteractor(editCoursePresenter);
        RemoveCoursePresenter removeCoursePresenter = new RemoveCoursePresenter();
        RemoveCourseInteractor removeCourseInteractor = new RemoveCourseInteractor(removeCoursePresenter);
        EditTimetableController editTimetableController = new EditTimetableController(removeCourseInteractor, addCourseInteractor, editCourseInteractor);

        RetrieveTimetableInteractor retrieveTimetableInteractor = new RetrieveTimetableInteractor();
        addCourseInteractor.setRetrieveInteractor(retrieveTimetableInteractor);

        DisplayTimetablePresenter displayTimetablePresenter1 = new DisplayTimetablePresenter();
        DisplayTimetableInteractor displayTimetableInteractor1 = new DisplayTimetableInteractor(displayTimetablePresenter1);
        DisplayTimetableController displayTimetableController1 = new DisplayTimetableController(displayTimetableInteractor1);

        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, recommendBRController, editTimetableController);
        EditTimetableScreen editTimetableScreen = new EditTimetableScreen(frame, editTimetableController, null, displayTimetableController1);
        displayTimetablePresenter1.setView(editTimetableScreen);
        addCoursePresenter.setView(editTimetableScreen);
        editCoursePresenter.setView(editTimetableScreen);
        addCoursePresenter.setView(editTimetableScreen);
        removeCoursePresenter.setView(editTimetableScreen);

        editTimetableScreen.setBRWindow(recommendBRWindow);
        editTimetableScreen.updateTimetable(new TimetableViewModel(new ArrayList<>()));

        Block block1 = new Block("MO", "11:00", "12:00", "");
        Block block2 = new Block("FR", "11:00", "12:00", "");
        java.util.List<Block> blocks1 = new ArrayList<>();
        blocks1.add(block1);
        blocks1.add(block2);

        Block block3 = new Block("WE", "11:00", "12:00", "");
        java.util.List<Block> blocks2 = new ArrayList<>();
        blocks2.add(block3);

        Block block4 = new Block("TU", "16:00", "17:00", "");
        Block block5 = new Block("FR", "16:00", "17:00", "");
        java.util.List<Block> blocks3 = new ArrayList<>();
        blocks3.add(block4);
        blocks3.add(block5);

        Block block6 = new Block("MO", "14:00", "16:00", "");
        java.util.List<Block> blocks4 = new ArrayList<>();
        blocks4.add(block6);

        Section s1 = new Section("LEC0101", "", blocks1);
        Section s2 = new Section("TUT0101", "", blocks2);

        Section s3 = new Section("LEC0401", "", blocks3);
        Section s4 = new Section("TUT0301", "", blocks4);

        java.util.List<Section> sections1 = new ArrayList<>();
        sections1.add(s1);
        sections1.add(s2);
        List<Section> sections2 = new ArrayList<>();
        sections2.add(s3);
        sections2.add(s4);

        TimetableCourse c1;
        TimetableCourse c2;

        try {
            c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
            c2 = new TimetableCourse("some other title", sections2, "", "CSC207H1", "");
        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }

        ArrayList<TimetableCourse> courses = new ArrayList<>();
        courses.add(c1);
        courses.add(c2);
        Timetable timetable = new Timetable(courses, "F");

        recommendBRPresenter.setView(recommendBRWindow);
        recommendBRInteractor.setTimetable(timetable);

        SessionGateway sessionGateway = new SessionGateway();
        Session fSession;
        try {
            fSession = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");
        } catch (IOException | ParseException | InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
        addCourseInteractor.setSession(fSession);
        addCourseInteractor.setTimetable(timetable);
        removeCourseInteractor.setTimetable(timetable);
        editCourseInteractor.setSession(fSession);
        editCourseInteractor.setTimetable(timetable);
        recommendBRInteractor.onNext(fSession);

        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController = new SectionFilterController(sectionFilterInteractor);

        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(new JPanel(), sectionFilterController);
        sectionFilterPresenter.setView(constraintsInputScreen);

        DisplayTimetablePresenter displayTimetablePresenter2 = new DisplayTimetablePresenter();
        DisplayTimetableInteractor displayTimetableInteractor2 = new DisplayTimetableInteractor(displayTimetablePresenter2);
        DisplayTimetableController displayTimetableController2 = new DisplayTimetableController(displayTimetableInteractor2);

        displayTimetableInteractor2.setTimetable(timetable);
        TimetableUI timetableUI = new TimetableUI(displayTimetableController2, editTimetableScreen);
        displayTimetablePresenter2.setView(timetableUI);

        displayTimetableInteractor1.setTimetable(timetable);

        SessionGateway gateway = new SessionGateway();
        SessionGatewayInteractor sessionGatewayInteractor = new SessionGatewayInteractor(gateway);
        SessionFileController sessionFileController = new SessionFileController(sessionGatewayInteractor);

        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editTimetableScreen, timetableUI, sessionFileController);
        timetableUI.setPrevPanel(mainUI);
        editTimetableScreen.setPreviousPanel(mainUI);


        mainUI.setPreferredSize(new Dimension(1280, 720));


        Timetable[] timetables = new Timetable[10];

        for (int i = 0; i < 10; i++) {
            timetables[i] = timetable;
        }

        TimetableGeneratorPresenter timetableGeneratorPresenter = new TimetableGeneratorPresenter();
        TimetableGeneratorInteractor timetableGeneratorInteractor =
                new TimetableGeneratorInteractor(timetableGeneratorPresenter);


        //  Use case 1 main requirements:
        //      1- TimetablesSortPresenter, 2- TimetablesSortInteractor(Presenter), 3- AllTimetablesPublisher,
        //      4- TimetablesSortController(TimetablesSortInteractor, AllTimetablesPublisher),
        //      5- AllTimetablesController(DisplayTimetableInteractor, AllTimetablesPublisher),
        //      6- AllTimetablesScreen(JFrame, MainUI, TimetableUI, TimetablesSortController,
        //      DisplayTimetableController, AllTimetablesController)
        //      7- TimetablesSortPresenter.setView(AllTimetablesScreen)
        //      8,9- subscribe my interactors to JD's publisher

        TimetablesSortPresenter timetablesSortPresenter = new TimetablesSortPresenter();
        TimetablesSortInteractor timetablesSortInteractor = new TimetablesSortInteractor(timetablesSortPresenter);
        AllTimetablesInteractor allTimetablesInteractor = new AllTimetablesInteractor();
        TimetablesSortController timetablesSortController =
                new TimetablesSortController(timetablesSortInteractor, allTimetablesInteractor);
        timetablesSortInteractor.setTimetables(timetables); // this is only for testing, it should automatically
        // update when its publisher publishes
        AllTimetablesController allTimetablesController =
                new AllTimetablesController(displayTimetableInteractor1, allTimetablesInteractor);
        AllTimetablesScreen allTimetablesScreen = new AllTimetablesScreen(frame, mainUI, timetableUI,
                timetablesSortController, displayTimetableController2, allTimetablesController);
        allTimetablesScreen.updateTimetables(timetableViewModels); // this is only for testing, it should be called by
        // JD/Prime when they display my screen
        timetablesSortPresenter.setView(allTimetablesScreen);

        timetableGeneratorInteractor.subscribe(allTimetablesInteractor);
        timetableGeneratorInteractor.subscribe(timetablesSortInteractor);




        frame.add(allTimetablesScreen);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}

