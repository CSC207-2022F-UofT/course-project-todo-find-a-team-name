package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * ConstraintsInputScreen screen component: a panel used for course codes input.
 */

public class CourseCodePanel extends JPanel{

    JLabel sessionLabel = new JLabel("Session");
    JComboBox<String> comboBox;
    JLabel courseLabel = new JLabel("Course Codes");
    JTextField textField;

    public CourseCodePanel(JComboBox<String> comboBox, JTextField textField) {
        this.add(sessionLabel);
        this.comboBox = comboBox;
        this.add(comboBox);
        this.add(courseLabel);
        this.textField = textField;
        this.add(textField);
    }
}