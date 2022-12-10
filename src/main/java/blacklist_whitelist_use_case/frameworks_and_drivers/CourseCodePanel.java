package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * ConstraintsInputScreen screen component: a panel used for course codes input.
 */

public class CourseCodePanel extends JPanel{
    final JLabel courseLabel = new JLabel("Course Codes");
    final JTextField textField;

    public CourseCodePanel(JTextField textField) {
        this.add(courseLabel);
        this.textField = textField;
        this.add(textField);
    }
}