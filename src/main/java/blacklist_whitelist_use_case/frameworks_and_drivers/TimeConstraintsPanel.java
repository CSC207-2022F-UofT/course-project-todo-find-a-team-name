package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * Screen Component of Constraints Input Screen that extends Jpanel.
 * A panel for time constraints startTime and endTime input.
 */

public class TimeConstraintsPanel extends JPanel {
    JLabel label;
    JComboBox<String> selectList;
    JLabel label1 = new JLabel("startTime");
    JLabel label2 = new JLabel("endTime");
    JComboBox<String> startTime;
    JComboBox<String> endTime;

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