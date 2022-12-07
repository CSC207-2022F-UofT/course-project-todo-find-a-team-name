package overlap_crap_fix_locations_later;

import blacklist_whitelist_use_case.SectionFilterInteractor;
import display_timetable_use_case.interface_adapters.TimetableUI;
import display_timetable_use_case.interface_adapters.TimetableView;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import entities.*;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.SectionHoursInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import screens.ConstraintsInputScreen;
import screens.SectionFilterController;
import screens.SectionFilterPresenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.Flow;

public class OverlapInputDialog extends JDialog implements Flow.Publisher {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox timeTableComboBox;
    private JLabel textLabel;
    private OverlapTimetableViewModel selectedMainTimetable;

    private SectionFilterController sectionFilterController;

    // TODO: May wish to find a better way of doing this. This is a placeholder.
    private HashMap<String, OverlapTimetableViewModel> timeTableRepresentations = new HashMap<>();
    private ArrayList<Constraint> selectedConstraints;
    private ArrayList<Flow.Subscriber> dataReceivers = new ArrayList<>();
    private final OverlapMaxInputBoundary overlapMaxController;
    private ArrayList<OverlapTimetableViewModel> timeTableOptions;

    private final TimetableUI timetableDisplay;
    private final TimetableView timetablePanel;

    /**
     * Generating the Dialog also serves as the entry point for the Use Case. The dialog will call the controller
     * and interactor and such.
     */
    public OverlapInputDialog(ArrayList<OverlapTimetableViewModel> timeTableOptions,
                              SectionFilterController sectionFilterController,
                              TimetableUI timetableDisplay, TimetableView timetablePanel) {
        // This will be initialized later, when the controller subscribes to this InputDialog.
        this.overlapMaxController = null;
        this.sectionFilterController = sectionFilterController;
        for (int i = 0; i < timeTableOptions.size(); i++) {

            timeTableRepresentations.put("Timetable " + i, timeTableOptions.get(i));
        }
        this.timeTableOptions = timeTableOptions;
        this.timetableDisplay = timetableDisplay;
        this.timetablePanel = timetablePanel;
        $$$setupUI$$$();
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setUpCancelFunctionality();
        setUpInputPassing();
    }

    /**
     * Method through which the Dialog stores the entered main timeTable and begins receiving a set of constraints.
     **/
    private void finishDataEntry() {

        callInHans();

        // Get the extras from JD:

        // Bundle the selected main timetable, and the candidate timetables, to send to the subscribers
        // Notably, the TimeTableMatchInteractor
        System.out.println(timeTableComboBox.getSelectedItem());
        String selectedItemName = (String) timeTableComboBox.getSelectedItem();

        // TODO: Make this actually get data once I can integrate, from JD's case.
        ArrayList<OverlapTimetableViewModel> candidateTimetables = new ArrayList<>();

        // Use a makeshift bundle via a map. (It's a pity Android.Bundle isn't native to Java).
        HashMap<OverlapInputDialogDataKeys, Object> dataBundle = new HashMap<>();
        this.selectedMainTimetable = timeTableRepresentations.get(selectedItemName);

        dataBundle.put(OverlapInputDialogDataKeys.mainTable, this.selectedMainTimetable);
        dataBundle.put(OverlapInputDialogDataKeys.candidateTimetables, candidateTimetables);

        // Pass our makeshift bundles to the subscribers.
        for (Flow.Subscriber subscriber : dataReceivers) {
            subscriber.onNext(dataBundle);
            // This Dialog should only create data one time. So destroy the thing after.
            subscriber.onComplete();
        }

        // Do something with this!
        TimetableModel bestMatch = overlapMaxController.getBestMatchingTimetable(selectedMainTimetable, candidateTimetables);
        timetableDisplay.updateTimetable(TimetableModelConverter.timetableToView(bestMatch));
        timetableDisplay.setVisible(true);

    }

    public void updateTimetableOptions(ArrayList<OverlapTimetableViewModel> newTimetableOptions) {
        this.timeTableOptions = newTimetableOptions;
    }

