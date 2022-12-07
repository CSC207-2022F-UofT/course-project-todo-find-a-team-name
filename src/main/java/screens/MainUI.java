package screens;

// TODO: Remove these imports (It's used for main)
import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import display_timetable_use_case.application_business.DisplayTimetableInteractor;

import edit_timetable_use_case.application_business.AddCourseInteractor;
import edit_timetable_use_case.application_business.EditCourseInteractor;
import edit_timetable_use_case.application_business.RemoveCourseInteractor;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.DisplayTimetablePresenter;
import display_timetable_use_case.frameworks_and_drivers.TimetableUI;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import entities.*;


import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.application_business.timetable_specific_classes.TimetableGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import fileio_use_case.interface_adapters.SessionFileController;
import fileio_use_case.interface_adapters.TimetableFileController;
import org.json.simple.parser.ParseException;
import overlap_crap_fix_locations_later.OverlapInputDialog;
import recommend_br_use_case.application_business.CourseComparatorFactory;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;

import entities.InvalidSectionsException;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to display the main menu of this program that allow user to import files and navigates user to
 * other screens.
 * Instance Attributes:
 *      - fallSessionFilePath: JLabel representing the filepath of the session data that user imports.
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 *      - filePathTimetable: filepath of the timetable data user imports
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 */
public class MainUI extends JPanel implements ActionListener {

    private JLabel fallSessionFilePath;
    private JLabel timetableFilePath;
    private JLabel winterSessionFilePath;
    public SessionFileController sessionController;

    private final ConstraintsInputScreen constraintsInputScreen;
    private final EditTimetableScreen editTimetableScreen;
    private final TimetableUI timetableUI;
    private final JFrame frame;
    private final JPanel timetablePanel;

    /**
     * Constructs MainUI with title, import timetable/session buttons, and display/edit/generate timetable buttons.
     *
     * @param frame window that this screen belongs to
     * @param constraintsInputScreen input screen for generate timetable button
     * @param editTimetableScreen screen for editing timetable
     * @param timetableUI screen for displaying timetable
     * @param sessionController controller used for importing sessions
     */
    public MainUI(JFrame frame, ConstraintsInputScreen constraintsInputScreen, EditTimetableScreen editTimetableScreen,
                  TimetableUI timetableUI, SessionFileController sessionController){
        super();
        this.constraintsInputScreen = constraintsInputScreen;
        this.editTimetableScreen = editTimetableScreen;
        this.timetableUI = timetableUI;
        this.frame = frame;
        this.sessionController = sessionController;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Main menu");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        add(title, BorderLayout.PAGE_START);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

        timetablePanel = createTimetablePanel();

        JButton generateTimetable = new JButton("Generate timetable");
        generateTimetable.addActionListener(this);
        generateTimetable.setAlignmentX(JButton.CENTER_ALIGNMENT);

        wrapperPanel.add(Box.createVerticalStrut(50));
        wrapperPanel.add(timetablePanel);
        wrapperPanel.add(Box.createVerticalStrut(50));
        wrapperPanel.add(generateTimetable);

        JPanel centerPanel = new JPanel();
        centerPanel.add(wrapperPanel);

        JPanel importSessionPanel = createImportSessionPanel();

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
    }

