package screens;

// TODO: Remove these imports (It's used for main)
import blacklist_whitelist_use_case.SectionFilterInteractor;
import edit_timetable_use_case.AddCourseInteractor;
import edit_timetable_use_case.RemoveCourseInteractor;
import entities.*;
import recommend_br_use_case.RecommendBRInteractor;


import edit_timetable_use_case.EditTimetableController;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

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

    private final JLabel filePathSession;
    private final JLabel filePathTimetable;

    private final ConstraintsInputScreen constraintsInputScreen;
    private final EditTimetableScreen editTimetableScreen;
    private final TimetableUI timetableUI;
    private final JFrame frame;

    /**
     * Constructs MainUI with title, import timetable/session buttons, and display/edit/generate timetable buttons.
     */
    public MainUI(JFrame frame, ConstraintsInputScreen constraintsInputScreen, EditTimetableScreen editTimetableScreen,
                  TimetableUI timetableUI){
        super();
        this.constraintsInputScreen = constraintsInputScreen;
        this.editTimetableScreen = editTimetableScreen;
        this.timetableUI = timetableUI;
        this.frame = frame;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Main menu");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        add(title, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

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

        JButton generateTimetable = new JButton("Generate timetable");
        generateTimetable.addActionListener(this);
        generateTimetable.setAlignmentX(JButton.CENTER_ALIGNMENT);


        panel.add(Box.createVerticalStrut(50));

        panel.add(timetablePanel);

        panel.add(Box.createVerticalStrut(50));

        panel.add(generateTimetable);

        centerPanel.add(panel);


        JPanel importSessionPanel = new JPanel();
        JButton importSession = new JButton("Import session");
        importSession.addActionListener(this);

        filePathSession = new JLabel("Choose the file...");
        importSessionPanel.add(importSession);
        importSessionPanel.add(filePathSession);

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
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
            case "Import session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    filePathSession.setText(fileChooser.getSelectedFile().getAbsolutePath());
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

    // TODO: Remove this
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter(null);
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter);
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
        recommendBRInteractor.setFSession(fSession);

        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);
        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(sectionFilterController1);
        sectionFilterPresenter.setView(constraintsInputScreen);

        TimetableUI timetableUI = new TimetableUI(new TimetableViewModel(new ArrayList<>()), editTimetableScreen);

        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editTimetableScreen, timetableUI);
        timetableUI.setPrevPanel(mainUI);
        mainUI.setPreferredSize(new Dimension(1280, 720));
        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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
