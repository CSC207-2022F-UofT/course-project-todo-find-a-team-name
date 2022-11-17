package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class EditCourseAction extends Action {

    private EditTimetableScreen screen;
    private String courseCode;

    public EditCourseAction(EditTimetableScreen screen, String courseCode){
        this.screen = screen;
        this.courseCode = courseCode;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        screen.openEditCourseScreen(courseCode);
    }
}
