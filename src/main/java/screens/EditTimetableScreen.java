package screens;

import edit_timetable_use_case.EditTimetableController;
import edit_timetable_use_case.RemoveCourseFailedException;
import entities.Session;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;


/**
 *
 */
public class EditTimetableScreen extends JPanel {

    private final EditTimetableController controller;
    private Timetable timetable;
    private Session session;
    private JOptionPane optionPane;


    /**
     * @param controller
     */
    public EditTimetableScreen(EditTimetableController controller){
        this.controller = controller;

        /*JButton recommendBR = new JButton("Recommend BR courses");
        recommendBR.setAction(new RecommendBRAction(...));

        JButton save = new JButton("Save");
        save.setAction(new SaveTimetableAction(...));

        JButton addCourse = new JButton("Add a course");
        addCourse.setAction(new AddCourseAction());*/

        optionPane = new JOptionPane();

        JPanel buttons = new JPanel();
        /*buttons.add(save);
        buttons.add(recommendBR);
        buttons.add(addCourse);*/

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(buttons);
        /*this.add(timetableViewPanel);*/
        this.add(optionPane);

        this.setVisible(true);
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
        new EditCourseScreen(this, courseCode);
    }
    */
}
