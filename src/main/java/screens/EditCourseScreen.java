package screens;

import retrieve_timetable_use_case.RetrieveTimetableController;
import entities.Section;

import javax.swing.*;

public class EditCourseScreen extends JPanel {

    private EditTimetableScreen screen;
    private RetrieveTimetableController controller;
    private String courseCode;

    public EditCourseScreen(EditTimetableScreen screen, RetrieveTimetableController controller, String courseCode){
        this.screen = screen;
        this.courseCode = courseCode;
        this.controller = controller;

        CourseResponseModel calCourse = controller.retrieveCalendarCourse(courseCode);
        CourseResponseModel course = controller.retrieveTimetableCourse(courseCode);

        for (Section section : calCourse.getSections()){
            ... generate a box with data...

        }

        screen.setVisible(false);
        this.setVisible(true);

    }
}
