package timetables_sort_use_case.frameworks_and_drivers;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A menu used to display the display preferences for the user to pick from.
 * Instance variables:
 * - allTimetablesScreen is the allTimetablesScreen that made this Menu
 * - JFrame the frame of this window
 * - timeButtons an array of radio buttons for the user to choose their time preference
 * - breakButtons an array of radio buttons for the user to choose their break preference
 */
public class TimetablesSortMenu extends JPanel implements ActionListener {

    private final AllTimetablesScreen allTimetablesScreen;
    private final JFrame frame;
    private final JRadioButton[] timeButtons;
    private final JRadioButton[] breakButtons;
    public TimetablesSortMenu(JFrame frame, AllTimetablesScreen allTimetablesScreen) {

        this.frame = frame;
        this.allTimetablesScreen = allTimetablesScreen;

        this.setLayout(new GridLayout(0, 1));

        JPanel header = new JPanel();
        JLabel title = new JLabel("Preferences");
        title.setFont(new Font("Serif", Font.PLAIN, 20));
        header.add(title);
        this.add(header);


        JPanel timePanel = new JPanel();
        timePanel.setLayout(new BorderLayout());
        timePanel.add(new JLabel("Preferred time for courses:"), BorderLayout.PAGE_START);

        JRadioButton morning = new JRadioButton("morning");
        JRadioButton afternoon = new JRadioButton("afternoon");
        JRadioButton evening = new JRadioButton("evening");
        JRadioButton dontCare = new JRadioButton("don't care");

        this.timeButtons = new JRadioButton[] {morning, afternoon, evening, dontCare};

        JPanel timeButtonPanel = new JPanel();
        ButtonGroup timeButtons = new ButtonGroup();

        timeButtons.add(morning);
        timeButtons.add(afternoon);
        timeButtons.add(evening);
        timeButtons.add(dontCare);

        timeButtonPanel.add(morning);
        timeButtonPanel.add(afternoon);
        timeButtonPanel.add(evening);
        timeButtonPanel.add(dontCare);

        timePanel.add(timeButtonPanel, BorderLayout.CENTER);
        this.add(timePanel);

        JPanel breakPanel = new JPanel();
        breakPanel.setLayout(new BorderLayout());
        breakPanel.add(new JLabel("Preferred break format: (all timetables that satisfy this will show up on top)"),
                BorderLayout.PAGE_START);

        JRadioButton hour = new JRadioButton("breaks (try to have at least an hour of break between classes)");
        JRadioButton commuter = new JRadioButton("commuter (shove everything into as few days as possible)");
        JRadioButton dontCare2 = new JRadioButton("don't care");

        this.breakButtons = new JRadioButton[] {hour, commuter, dontCare2};

        JPanel breakButtonPanel = new JPanel();
        ButtonGroup breakButtons = new ButtonGroup();

        breakButtons.add(hour);
        breakButtons.add(commuter);
        breakButtons.add(dontCare2);

        breakButtonPanel.add(hour);
        breakButtonPanel.add(commuter);
        breakButtonPanel.add(dontCare2);

        breakPanel.add(breakButtonPanel, BorderLayout.CENTER);
        this.add(breakPanel);

        JButton submit = new JButton("Submit Input");
        submit.addActionListener(this);
        JPanel submitWrapper = new JPanel();
        submitWrapper.add(submit);

        this.add(submitWrapper, BorderLayout.PAGE_END);

    }

    /**
     * Finds which buttons were pressed and pass them to allTimetablesScreen
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

        boolean noInputs = true;
        for (JRadioButton timeButton : timeButtons) {
            if (timeButton.isSelected()) {
                for (JRadioButton breakButton : breakButtons) {
                    if (breakButton.isSelected()) {
                        noInputs = false;
                        frame.getContentPane().removeAll();
                        allTimetablesScreen.timetablesSort(timeButton.getText(), breakButton.getText());
                        frame.add(allTimetablesScreen);
                        frame.setSize(1280, 720);
                        frame.revalidate();
                    }
                }
            }
        }

        if(noInputs){
            JOptionPane.showMessageDialog(frame, "Please choose your preferences");
        }

    }
}
