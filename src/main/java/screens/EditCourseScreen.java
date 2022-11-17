package screens;

import edit_timetable_use_case.EditTimetableController;
import entities.Section;

import javax.swing.*;

public class EditCourseScreen extends JPanel {

    private EditTimetableScreen screen;
    private Controller controller;
    private String courseCode;

    public EditCourseScreen(EditTimetableScreen screen, ModuleLayer.Controller controller, String courseCode){
        this.screen = screen;
        this.courseCode = courseCode;
        this.controller = controller;

        CourseResponseModel calCourse = controller.retrieveCalendarCourseData(courseCode);
        CourseResponseModel course = controller.retrieveTimetableCourseData(courseCode);

        for (Section section : calCourse.getSections()){
            ... generate a box with data...

        }

        screen.setVisible(false);
    }
}
