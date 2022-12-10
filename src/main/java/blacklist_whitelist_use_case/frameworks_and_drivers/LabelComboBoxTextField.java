package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * Screen Component of Constraints Input Screen that extends Jpanel.
 * A panel used for user entered instructor or room constraint.
 */

public class LabelComboBoxTextField extends JPanel{

    final JLabel label;
    final JComboBox<String> jComboBox;
    final JTextField textField;

    public LabelComboBoxTextField(JLabel label, JComboBox<String> jComboBox, JTextField textField) {
        this.label = label;
        this.add(label);

        this.jComboBox = jComboBox;
        this.add(jComboBox);

        this.textField = textField;
        this.add(textField);


    }

}
