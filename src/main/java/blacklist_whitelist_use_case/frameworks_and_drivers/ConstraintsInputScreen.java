package blacklist_whitelist_use_case.frameworks_and_drivers;

import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.application_business.SectionFilterRequestModel;
import blacklist_whitelist_use_case.interface_adapters.ISectionFilterView;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterViewModel;
import entities.Session;

import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Main Screen for BlackListWhiteList use case, where user enters course codes and constraints they want to apply.
 */
public class ConstraintsInputScreen extends JPanel implements ActionListener, ISectionFilterView {
    SectionFilterController sectionFilterController;
    private final String[] CONSTRAINT_LIST_TYPE = {"/", "BLACKLIST", "WHITELIST"};
    private final String[] TIME = {"8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00",
            "17:00", "18:00", "19:00", "20:00", "21:00", "22:00"};
    private final String[] SESSION = {"F", "S"};
    ArrayList<JRadioButton> radioButtonList = new ArrayList<>();
    JLabel instructorLabel = new JLabel("Prof Constraint");

    JLabel roomLabel = new JLabel("Room Constraint");
    JLabel timeLabel = new JLabel("Time Constraint");
    JLabel dayLabel = new JLabel("Date Constraint");
    JComboBox<String> sessionBtn = new JComboBox<>(SESSION);
    JComboBox<String> instructorBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    JComboBox<String> roomBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    JComboBox<String> timeBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    JComboBox<String> dayBtn = new JComboBox<>(CONSTRAINT_LIST_TYPE);
    JTextField instructorTextField = new JTextField(50);
    JTextField roomTextField = new JTextField(50);
    //    JTextField dayTextField = new JTextField(60);
    JTextField courseCodesTextField = new JTextField(60);
    JComboBox<String> startTime = new JComboBox<>(TIME);
    JComboBox<String> endTime = new JComboBox<>(TIME);
    JButton submit = new JButton("submit and filter");
    JButton help = new JButton("help");


    ConstraintsInputScreen(SectionFilterController controller) {
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
        this.setSize(400, 800);


        submit.addActionListener(this);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        LabelComboBoxTextField instructorInput = new LabelComboBoxTextField(instructorLabel, instructorBtn, instructorTextField);
        LabelComboBoxTextField roomInput = new LabelComboBoxTextField(roomLabel, roomBtn, roomTextField);
        DayConstraintsPanel dayInput = new DayConstraintsPanel(dayLabel, dayBtn, radioButtonList);
        TimeConstraintsPanel timeInput = new TimeConstraintsPanel(timeLabel, timeBtn, startTime, endTime);
        CourseCodePanel courseInput = new CourseCodePanel(sessionBtn , courseCodesTextField);

        JPanel buttons = new JPanel();
        buttons.add(submit);
        buttons.add(help);
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
     *main function that runs the Constraints Input Screen. For testing purpose.
     */
    public static void main(String[] args) throws IOException, ParseException {
        SessionGateway sessionGateway = new SessionGateway();
        Session fall;
        Session winter;
        try {
            fall = sessionGateway.readFromFile("src/main/resources/test_session_data.json", "F");
            winter = sessionGateway.readFromFile("src/main/resources/test_session_data.json", "S");
        } catch (ParseException | IOException e) {
            throw new RuntimeException(e);
        }

        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 400);
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel screens = new JPanel(cardLayout);
        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
        sectionFilterInterator.setFallSession(fall); //delete
        sectionFilterInterator.setWinterSession(winter); //delete
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);
        ConstraintsInputScreen c = new ConstraintsInputScreen(sectionFilterController1);
        sectionFilterPresenter.setView(c);
        screens.add(c, "hi");
        jFrame.add(screens);
        jFrame.setVisible(true);
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
            SectionFilterRequestModel requestModel = new SectionFilterRequestModel(
                    ((String)sessionBtn.getSelectedItem()),
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
            System.out.println("Enter: " + requestModel);
            sectionFilterController.filter(requestModel);
        }
        if (e.getSource() == help){
            JDialog helpDialogue = new HelpInstructionScreen();
            helpDialogue.setVisible(true);
        }
    }

    @Override
    public void showSuccessView(SectionFilterViewModel viewModel) {
        JFrame window = (JFrame) SwingUtilities.getWindowAncestor(this);
        JDialog dialog = new FilteredSectionsOutputScreen(window, viewModel);
        dialog.setVisible(true);
    }

    @Override
    public void showFailView(String message) {
        sessionBtn.setSelectedIndex(0);
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