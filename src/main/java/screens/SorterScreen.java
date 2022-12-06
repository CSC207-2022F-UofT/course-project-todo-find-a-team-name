package screens;

import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.interface_adapters.TimetableViewSectionModel;
import timetablesSorter_use_case.SorterInteractor;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class SorterScreen extends JPanel implements ActionListener {

    private final SorterController controller;
    private JFrame frame;
    private TimetableViewModel[] timetables;
    private final JRadioButton[] timeButtons;
    private final JRadioButton[] breakButtons;
    public SorterScreen(JFrame frame, SorterController controller, TimetableViewModel[] timetables) {
        this.controller = controller;
        this.timetables = timetables;

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

    @Override
    public void actionPerformed(ActionEvent e) {
        boolean bool = false;
        for (JRadioButton timeButton : timeButtons) {
            if (timeButton.isSelected()) {
                for (JRadioButton breakButton : breakButtons) {
                    if (breakButton.isSelected()) {
                        bool = true;
                        this.controller.preferenceSort(timeButton.getText(), breakButton.getText());
                    }
                }
            }
        }
        /**
         * TODO: prompt User to choose preferences if they submit without choosing
         */
        if (!bool){
            System.out.println("choose preferences");
        }

    }


    /**
     * THIS IS FOR TESTING PURPOSES ONLY
     */
    public static void main(String[] args) {
        java.util.List<TimetableViewCourseModel> courseData = new ArrayList<>();
        java.util.List<TimetableViewSectionModel> sectionModels1 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels1 = new ArrayList<>();
        blockModels1.add(new TimetableViewBlockModel(0, 11, 12));
        blockModels1.add(new TimetableViewBlockModel(4, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("LEC0101", blockModels1));

        java.util.List<TimetableViewBlockModel> blockModels2 = new ArrayList<>();
        blockModels2.add(new TimetableViewBlockModel(2, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("TUT0101", blockModels2));

        courseData.add(new TimetableViewCourseModel("CSC236H1", sectionModels1));


        java.util.List<TimetableViewSectionModel> sectionModels2 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels3 = new ArrayList<>();
        blockModels3.add(new TimetableViewBlockModel(1, 16, 17));
        blockModels3.add(new TimetableViewBlockModel(4, 16, 17));
        sectionModels2.add(new TimetableViewSectionModel("LEC0401", blockModels3));

        List<TimetableViewBlockModel> blockModels4 = new ArrayList<>();
        blockModels4.add(new TimetableViewBlockModel(0, 14, 16));
        sectionModels2.add(new TimetableViewSectionModel("TUT0301", blockModels4));

        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));

        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
        TimetableViewModel[] timetables = new TimetableViewModel[10];

        for (int i = 0; i < 10; i++) {
            timetables[i] = timetableViewModel;
        }

        JFrame frame = new JFrame();
        SorterPresenter presenter = new SorterPresenter();
        SorterInteractor interactor = new SorterInteractor(timetables, presenter);
        SorterController controller = new SorterController(interactor);
        SorterScreen screen = new SorterScreen(frame, controller, timetables);
        frame.add(screen);
        frame.pack();
        frame.setVisible(true);
    }
}
