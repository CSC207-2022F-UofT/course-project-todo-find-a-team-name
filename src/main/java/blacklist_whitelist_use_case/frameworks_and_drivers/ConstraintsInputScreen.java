package blacklist_whitelist_use_case.frameworks_and_drivers;
import blacklist_whitelist_use_case.interface_adapters.ISectionFilterView;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterViewModel;
import timetable_generator_use_case.frameworks_and_drivers.GenerateTimetableScreen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Main Screen for BlackListWhiteList use case, where user enters course codes and constraints they want to apply.
 */
public class ConstraintsInputScreen extends JPanel implements ActionListener, ISectionFilterView {
    private JPanel prevPanel = null;
    private final JButton prev = new JButton("<-");
    private final GenerateTimetableScreen generateTimeTableScreen;
    private final SectionFilterController sectionFilterController;
    private final String[] CONSTRAINT_LIST_TYPE = {"/", "BLACKLIST", "WHITELIST"};
    private final String[] TIME = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
    private final ArrayList<JRadioButton> radioButtonList = new ArrayList<>();
    private final JComboBox<String> instructorBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    private final JComboBox<String> roomBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    private final JComboBox<String> timeBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    private final JComboBox<String> dayBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    private final JTextField instructorTextField = new JTextField(50);
    private final JTextField roomTextField = new JTextField(50);
    //    JTextField dayTextField = new JTextField(60);
    private final JTextField courseCodesTextField = new JTextField(60);
    private final JComboBox<String> startTime = new JComboBox<>(TIME);
    private final JComboBox<String> endTime = new JComboBox<>(TIME);
    private final JButton submit = new JButton("submit and filter");
    private final JButton help = new JButton("help");

    public ConstraintsInputScreen(GenerateTimetableScreen generateTimeTableScreen, SectionFilterController controller) {
        this.generateTimeTableScreen = generateTimeTableScreen;
        JRadioButton radioButton = new JRadioButton("MO");
        JRadioButton radioButton1 = new JRadioButton("TU");
        JRadioButton radioButton2 = new JRadioButton("WE");
        JRadioButton radioButton3 = new JRadioButton("TH");
        JRadioButton radioButton4 = new JRadioButton("FR");
        radioButtonList.add(radioButton);
        radioButtonList.add(radioButton1);
        radioButtonList.add(radioButton2);
        radioButtonList.add(radioButton3);
        radioButtonList.add(radioButton4);


        timeBtn.addActionListener(this);
        dayBtn.addActionListener(this);
        instructorBtn.addActionListener(this);
        this.sectionFilterController = controller;

        JLabel title = new JLabel("ConstraintsInput Screen");
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 18));
        title.setHorizontalAlignment(JLabel.CENTER);


        submit.addActionListener(this);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JLabel instructorLabel = new JLabel("Prof Constraint");
        LabelComboBoxTextField instructorInput = new LabelComboBoxTextField(instructorLabel, instructorBtn, instructorTextField);
        JLabel roomLabel = new JLabel("Room Constraint");
        LabelComboBoxTextField roomInput = new LabelComboBoxTextField(roomLabel, roomBtn, roomTextField);
        JLabel dayLabel = new JLabel("Date Constraint");
        DayConstraintsPanel dayInput = new DayConstraintsPanel(dayLabel, dayBtn, radioButtonList);
        JLabel timeLabel = new JLabel("Time Constraint");
        TimeConstraintsPanel timeInput = new TimeConstraintsPanel(timeLabel, timeBtn, startTime, endTime);
        CourseCodePanel courseInput = new CourseCodePanel(courseCodesTextField);

        JPanel buttons = new JPanel();
        buttons.add(submit);
        buttons.add(help);
        buttons.add(prev);
        prev.addActionListener(this);
        help.addActionListener(this);
        this.add(title, BorderLayout.PAGE_START);
        panel.add(courseInput);
        panel.add(instructorInput);
        panel.add(roomInput);
        panel.add(dayInput);
        panel.add(timeInput);
        this.add(panel, BorderLayout.CENTER);
        this.add(buttons, BorderLayout.PAGE_END);

    }

    /**
     * Set previous panel of this panel to the given panel
     *
     * @param prevPanel new panel set to previous panel
     */
    public void setPrevPanel(JPanel prevPanel) {
        this.prevPanel = prevPanel;
    }

    /**
     * Change the screen of the frame to the given panel
     *
     * @param panel new screen
     */
    private void changeScreen(JPanel panel){
        JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
        this.setVisible(false);
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        this.setVisible(true);
    }


    /**
     * Invoked when an action occurs.
     *
     * @param e ActionEvent
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            ArrayList<Integer> dayArrayList = new ArrayList<>();
            for (int i = 0; i < radioButtonList.size(); i++) {
                if (radioButtonList.get(i).isSelected()) {
                    dayArrayList.add(i);
                }
            }
            sectionFilterController.filter(
                    courseCodesTextField.getText(),
                    ((String) instructorBtn.getSelectedItem()),
                    ((String) roomBtn.getSelectedItem()),
                    ((String) dayBtn.getSelectedItem()),
                    ((String) timeBtn.getSelectedItem()),
                    instructorTextField.getText(),
                    roomTextField.getText(),
                    dayArrayList,
                    ((String) startTime.getSelectedItem()),
                    ((String) endTime.getSelectedItem())
            );
        }
        if (e.getSource() == help){
            JDialog helpDialogue = new HelpInstructionScreen();
            helpDialogue.setVisible(true);
        }
        if (e.getSource() == prev){
            changeScreen(prevPanel);
        }

    }

    /**
     * Display the data stored inside the viewModel to the user
     * @param viewModel a data structure that stores the information to be displayed to the user.
     */
    @Override
    public void showSuccessView(SectionFilterViewModel viewModel) {
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new FilteredSectionsOutputScreen(generateTimeTableScreen, window, viewModel);
        dialog.setVisible(true);
    }

    /**
     * Display the error message to inform the user of a potential error in their input.
     * @param message an error message telling the user the reason their input is not valid.
     */
    @Override
    public void showFailView(String message) {
        roomBtn.setSelectedIndex(0);
        timeBtn.setSelectedIndex(0);
        dayBtn.setSelectedIndex(0);
        instructorBtn.setSelectedIndex(0);
        startTime.setSelectedIndex(0);
        endTime.setSelectedIndex(0);
        courseCodesTextField.setText("");
        instructorTextField.setText("");
        roomTextField.setText("");
        for (JRadioButton radiobutton: radioButtonList) {
            radiobutton.setSelected(false);
        }
        JOptionPane.showMessageDialog(this, message);
    }
}