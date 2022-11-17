package screens;

import edit_timetable_use_case.EditTimetableController;

import javax.swing.*;
import java.awt.event.ActionEvent;

public class RecommendBRAction extends AbstractAction {
    private final EditTimetableController controller;
    private final EditTimetableScreen screen;
    public RecommendBRAction(EditTimetableController controller, EditTimetableScreen screen) {
        this.controller = controller;
        this.screen = screen;
    }

    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
