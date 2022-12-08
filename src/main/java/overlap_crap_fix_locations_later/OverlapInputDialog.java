package overlap_crap_fix_locations_later;

import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import display_timetable_use_case.interface_adapters.TimetableUI;
import display_timetable_use_case.interface_adapters.TimetableView;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapInputEntry;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModelToModelConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class OverlapInputDialog extends JDialog implements Flow.Subscriber<Object>, OverlapInputEntry {


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> timeTableComboBox;
    private JLabel textLabel;
    private OverlapTimetableViewModel selectedMainTimetable;

    // TODO: May wish to find a better way of doing this. This is a placeholder.
    private HashMap<String, OverlapTimetableViewModel> timeTableRepresentations = new HashMap<>();
    private final OverlapMaxInputBoundary overlapMaxController;
    private ArrayList<OverlapTimetableViewModel> timeTableOptions;

    private final ConstraintsInputScreen hansInputScreen;
    private final TimetableUI timetableDisplay;
    private final TimetableView timetablePanel;

    private String selectedItemName;
    private Boolean waitingForNewData = false;

    /**
     * Generating the Dialog also serves as the entry point     for the Use Case. The dialog will call the controller
     * and interactor and such.
     */
    public OverlapInputDialog(ArrayList<OverlapTimetableViewModel> timeTableOptions,
                              TimetableUI timetableDisplay, TimetableView timetablePanel,
                              ConstraintsInputScreen hansInputScreen) {
        // This will be initialized later, when the controller subscribes to this InputDialog.
        this.overlapMaxController = null;
        for (int i = 0; i < timeTableOptions.size(); i++) {

            timeTableRepresentations.put("Timetable " + i, timeTableOptions.get(i));
        }
        this.timeTableOptions = timeTableOptions;
        this.timetableDisplay = timetableDisplay;
        this.timetablePanel = timetablePanel;
        this.hansInputScreen = hansInputScreen;
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

        // Assume the user has selected their target Timetable upon hitting the finish button, so ready that.
        System.out.println(timeTableComboBox.getSelectedItem());
        selectedItemName = (String) timeTableComboBox.getSelectedItem();

        // Run through Hans' use case to go through JD's and stuff like that for him to generate the data.
        callInHans();

        // Signal that we're ready to receive data (so the next packet we get should be ready!)
        waitingForNewData = true;


    }

    /**
     * A method to actually activate the calculations for the new, matched timeTable.
     * It's supposed to be called after Hans' thing is activated, and positioned to wait for a new data packet from it to arrive.
     */
    public void activateCalculations() {
        // Calculations activating = no longer waiting for data.
        waitingForNewData = false;

        // Collate the data post-update.
        ArrayList<OverlapTimetableViewModel> candidateTimetables = this.timeTableOptions;
        this.selectedMainTimetable = timeTableRepresentations.get(selectedMainTimetable);

        // Get the bloody timeTable.
        TimetableModel bestMatch = overlapMaxController.getBestMatchingTimetable(selectedMainTimetable, candidateTimetables);

        // Print it out for debugging.
        System.out.println(bestMatch);

        timetableDisplay.updateTimetable(TimetableModelConverter.timetableToView(bestMatch));
        timetableDisplay.setVisible(true);

    }

    public void updateTimetableOptions(ArrayList<OverlapTimetableViewModel> newTimetableOptions) {
        this.timeTableOptions = newTimetableOptions;
    }

    private void callInHans() {
        hansInputScreen.setVisible(true);
    }

    private void onCancel() {
        dispose();
    }

//    public static void main(String[] args) {
//        // Initialise a test value.
//        Block testBlock = new Block("Monday", "18:00", "21:00", "Castle Badr");
//        ArrayList<Block> testBlockList = new ArrayList<>();
//        testBlockList.add(testBlock);
//
//        Section testSection = new Section("LEC0101", "Mario", testBlockList);
//        ArrayList<Section> testSectionList = new ArrayList<>();
//        testSectionList.add(testSection);
//
//        try {
//            TimetableCourse testCourse = new TimetableCourse("C1", testSectionList, "S", "CLA215", "4");
//            ArrayList<TimetableCourse> testTimetableCourseList = new ArrayList<>();
//            testTimetableCourseList.add(testCourse);
//
//            Timetable testTimetable = new Timetable(testTimetableCourseList, "S");
//            ArrayList<Timetable> testTimetableList = new ArrayList<Timetable>();
//            testTimetableList.add(testTimetable);
//
//            SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
//            SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
//            SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);
//
//            SectionHoursInputBoundary sectionHoursCalculator = new CalculateSectionHoursInteractor();
//            TimetableMatchInputBoundary timeTableMatcher = new TimeTableMatchInteractor(sectionHoursCalculator);
//
//            ArrayList<OverlapTimetableViewModel> timetableViewModels = new ArrayList<>();
//            for (Timetable timetable : testTimetableList){
//                timetableViewModels.add(
//                        ModelToOverlapViewModelConverter.convertTimetableModel(
//                            EntityConverter.generateTimetableResponse(timetable))
//                );
//            }
//
//            OverlapInputDialog dialog = new OverlapInputDialog(timetableViewModels, sectionFilterController1);
//
//            OverlapMaxInputBoundary overlapMaxController = new OverlapMaximizationController(timeTableMatcher,
//                    dialog);
//
//            dialog.pack();
//            dialog.setVisible(true);
//
//        } catch (InvalidSectionsException e) {
//            throw new RuntimeException(e);
//        }
//    }

    /**
     * A method generated by the Swing Framework. Custom initialization of components should be handled in this method.
     */
    private void createUIComponents() {
        timeTableComboBox = initialiseComboBox();
    }

    // Custom initialize a ComboBox object and return it.
    private JComboBox initialiseComboBox() {
        JComboBox marCombo = new JComboBox(timeTableRepresentations.keySet().toArray());

        return marCombo;
    }

    /**
     * A helper ItemListener that lets me listen to when a ComboBox selected item is changed. :)
     **/

    private class ComboBoxItemChanged implements ItemListener {
        @Override
        public void itemStateChanged(ItemEvent e) {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                OverlapTimetableViewModel currentlySelectedTimetable = timeTableRepresentations
                        .get(timeTableComboBox.getSelectedItem());


                TimetableViewModel currentlySelectedTimetableViewModel = TimetableModelConverter.timetableToView(
                        OverlapTimetableViewModelToModelConverter.convertOverlapTimetableViewModelToModel(
                                currentlySelectedTimetable
                        ));

                timetablePanel.updateViewModel(currentlySelectedTimetableViewModel);
            }
        }
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
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }

    @Override
    public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels) {
        timeTableOptions = new ArrayList<>(viewModels);

        // Make the calculation part
        // only activate if a flag of currently calculating is true. Otherwise just take in the data. That way
        // we can kind of wait for JD's thing to finish.
        // If we're waiting on data, activate the calculations with this one.
        if (waitingForNewData) {
            activateCalculations();
        }
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
        contentPane.setLayout(new GridLayoutManager(2, 1, new Insets(10, 10, 10, 10), -1, -1));
        contentPane.setBorder(BorderFactory.createTitledBorder(null, "Overlap Input Dialog", TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        buttonOK.setText("OK");
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        buttonCancel.setText("Cancel");
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textLabel = new JLabel();
        textLabel.setText("Choose a timetable to produce an overlapping one for.");
        panel3.add(textLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        panel3.add(timeTableComboBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

    // TODO: Add an error if something goes really wrong and this has no timetable things.

}
