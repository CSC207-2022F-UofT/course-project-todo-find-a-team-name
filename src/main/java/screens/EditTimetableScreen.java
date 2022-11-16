package screens;

import edit_timetable_use_case.EditTimetableController;
import edit_timetable_use_case.RemoveCourseFailedException;

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


    /**
     * @param controller
     */
    public EditTimetableScreen(EditTimetableController controller){
        this.controller = controller;

        JButton recommendBR = new JButton("Recommend BR courses");
        RecommendBRAction recommendBRAction = new RecommendBRAction(controller);
        recommendBR.setAction(recommendBRAction);

        JButton save = new JButton("Save");
        SaveTimetableAction saveTimetableAction = new SaveTimetableAction(________);
        save.setAction(saveTimetableAction);

        JButton addCourse = new JButton("Add a course");
        AddCourseAction addCourseAction = new AddCourseAction(controller);
        addCourse.setAction(addCourseAction);


        JPanel buttons = new JPanel();
        buttons.add(save);
        buttons.add(recommendBR);
        buttons.add(addCourse);

    }

    public void remove(String courseCode){
        try{
            this.controller.remove(courseCode).getMessage()
        }
        catch (RemoveCourseFailedException e){
            ...... e.getMessage() ......;
        }
    }

}
