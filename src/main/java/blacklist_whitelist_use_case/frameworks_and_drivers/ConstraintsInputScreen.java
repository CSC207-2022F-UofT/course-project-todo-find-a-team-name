package blacklist_whitelist_use_case.frameworks_and_drivers;

import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.application_business.SectionFilterRequestModel;
import blacklist_whitelist_use_case.interface_adapters.ISectionFilterView;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterViewModel;
import entities.Block;
import entities.Session;

import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

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

    public static void main(String[] args) throws IOException, ParseException {
//        java.util.List<Block> blocks1 = new ArrayList<>();
//        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
//        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
//        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
//
//        java.util.List<Block> blocks2 = new ArrayList<>();
//        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
//        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));
//
//        java.util.List<Block> blocks3 = new ArrayList<>();
//        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));
//
//        java.util.List<Block> blocks4 = new ArrayList<>();
//        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
//        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
//        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));
//
//        java.util.List<Block> blocks5 = new ArrayList<>();
//        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
//        blocks5.add(new Block("WE", "16:30", "18:00", "room3"));
//
//        java.util.List<Block> blocks6 = new ArrayList<>();
//        blocks6.add(new Block("FR", "11:30", "12:30", "room3"));
//        blocks6.add(new Block("FR", "11:30", "12:30", "room4"));
//
//        //Create demo section entities for testing.
//        Section section1 = new Section("LEC-0101", "inst1", blocks1);
//        Section section2 = new Section("TUT-0401", "inst2", blocks2);
//        Section section3 = new Section("PRA-0301", "inst3", blocks3);
//        Section section4 = new Section("LEC-0201", "inst4", blocks4);
//        Section section5 = new Section("TUT-0402", "inst5", blocks5);
//        Section section6 = new Section("LEC-0509", "inst6", blocks6);
//
//        java.util.List<Section> sections1 = new ArrayList<>();
//        sections1.add(section1);
//        sections1.add(section2);
//
//        java.util.List<Section> sections2 = new ArrayList<>();
//        sections2.add(section1);
//        sections2.add(section2);
//        sections2.add(section3);
//        sections2.add(section4);
//        sections2.add(section5);
//        sections2.add(section6);
//
//        java.util.List<Section> sections3 = new ArrayList<>();
//        sections3.add(section1);
//        sections3.add(section2);
//        sections3.add(section3);
//        sections3.add(section4);
//        sections3.add(section5);
//        sections3.add(section6);
//
//        java.util.List<Section> sections4 = new ArrayList<>();
//        sections4.add(section1);
//        sections4.add(section2);
//        sections4.add(section3);
//        sections4.add(section4);
//        sections4.add(section5);
//        sections4.add(section6);
//
//        List<Section> sections5 = new ArrayList<>();
//        sections5.add(section1);
//        sections5.add(section4);
//
//        //Create fall and winter Session that will be used to test Constraints Applier.
//
//        Session fall = new Session("F");
//        fall.addCourse(new CalendarCourse("CSC207", sections1, "F", "CSC207H1", "5"));
//        fall.addCourse(new CalendarCourse("CSC258", sections2, "F", "CSC258H1", "5"));
//        fall.addCourse(new CalendarCourse("MAT235", sections3, "F", "MAT235H1", "5"));
//        fall.addCourse(new CalendarCourse("CSC236", sections4, "F", "CSC236H1", "5"));
//        fall.addCourse(new CalendarCourse("STA247", sections5, "F", "STA247H1", "5"));
//
//        Session winter = new Session("S");
//        winter.addCourse(new CalendarCourse("CSC207", sections1, "S", "CSC207H1", "5"));
//        winter.addCourse(new CalendarCourse("CSC258", sections2, "S", "CSC258H1", "5"));
//        winter.addCourse(new CalendarCourse("MAT235", sections3, "S", "MAT235H1", "5"));
//        winter.addCourse(new CalendarCourse("CSC236", sections4, "S", "CSC236H1", "5"));
//        winter.addCourse(new CalendarCourse("STA247", sections5, "S", "STA247H1", "5"));
//        //delete
        SessionGateway sessionGateway = new SessionGateway();
        Session fall;
        Session winter;
        try {
            fall = sessionGateway.readFromFile("src/main/resources/test_session_data.json", "F");
            winter = sessionGateway.readFromFile("src/main/resources/test_session_data.json", "W");
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