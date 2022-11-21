package screens;

import javax.swing.*;
import java.util.List;

/**
 * Screen Component of Constraints Input Screen that extends Jpanel.
 * A panel for day constraints which allows the user to select weekdays to apply constraints.
 */

public class DayConstraintsPanel extends JPanel {
    JLabel label;
    JComboBox<String> jComboBox;
    List<JRadioButton> jRadioButtons;

    DayConstraintsPanel(JLabel label, JComboBox<String> comboBox, List<JRadioButton> jRadioButtons){
        this.label = label;
        this.jComboBox = comboBox;
        this.jRadioButtons = jRadioButtons;
        this.add(label);
        this.add(comboBox);
        for (JRadioButton radioButton: jRadioButtons) {
            this.add(radioButton);
        }
    }
}
