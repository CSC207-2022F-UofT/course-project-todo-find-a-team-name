package screens;

import edit_timetable_use_case.EditTimetableController;
import edit_timetable_use_case.RemoveCourseFailedException;
import edit_timetable_use_case.RemoveCourseInteractor;
import entities.*;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 *
 */
public class EditTimetableScreen extends JPanel implements ActionListener {

    private final EditTimetableController controller;
    private TimetableViewModel timetable;
    /*private SessionViewModel session;*/
    private JOptionPane optionPane;


    /**
     * @param controller
     */
    public EditTimetableScreen(EditTimetableController controller, TimetableViewModel timetable/*,
                               SessionViewModel session*/){
        this.controller = controller;
        this.timetable = timetable;
        /*this.session = session;*/

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

        TimetableView ttView = new TimetableView(1280, 720, timetable);
        this.add(ttView);

        JPanel courseButtons = new JPanel();
        for (TimetableViewCourseModel course : timetable.getCourseData()){
            JButton editButton = new JButton("Edit " + course.getCode());
            editButton.addActionListener(this);
            courseButtons.add(editButton);

            JButton removeButton = new JButton("Remove " + course.getCode());
            removeButton.addActionListener(this);
            courseButtons.add(removeButton);
        }
        courseButtons.setVisible(true);

        optionPane = new JOptionPane();
        this.add(optionPane);

        this.setVisible(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

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

        block1 = new Block("");

        List<Section> sections1 = new ArrayList<Section>();
        List<Section> sections2 = new ArrayList<Section>();
        c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
        c2 = new TimetableCourse("some other title", sections2, "", "CSC207H1", "");


        ArrayList<TimetableCourse> courses = new ArrayList<TimetableCourse>(c1, c2);
        courses.add(c1, c2);
        Timetable timetable = new Timetable(courses);

        RemoveCoursePresenter presenter = new RemoveCoursePresenter();
        RemoveCourseInteractor interactor = new RemoveCourseInteractor(timetable, presenter);
        EditTimetableController controller = new EditTimetableController(interactor);
        EditTimetableScreen screen = new EditTimetableScreen(controller, timetableViewModel);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

    public void remove(String courseCode){
        try{
            optionPane.createDialog(this.controller.remove(courseCode).getMessage());
        }
        catch (RemoveCourseFailedException e){
            optionPane.createDialog(e.getMessage());
        }
    }

    public void openAddCourseMenu(){

    }

    /*public void openEditCourseScreen(String courseCode){
        EditCourseScreen screen = new EditCourseScreen(this, courseCode);
        screen.setVisible(true);
        this.setVisible(false);
    }/*

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        if (cmd.startsWith("Remove ")){
            remove(cmd.substring("Remove ".length(), cmd.length() - 1));
        }/*
        else if (cmd.startsWith("Edit ")){

        }
        else if (cmd.equals("Save")){

        }
        else if (cmd.equals("Recommend BR Courses")){

        }
        else if (e.getActionCommand().equals("Add Course")){
            openAddCourseMenu();
        }*/
    }
}
