package timetable_generator_use_case.frameworks_and_drivers;

import timetable_generator_use_case.interface_adapters.TimetableGeneratorController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;

public class GenerateTimetableScreen extends JPanel implements ActionListener {
    private HashMap<String, List<String>> modifiedCourses;
    private final TimetableGeneratorController controller;
    private final JButton generateButton;

    public GenerateTimetableScreen(TimetableGeneratorController controller){
        this.controller = controller;
        this.generateButton = new JButton("Generate TimeTable");
        setLayout(new GridBagLayout());
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.gridwidth = GridBagConstraints.REMAINDER;
        gridBagConstraints.anchor = GridBagConstraints.NORTH;
        this.add(generateButton, gridBagConstraints);
        generateButton.addActionListener(this);
    }

    public void setModifiedCourses(HashMap<String, List<String>> modifiedCourses) {
        this.modifiedCourses = modifiedCourses;
    }

    /**
     * Invoked when an action occurs.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == generateButton){
            controller.generateTimetable(modifiedCourses);
        }
    }
}
