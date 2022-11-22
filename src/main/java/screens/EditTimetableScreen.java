package screens;

import edit_timetable_use_case.*;
import entities.*;
import recommend_br_use_case.IDummyTimetableGateway;
import recommend_br_use_case.RecommendBRInteractor;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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

    public EditTimetableScreen(JFrame frame, EditTimetableController controller) {
        this.frame = frame;
        this.controller = controller;

        JButton recommendBR = new JButton("Recommend BR Courses");
        recommendBR.addActionListener(this);

        JButton save = new JButton("Save");
        save.addActionListener(this);

        JButton addCourse = new JButton("Add Course");
        addCourse.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.add(save);
        buttons.add(recommendBR);
        buttons.add(addCourse);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buttons);

        this.setVisible(true);
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

        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));

        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);

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
            courses.add(c2);
            Timetable timetable = new Timetable(courses, "F");



            RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
            RemoveCourseInputBoundary removeInteractor = new RemoveCourseInteractor(removePresenter);
            removeInteractor.setTimetable(timetable);
            AddCourseOutputBoundary addPresenter = new AddCoursePresenter();
            AddCourseInputBoundary addInteractor = new AddCourseInteractor(addPresenter);
            addInteractor.setTimetable(timetable);
            addInteractor.setSession(new Session("S"));
            EditTimetableController controller = new EditTimetableController(removeInteractor, addInteractor);
            EditTimetableScreen screen = new EditTimetableScreen(frame, controller);
            RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, BRcontroller, controller);
            BRpresenter.setView(recommendBRWindow);
            screen.setBRWindow(recommendBRWindow);
            screen.updateTimetable(timetableViewModel);
            removePresenter.setView(screen);
            frame.add(screen);


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

    /*public void openAddCourseMenu() {

    }*/


    /*public void openEditCourseScreen(String courseCode){
        EditCourseScreen screen = new EditCourseScreen(this, courseCode);
        screen.setVisible(true);
        this.setVisible(false);
    }*/

    /** Processes button presses with appropriate function calls.
     * Pressing a "Remove [Course]" button calls the remove use case, pressing "Add Course" will begin the input
     * process for the Add Course use case. "Edit [Course]" will begin the input process for the Edit Course use case.
     * "Save" saves the timetable on the view, and "Recommend BR Courses" begins the Recommend BR use case.
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
        /*else if (e.getActionCommand().equals("Add Course")){
            openAddCourseMenu();
        }*/

        /*
        else if (cmd.startsWith("Edit ")){

        }
        else if (cmd.equals("Save")){

        }
        else if (cmd.equals("Recommend BR Courses")){

        }*/

    }

    /**
     * @param timetable Updates the view with the given TimetableViewModel.
     */
    @Override
    public void updateTimetable(TimetableViewModel timetable) {
        if (ttView != null){
            ttView.setVisible(false);
        }
        if (courseButtons != null){
            courseButtons.setVisible(false);
        }

        ttView = new TimetableView(1280, 720, timetable);
        this.add(ttView);

        courseButtons = new JPanel();
        for (TimetableViewCourseModel course : timetable.getCourseData()) {
            /*JButton editButton = new JButton("Edit " + course.getCode());
            editButton.addActionListener(this);
            courseButtons.add(editButton);*/

            JButton removeButton = new JButton("Remove " + course.getCode());
            removeButton.addActionListener(this);
            courseButtons.add(removeButton);
        }
        this.add(courseButtons);

        ttView.setVisible(true);
        courseButtons.setVisible(true);
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
}