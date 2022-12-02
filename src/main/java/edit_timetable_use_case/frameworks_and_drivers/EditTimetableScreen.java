package edit_timetable_use_case.frameworks_and_drivers;

import edit_timetable_use_case.application_business.*;
import edit_timetable_use_case.interface_adapters.*;
import entities.*;
import recommend_br_use_case.IDummyTimetableGateway;
import recommend_br_use_case.RecommendBRInteractor;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import screens.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.HashMap;
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
public class EditTimetableScreen extends JPanel implements ActionListener, EditTimetableView {

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


    public EditTimetableScreen(JFrame frame, EditTimetableController controller, JPanel previousPanel,
                               UpdateTimetable) {
        this.frame = frame;
        this.controller = controller;
        this.previousPanel = previousPanel;
        this.updateTimetableController = updateTimetableController;

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


    /** A temporary method used to demonstrate RemoveCourse with minimal integration.
     */
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        IDummyTimetableGateway timetableGateway = timetableId -> new Timetable(new ArrayList<>(), "F");

        RecommendBRPresenter BRpresenter = new RecommendBRPresenter(null);
        RecommendBRInteractor BRinteractor = new RecommendBRInteractor(BRpresenter);
        RecommendBRController BRcontroller = new RecommendBRController(BRinteractor);

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

        //courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));

        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
        HashMap<String, TimetableViewCourseModel> calCourses = new HashMap<>();
        calCourses.put("CSC207H1", new TimetableViewCourseModel("CSC207H1", sectionModels2));
        calCourses.put("CSC236H1", new TimetableViewCourseModel("CSC236H1", sectionModels1));
        SessionViewModel sessionViewModel = new SessionViewModel(calCourses, "");


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

        try {
            TimetableCourse c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
            TimetableCourse c2 = new TimetableCourse("some other title", sections2, "", "CSC207H1", "");

            ArrayList<TimetableCourse> courses = new ArrayList<>();
            courses.add(c1);
            //courses.add(c2);
            Timetable timetable = new Timetable(courses, "F");

            CalendarCourse cc1 = new CalendarCourse("some title", sections1, "F", "CSC236H1", "");
            CalendarCourse cc2 = new CalendarCourse("some other title", sections2, "F", "CSC207H1", "");

            Session session = new Session("F");
            session.addCourse(cc1);
            session.addCourse(cc2);

            RetrieveTimetableInputBoundary retrieveInteractor = new RetrieveTimetableInteractor(timetable, session);

            RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
            RemoveCourseInputBoundary removeInteractor = new RemoveCourseInteractor(removePresenter);
            removeInteractor.setTimetable(timetable);
            removeInteractor.setRetrieveInteractor(retrieveInteractor);
            AddCourseOutputBoundary addPresenter = new AddCoursePresenter();
            AddCourseInputBoundary addInteractor = new AddCourseInteractor(addPresenter);
            addInteractor.setTimetable(timetable);
            addInteractor.setSession(session);
            addInteractor.setRetrieveInteractor(retrieveInteractor);
            EditCourseOutputBoundary editPresenter = new EditCoursePresenter();
            EditCourseInputBoundary editInteractor = new EditCourseInteractor(editPresenter);
            editInteractor.setTimetable(timetable);
            editInteractor.setSession(session);
            EditTimetableController controller = new EditTimetableController(removeInteractor, addInteractor, editInteractor);
            editInteractor.setRetrieveInteractor(retrieveInteractor);
            JPanel prevPanel = new JPanel();
            EditTimetableScreen screen = new EditTimetableScreen(frame, controller, prevPanel);
            RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, BRcontroller, controller);
            BRpresenter.setView(recommendBRWindow);
            screen.setBRWindow(recommendBRWindow);
            screen.updateTimetable(timetableViewModel);
            screen.updateSession(sessionViewModel);
            removePresenter.setView(screen);
            addPresenter.setView(screen);
            editPresenter.setView(screen);
            frame.add(screen);
            screen.setVisible(true);


        } catch (InvalidSectionsException e) {
            System.out.println("InvalidSectionsException thrown.");
        }

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
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
        else if (e.getActionCommand().equals("Add Course")){
            openCourseMenu();
        }
        else if (cmd.startsWith("Edit ")){
            openSectionsMenu(cmd.substring("Edit ".length()));
        }
        /*else if (cmd.equals("Save")){
        }*/
        else if(cmd.equals("<=")){
            this.setVisible(false);
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
     * @param session Updates the screen's session view model.
     */
    public void updateSession(SessionViewModel session) {
        this.session = session;
    }
    /**
     * @param successMessage A success message as determined by the presenter.
     *                       This method creates a message dialog with the given message.
     */
    @Override
    public void displayResponse(String successMessage) {
        JOptionPane.showMessageDialog(frame, successMessage);
    }

    /**
     * @param recommendBRWindow sets the RecommendBRWindow that is used when the "Recommend BR" button is pressed.
     */
    public void setBRWindow(RecommendBRWindow recommendBRWindow) {
        this.BRWindow = recommendBRWindow;
    }

    public void setPreviousPanel(JPanel previousPanel){
        this.previousPanel = previousPanel;
    }
}