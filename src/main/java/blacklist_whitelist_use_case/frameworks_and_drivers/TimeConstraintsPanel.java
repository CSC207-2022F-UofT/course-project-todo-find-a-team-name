package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * Screen Component of Constraints Input Screen that extends JPanel.
 * A panel for time constraints startTime and endTime input.
 */

public class TimeConstraintsPanel extends JPanel {
    final JLabel label;
    final JComboBox<String> selectList;
    final JLabel label1 = new JLabel("startTime");
    final JLabel label2 = new JLabel("endTime");
    final JComboBox<String> startTime;
    final JComboBox<String> endTime;

    public TimeConstraintsPanel(JLabel label, JComboBox<String> selectList, JComboBox<String> startTime, JComboBox<String> endTime) {
        this.label = label;
        this.add(label);
        this.selectList = selectList;
        this.add(selectList);
        this.add(label1);
        this.startTime= startTime;
        this.endTime = endTime;
        this.add(startTime);
        this.add(label2);
        this.add(endTime);
    }
}