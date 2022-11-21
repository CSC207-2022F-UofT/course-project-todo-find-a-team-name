package screens;

import javax.swing.*;

/**
 * ConstraintsInputScreen UI component: a panel used for course codes input.
 */
public class CourseCodePanel extends JPanel{

    JLabel label;
    JTextField textField;

    public CourseCodePanel(JLabel label, JTextField textField) {
        this.label = label;
        this.add(label);
        this.textField = textField;
        this.add(textField);
    }
}