    /**
     * Create and return panel that allow user to import existing timetable.
     * filePathTimetable instance attribute is also updated to the JLabel representing the current
     * file path imported for timetable
     *
     * @return panel that allow user to import, edit and display existing timetable
     */
    private JPanel createTimetablePanel(){
        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.PAGE_AXIS));

        TitledBorder timetableBorder = BorderFactory.createTitledBorder("");
        timetableBorder.setTitleJustification(TitledBorder.CENTER);
        timetablePanel.setBorder(timetableBorder);
        JPanel importTimetablePanel = new JPanel();
        JButton importTimetable = new JButton("Import timetable");
        importTimetable.addActionListener(this);
        timetableFilePath = new JLabel("Choose the file...");
        importTimetablePanel.add(importTimetable);
        importTimetablePanel.add(timetableFilePath);

        timetablePanel.add(importTimetablePanel);
        return timetablePanel;
    }

    /**
     * Add edit and display button to timetablePanel located at the top-center of the screen
     */
    private void addTimetableButtons(){
        JPanel timetableButtons = new JPanel();
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(this);

        timetableButtons.add(editButton);
        timetableButtons.add(displayButton);
        timetablePanel.add(timetableButtons);
    }

    /**
     * Create and returns panel that allow user to import session.
     * fallSessionFilePath instance attribute is also updated to the JLabel representing the current
     * file path imported for session.
     *
     * @return panel that allow user to import session
     */
    private JPanel createImportSessionPanel(){
        JPanel importSessionPanel = new JPanel();

        importSessionPanel.setLayout(new BoxLayout(importSessionPanel, BoxLayout.PAGE_AXIS));
        JPanel importFallSessionPanel = new JPanel();
        JPanel importWinterSessionPanel = new JPanel();

        // Import fall session button
        JButton importFallSession = new JButton("Import fall session");
        importFallSession.addActionListener(this);
        fallSessionFilePath = new JLabel("Choose the file... ");
        importFallSessionPanel.add(importFallSession);
        importFallSessionPanel.add(fallSessionFilePath);

        // Import winter session button
        JButton importWinterSession = new JButton("Import winter session");
        importWinterSession.addActionListener(this);
        winterSessionFilePath = new JLabel("Choose the file... ");
        importWinterSessionPanel.add(importWinterSession);
        importWinterSessionPanel.add(winterSessionFilePath);

        importSessionPanel.add(importFallSessionPanel);
        importSessionPanel.add(importWinterSessionPanel);

        return importSessionPanel;
    }

    /**
     * Change the screen of the frame to the given panel
     *
     * @param panel new screen
     */
    private void changeScreen(JPanel panel){
        this.setVisible(false);
        frame.getContentPane().removeAll();
        panel.setVisible(true);
        frame.add(panel);
        frame.revalidate();
        this.setVisible(true);
    }

    /**
     * Process the user's interaction with buttons by navigating to appropriate screen/window when
     * button is pressed by user.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Import timetable": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    timetableFilePath.setText(fileChooser.getSelectedFile().getAbsolutePath());
                    addTimetableButtons();
                }
                break;
            }
            case "Import fall session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    fallSessionFilePath.setText(importedSessionFilePath);
                    // Add SessionFileController
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "F");
                    } catch (IOException | ParseException | java.text.ParseException | InvalidSectionsException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            }
            case "Import winter session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    winterSessionFilePath.setText(importedSessionFilePath);
                    // Add SessionFileController
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "S");
                    } catch (IOException | ParseException | java.text.ParseException | InvalidSectionsException ex) {
                        System.out.println("hello");
                        throw new RuntimeException(ex);
                    }
                }
                break;
            }
            case "Generate timetable": {
                changeScreen(constraintsInputScreen);
                break;
            }
            case "Edit":
                changeScreen(editTimetableScreen);
                editTimetableScreen.updateTimetable();
                editTimetableScreen.setPreviousPanel(this);
                break;
            case "Display": {
                changeScreen(timetableUI);
                timetableUI.updateTimetable();
                break;
            }
        }
    }


    // TODO: Remove this
    public static void main(String[] args) {
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

        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(null, sectionFilterController);
        sectionFilterPresenter.setView(constraintsInputScreen);

        OverlapInputDialog overlapInputDialog = new OverlapInputDialog(new ArrayList<>(), sectionFilterController);

        DisplayTimetablePresenter displayTimetablePresenter2 = new DisplayTimetablePresenter();
        DisplayTimetableInteractor displayTimetableInteractor2 = new DisplayTimetableInteractor(displayTimetablePresenter2);
        DisplayTimetableController displayTimetableController2 = new DisplayTimetableController(displayTimetableInteractor2);

        displayTimetableInteractor2.setTimetable(timetable);

        TimetableGateway timetableGateway = new TimetableGateway();
        TimetableGatewayInteractor timetableGatewayInteractor = new TimetableGatewayInteractor(timetableGateway);
        TimetableFileController timetableFileController = new TimetableFileController(timetableGatewayInteractor);
        TimetableUI timetableUI = new TimetableUI(displayTimetableController2, editTimetableScreen, overlapInputDialog,
                timetableFileController);
        displayTimetablePresenter2.setView(timetableUI);

        displayTimetableInteractor1.setTimetable(timetable);

        SessionGateway gateway = new SessionGateway();
        SessionGatewayInteractor sessionGatewayInteractor = new SessionGatewayInteractor(gateway);
        SessionFileController sessionFileController = new SessionFileController(sessionGatewayInteractor);

        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editTimetableScreen, timetableUI, sessionFileController);
        timetableUI.setPrevPanel(mainUI);
        editTimetableScreen.setPreviousPanel(mainUI);


        mainUI.setPreferredSize(new Dimension(1280, 720));

        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
