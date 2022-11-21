package screens;

import javax.swing.*;

/**
 * ConstraintsInputScreen screen component: a panel used for course codes input.
 */

public class CourseCodePanel extends JPanel{

    JLabel sessionlabel = new JLabel("Session");
    JComboBox<String> comboBox;
    JLabel courseLabel = new JLabel("Course Codes");
    JTextField textField;

    public CourseCodePanel(JComboBox<String> comboBox, JTextField textField) {
        this.add(sessionlabel);
        this.comboBox = comboBox;
        this.add(comboBox);
        this.add(courseLabel);
        this.textField = textField;
        this.add(textField);
    }
}