package screens;

import edit_timetable_use_case.EditTimetableController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RemoveCourseAction extends AbstractAction {

    private String courseCode;
    private EditTimetableScreen screen;

    public RemoveCourseAction(String courseCode, EditTimetableScreen screen){
        this.courseCode = courseCode;
        this.screen = screen;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        screen.remove(courseCode);
    }
}
