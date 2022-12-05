package screens;

// TODO: Remove these imports (It's used for main)
import blacklist_whitelist_use_case.SectionFilterInteractor;
import edit_timetable_use_case.AddCourseInteractor;
import edit_timetable_use_case.RemoveCourseInteractor;
import entities.*;


import edit_timetable_use_case.EditTimetableController;
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

import entities.InvalidSectionsException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Class used to display the main menu of this program that allow user to import files and navigates user to
 * other screens.
 * Instance Attributes:
 *      - filePathSession: JLabel representing the filepath of the session data that user imports.
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 *      - filePathTimetable: filepath of the timetable data user imports
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 */
public class MainUI extends JPanel implements ActionListener {

    private JLabel filePathSession;
    private JLabel filePathTimetable;
    private JLabel filePathSession2;
    public SessionFileController sessionController;

    private final ConstraintsInputScreen constraintsInputScreen;
    private final EditTimetableScreen editTimetableScreen;
    private final TimetableUI timetableUI;
    private final JFrame frame;

    /**
     * Constructs MainUI with title, import timetable/session buttons, and display/edit/generate timetable buttons.
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

        JPanel timetablePanel = createTimetablePanel();

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
     * Create and return panel that allow user to import, edit and display existing timetable.
     * filePathTimetable instance attribute is also updated to the JLabel representing the current
     * file path imported for timetable
     *
     * @return panel that allow user to import, edit and display existing timetable
     */
    private JPanel createTimetablePanel(){
        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.PAGE_AXIS));

        TitledBorder timetableBorder = BorderFactory.createTitledBorder("Existing Timetable Operations");
        timetableBorder.setTitleJustification(TitledBorder.CENTER);
        timetablePanel.setBorder(timetableBorder);
        JPanel importTimetablePanel = new JPanel();
        JButton importTimetable = new JButton("Import timetable");
        importTimetable.addActionListener(this);
        filePathTimetable = new JLabel("Choose the file...");
        importTimetablePanel.add(importTimetable);
        importTimetablePanel.add(filePathTimetable);

        timetablePanel.add(importTimetablePanel);

        JPanel timetableButtons = new JPanel();
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(this);

        timetableButtons.add(editButton);
        timetableButtons.add(displayButton);
        timetablePanel.add(timetableButtons);
        return timetablePanel;
    }

    /**
     * Create and returns panel that allow user to import session.
     * filePathSession instance attribute is also updated to the JLabel representing the current
     * file path imported for session.
     *
     * @return panel that allow user to import session
     */
    private JPanel createImportSessionPanel(){
        JPanel importSessionPanel = new JPanel();

        importSessionPanel.setLayout(new BoxLayout(importSessionPanel, BoxLayout.PAGE_AXIS));
        JPanel importFallSessionPanel = new JPanel();
        JPanel importWinterSessionPanel = new JPanel();

//        // Creates border for each session panel
//        TitledBorder sessionBorder = BorderFactory.createTitledBorder("");
//        importFallSessionPanel.setBorder(sessionBorder);
//        importWinterSessionPanel.setBorder(sessionBorder);

        // Import fall session button
        JButton importFallSession = new JButton("Import fall session");
        importFallSession.addActionListener(this);
        filePathSession = new JLabel("Choose the file... ");
        importFallSessionPanel.add(importFallSession);
        importFallSessionPanel.add(filePathSession);

        // Import winter session button
        JButton importWinterSession = new JButton("Import winter session");
        importWinterSession.addActionListener(this);
        filePathSession2 = new JLabel("Choose the file... ");
        importWinterSessionPanel.add(importWinterSession);
        importWinterSessionPanel.add(filePathSession2);

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
                    filePathTimetable.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
                break;
            }
            case "Import fall session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathSession.setText(importedSessionFilePath);
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
                    filePathSession2.setText(importedSessionFilePath);
                    // Add SessionFileController
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "S");
                    } catch (IOException | ParseException | java.text.ParseException | InvalidSectionsException ex) {
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
                break;
            case "Display": {
                changeScreen(timetableUI);
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

        SessionGateway sessionGateway = new SessionGateway();
        Session fSession;
        try {
            fSession = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");
        } catch (IOException | ParseException | InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
        addCourseInteractor.setSession(fSession);
        recommendBRInteractor.setFallSession(fSession);

        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInteractor);
        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(sectionFilterController1);
        sectionFilterPresenter.setView(constraintsInputScreen);

        TimetableUI timetableUI = new TimetableUI(new TimetableViewModel(new ArrayList<>()), editTimetableScreen);


        SessionGateway gateway = new SessionGateway();
        SessionGatewayInteractor sessionGatewayInteractor = new SessionGatewayInteractor(gateway);
        SessionFileController sessionFileController = new SessionFileController(sessionGatewayInteractor);

        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editTimetableScreen, timetableUI, sessionFileController);
        timetableUI.setPrevPanel(mainUI);
        mainUI.setPreferredSize(new Dimension(1280, 720));

        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
