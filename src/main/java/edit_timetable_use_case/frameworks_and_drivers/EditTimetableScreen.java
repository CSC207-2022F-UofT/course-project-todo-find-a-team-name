package edit_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.frameworks_and_drivers.TimetableUI;
import display_timetable_use_case.frameworks_and_drivers.TimetableView;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewCourseModel;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.ITimetableUI;
import edit_timetable_use_case.application_business.NotInTimetableException;
import edit_timetable_use_case.application_business.RemoveCourseFailedException;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.EditTimetableView;
import entities.InvalidSectionsException;
import fileio_use_case.interface_adapters.SaveTimetableController;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import retrieve_timetable_use_case.application_business.RetrieveTimetableView;
import retrieve_timetable_use_case.interface_adapters.RetrieveTimetableController;
import screens.SessionViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;


/**
 * An implementation of the EditTimetableView in JSwing.
 * <p>
 * Frame refers to the frame that the screen is added to.
 * Controller refers to the EditTimetableController that processes user inputs.
 * ttView is the timetable view used to display the timetable.
 * courseButtons refer to buttons used to remove a given course.
 * BRWindow is the screen associated with the Recommend BR use case, and must be set before making this screen visible.
 */
public class EditTimetableScreen extends JPanel implements ActionListener, EditTimetableView, ITimetableUI,
        RetrieveTimetableView {

    private final JFrame frame;
    private final EditTimetableController controller;

    private TimetableView ttView;

    private JPanel courseButtons;
    private RecommendBRWindow BRWindow;

    private JPanel courseMenu;
    private JPanel editMenu;
    private SessionViewModel session;
    private TimetableViewModel timetable;
    private JPanel previousPanel;
    private final DisplayTimetableController displayTimetableController;
    private final RetrieveTimetableController retrieveTimetableController;
    private final SaveTimetableController saveController;


    public EditTimetableScreen(JFrame frame, EditTimetableController controller, JPanel previousPanel,
                               DisplayTimetableController displayTimetableController, RetrieveTimetableController
                               retrieveTimetableController, SaveTimetableController saveController) {
        this.frame = frame;
        this.controller = controller;
        this.previousPanel = previousPanel;
        this.displayTimetableController = displayTimetableController;
        this.retrieveTimetableController = retrieveTimetableController;
        this.saveController = saveController;

        this.ttView = null;
        this.courseMenu = null;
        this.editMenu = null;

        JButton recommendBR = new JButton("Recommend BR Courses");
        recommendBR.addActionListener(this);

        JButton save = new JButton("Save");
        save.addActionListener(this);

        JButton addCourse = new JButton("Add Course");
        addCourse.addActionListener(this);

        JButton goBack = new JButton("<=");
        goBack.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.add(save);
        buttons.add(recommendBR);
        buttons.add(addCourse);
        buttons.add(goBack);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buttons);
    }

    /** This method removes a course as indicated by the user pressing the "Remove [Course]" button, or displays
     * an error message if the course is not in the timetable.
     * @param courseCode The code of the course to be removed.
     */
    public void remove(String courseCode) {
        try {
            this.controller.remove(courseCode);
        } catch (RemoveCourseFailedException e) {
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    /**
     * @param courseCode the course code of the course being added.
     * @param sectionCodes the section codes of the sections that will be added to the course.
     */
    public void add(String courseCode, List<String> sectionCodes){
        try{
            controller.add(courseCode, sectionCodes);
        }
        catch (InvalidSectionsException e){
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    /**
     * @param courseCode the course code of the course to be edited. The course should already be
     *                   in the timetable.
     * @param sectionCodes the codes of the sections to be in the course. These sections should
     *                     already be in the session.
     */
    public void edit(String courseCode, List<String> sectionCodes){
        try{
            controller.edit(courseCode, sectionCodes);
        }
        catch (InvalidSectionsException | NotInTimetableException e){
            JOptionPane.showMessageDialog(frame, e.getMessage());
        }
    }

    /**
     * Removes the courseMenu if it exists and replaces it with a new one based on the current timetable and session,
     * then sends the display to the courseMenu.
     */
    public void openCourseMenu() {
        if (courseMenu != null){
            courseMenu.setVisible(false);
            frame.remove(courseMenu);
        }
        courseMenu = new AddCourseMenu(session, timetable, this, frame);
        frame.add(courseMenu);
        this.setVisible(false);
        courseMenu.setVisible(true);
    }

    public void openSectionsMenu(String courseCode){
        if (editMenu != null){
            editMenu.setVisible(false);
            frame.remove(editMenu);
        }
        editMenu = new AddSectionsMenu(session, timetable, this, courseCode);
        frame.add(editMenu);
        this.setVisible(false);
        editMenu.setVisible(true);
    }

    /** Processes button presses with appropriate function calls.
     * Pressing a "Remove [Course]" button calls the remove use case, pressing "Add Course" will begin the input
     * process for the Add Course use case. "Edit [Course]" will begin the input process for the Edit Course use case.
     * "Save" saves the timetable on the view, and "Recommend BR Courses" begins the Recommend BR use case.
     * "Add Course" opens an AddCourseMenu that will call the add course use case, given valid inputs.
     * @param e the event to be processed.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.startsWith("Remove ")) {
            remove(cmd.substring("Remove ".length()));
        }
        else if (cmd.equals("Recommend BR Courses")){
            BRWindow.showInputView();
        }
        else if (cmd.equals("Add Course")){
            if (session == null){
                JOptionPane.showMessageDialog(frame, "Load a session first.");
            }
            else{
                openCourseMenu();
            }
        }
        else if (cmd.startsWith("Edit ")){
            String courseCode = cmd.substring("Edit ".length());
            if (session == null){
                JOptionPane.showMessageDialog(frame, "Load a session first.");
            }
            else if (session.getCourses().get(courseCode) == null){
                JOptionPane.showMessageDialog(frame, "Course does not exist in session." +
                        " Load the timetable's session first.");
            }
            else{
                openSectionsMenu(courseCode);
            }
        }
        else if (cmd.equals("Save")){
            saveController.saveTimetable();
            this.setVisible(false);
            frame.add(previousPanel);
            previousPanel.setVisible(true);
        }
        else if(cmd.equals("<=")){
            this.setVisible(false);
            frame.add(previousPanel);
            if (previousPanel instanceof TimetableUI){
                ((TimetableUI) previousPanel).updateTimetable();
            }
            previousPanel.setVisible(true);
        }

    }

    /**
     * @param timetable Updates the view with the given TimetableViewModel.
     */
    @Override
    public void updateTimetable(TimetableViewModel timetable) {
        this.timetable = timetable;
        if (ttView == null){
            ttView = new TimetableView(1280, 720, timetable);
            this.add(ttView);
        }
        if (courseButtons != null){
            courseButtons.setVisible(false);
        }

        ttView.setVisible(false);
        ttView.updateViewModel(timetable);

        if (courseButtons != null){
            courseButtons.setVisible(false);
        }


        courseButtons = new JPanel();
        for (TimetableViewCourseModel course : timetable.getCourseData()) {
            JButton editButton = new JButton("Edit " + course.getCode());
            editButton.addActionListener(this);
            courseButtons.add(editButton);

            JButton removeButton = new JButton("Remove " + course.getCode());
            removeButton.addActionListener(this);
            courseButtons.add(removeButton);
        }
        this.add(courseButtons);

        ttView.setVisible(true);
        courseButtons.setVisible(true);
    }

    /**
     * @param message A message to be displayed to the user when a failure occurs.
     */
    @Override
    public void showTimetableFailView(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }


    /**
     * @param successMessage A success message as determined by the presenter.
     *                       This method creates a message dialog with the given message.
     */
    @Override
    public void displayResponse(String successMessage) {
        JOptionPane.showMessageDialog(frame, successMessage);
    }

    public void setBRWindow(RecommendBRWindow recommendBRWindow) {
        this.BRWindow = recommendBRWindow;
    }

    public void setPreviousPanel(JPanel previousPanel){
        this.previousPanel = previousPanel;
    }

    public void updateTimetable(){
        displayTimetableController.displayTimetable();
    }

    public void updateSession(){
        retrieveTimetableController.updateSession();
    }

    /**
     * @param sessionViewModel The current session's view model.
     */
    @Override
    public void updateSession(SessionViewModel sessionViewModel) {
        this.session = sessionViewModel;
    }
}