    private void callInHans() {
        /* This is mostly taken from Hans' use case to get his to start. **/
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 400);
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel screens = new JPanel(cardLayout);
        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);
        ConstraintsInputScreen c = new ConstraintsInputScreen(sectionFilterController1);
        sectionFilterPresenter.setView(c);
        screens.add(c, "hi");
        jFrame.add(screens);

        jFrame.setVisible(true);
    }

    private void onCancel() {
        dispose();
    }

    public static void main(String[] args) {
        // Initialise a test value.
        Block testBlock = new Block("Monday", "18:00", "21:00", "Castle Badr");
        ArrayList<Block> testBlockList = new ArrayList<>();
        testBlockList.add(testBlock);

        Section testSection = new Section("LEC0101", "Mario", testBlockList);
        ArrayList<Section> testSectionList = new ArrayList<>();
        testSectionList.add(testSection);

        try {
            TimetableCourse testCourse = new TimetableCourse("C1", testSectionList, "S", "CLA215", "4");
            ArrayList<TimetableCourse> testTimetableCourseList = new ArrayList<>();
            testTimetableCourseList.add(testCourse);

            Timetable testTimetable = new Timetable(testTimetableCourseList, "S");
            ArrayList<Timetable> testTimetableList = new ArrayList<Timetable>();
            testTimetableList.add(testTimetable);

            SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
            SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
            SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);

            SectionHoursInputBoundary sectionHoursCalculator = new CalculateSectionHoursInteractor();
            TimetableMatchInputBoundary timeTableMatcher = new TimeTableMatchInteractor(sectionHoursCalculator);


            OverlapInputDialog dialog = new OverlapInputDialog(testTimetableList, sectionFilterController1);

            OverlapMaxInputBoundary overlapMaxController = new OverlapMaximizationController(timeTableMatcher,
                    dialog);

            dialog.pack();
            dialog.setVisible(true);

        } catch (InvalidSectionsException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * A method generated by the Swing Framework. Custom initialization of components should be handled in this method.
     */
    private void createUIComponents() {
        timeTableComboBox = initialiseComboBox();
    }

    // Custom initialize a ComboBox object and return it.
    private JComboBox initialiseComboBox() {
        JComboBox marCombo = new JComboBox(timeTableRepresentations.keySet().toArray());
        // TODO: Clean this up?
        marCombo.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange() == ItemEvent.SELECTED) {
                    Timetable currentlySelectedTimetable = timeTableRepresentations.get(timeTableComboBox.getSelectedItem());
                    TimetableViewModel currentlySelectedTimetableViewModel = TimetableModelConverter.timetableToView(
                            OverlapMaximizationController.convertTimetableToModel(currentlySelectedTimetable));
                    timetablePanel.updateViewModel(currentlySelectedTimetableViewModel);
                }
            }
        });

        return marCombo;
    }

    // TODO: Add a method that will let me

    public void setUpCancelFunctionality() {
        buttonCancel.addActionListener(new ActionListener() {
            /**
             * Add an action listener for the cancel dialogue button that kills the dialog.
             **/
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });
    }

    public void setUpInputPassing() {
        buttonOK.addActionListener(new ActionListener() {
            /** Add an action listener for the OK button. */
            public void actionPerformed(ActionEvent e) {
                finishDataEntry();
                dispose();
            }
        });
    }

    @Override
    public void subscribe(Flow.Subscriber subscriber) {
        // Keep a reference of it. I guess.
        System.out.println(dataReceivers);
        dataReceivers.add(subscriber);
        System.out.println(dataReceivers);
    }


    /**
     * Method generated by IntelliJ IDEA GUI Designer
     * >>> IMPORTANT!! <<<
     * DO NOT edit this method OR call it in your code!
     *
     * @noinspection ALL
     */
    private void $$$setupUI$$$() {
        createUIComponents();
        contentPane = new JPanel();
        contentPane.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBorder(BorderFactory.createTitledBorder(null, "Overlap Input Dialog", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer1 = new com.intellij.uiDesigner.core.Spacer();
        panel1.add(spacer1, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new com.intellij.uiDesigner.core.GridConstraints(0, 1, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new com.intellij.uiDesigner.core.GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_BOTH, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_SHRINK | com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textLabel = new JLabel();
        textLabel.setText("Choose a timetable to produce an overlapping one for.");
        panel3.add(textLabel, new com.intellij.uiDesigner.core.GridConstraints(0, 0, 1, 2, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_NONE, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer2 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer2, new com.intellij.uiDesigner.core.GridConstraints(1, 1, 2, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel3.add(timeTableComboBox, new com.intellij.uiDesigner.core.GridConstraints(2, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_WEST, com.intellij.uiDesigner.core.GridConstraints.FILL_HORIZONTAL, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_CAN_GROW, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer3 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer3, new com.intellij.uiDesigner.core.GridConstraints(1, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final com.intellij.uiDesigner.core.Spacer spacer4 = new com.intellij.uiDesigner.core.Spacer();
        panel3.add(spacer4, new com.intellij.uiDesigner.core.GridConstraints(3, 0, 1, 1, com.intellij.uiDesigner.core.GridConstraints.ANCHOR_CENTER, com.intellij.uiDesigner.core.GridConstraints.FILL_VERTICAL, 1, com.intellij.uiDesigner.core.GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    // TODO: Add an error if something goes really wrong and this has no timetable things.

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
