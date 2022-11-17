package screens;

import edit_timetable_use_case.EditTimetableController;

import javax.swing.*;

public class EditCourseScreen extends JPanel {

    private EditTimetableScreen screen;
    private String courseCode;

    public EditCourseScreen(EditTimetableScreen screen, , String courseCode){
        this.screen = screen;
        this.courseCode = courseCode;



        screen.setVisible(false);
    }
}
