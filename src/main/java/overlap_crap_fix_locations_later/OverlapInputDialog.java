package overlap_crap_fix_locations_later;

import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import display_timetable_use_case.interface_adapters.TimetableView;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import entities.*;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapInputEntryViewModel;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.ViewModels.ModelToOverlapViewModelConverter;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModelToModelConverter;
import overlap_crap_fix_locations_later.presenters.OverlapMaxPresenter;
import retrieve_timetable_use_case.application_business.EntityConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;
import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;
import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
import timetable_generator_use_case.frameworks_and_drivers.GenerateTimetableScreen;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorController;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class OverlapInputDialog extends JDialog implements Flow.Subscriber<Object>, OverlapInputEntryViewModel {


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> timeTableComboBox;
    private JLabel textLabel;
    private OverlapTimetableViewModel selectedMainTimetable;

    private HashMap<String, OverlapTimetableViewModel> timeTableRepresentations = new HashMap<>();
    private final OverlapMaxInputBoundary overlapMaxController;
    private ArrayList<OverlapTimetableViewModel> timeTableOptions;

    private final ConstraintsInputScreen hansInputScreen;
    private final TimetableView timetablePanel;

    private String selectedItemName;
    private Boolean waitingForNewData = false;

    /**
     * Generating the Dialog also serves as the entry point     for the Use Case. The dialog will call the controller
     * and interactor and such.
     */
    public OverlapInputDialog(ArrayList<OverlapTimetableViewModel> timeTableOptions,
                              TimetableView timetablePanel,
                              ConstraintsInputScreen hansInputScreen, OverlapMaxInputBoundary overlapMaxController) {
        for (int i = 0; i < timeTableOptions.size(); i++) {

            timeTableRepresentations.put("Timetable " + i, timeTableOptions.get(i));
        }
        this.timeTableOptions = timeTableOptions;
        this.timetablePanel = timetablePanel;
        this.hansInputScreen = hansInputScreen;
        this.overlapMaxController = overlapMaxController;
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
        // This is sus!
        this.selectedMainTimetable = timeTableRepresentations.get(selectedItemName);

        // Get the bloody timeTable.
        overlapMaxController.getBestMatchingTimetable(selectedMainTimetable, candidateTimetables);

        // Now when the output comes back, the presenter will call a method to display it :).
    }

    public void updateTimetableOptions(ArrayList<OverlapTimetableViewModel> newTimetableOptions) {
        this.timeTableOptions = newTimetableOptions;
    }

    /**
     * A method to help call in Hans' UI to take over some data transfer stuff. Gives the necessary set up
     * to display his UI< basically.
     */
    private void callInHans() {
        // Set up a frame for his UI
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 400);
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel screen = new JPanel(cardLayout);
        screen.add(hansInputScreen);
        jFrame.add(screen);
        jFrame.setVisible(true);
    }

    /**
     * A method to help call in Kai's UI to display the best matching Timetable we got.
     */
    private void callInKai() {
        // Set up a frame for his UI
        JFrame jFrame = new JFrame();
        jFrame.setSize(800, 400);
        jFrame.setResizable(true);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        CardLayout cardLayout = new CardLayout();
        JPanel screen = new JPanel(cardLayout);
        screen.add(timetablePanel);
        jFrame.add(screen);
        jFrame.setVisible(true);
    }

    private void onCancel() {
        dispose();
    }

    // TODO: Delete this method and all its dependencies.
    // TODO: Also delete the freaking options thing eventually, keep it for now for initialization.
    //  I mean making it out of ViewModels doesn't hurt but like still.
    public static void main(String[] args) {

        // Initialise a test section with one block.
        Block testBlock = new Block("Monday", "18:00", "21:00", "Castle Badr");
        ArrayList<Block> testBlockList = new ArrayList<>();
        testBlockList.add(testBlock);

        Section testSection = new Section("LEC0101", "Mario", testBlockList);
        ArrayList<Section> testSectionList = new ArrayList<>();
        testSectionList.add(testSection);

        try {
            // Make a test course.
            TimetableCourse testCourse = new TimetableCourse("C1", testSectionList, "S", "CLA215", "4");
            ArrayList<TimetableCourse> testTimetableCourseList = new ArrayList<>();
            testTimetableCourseList.add(testCourse);

            // Now make a test timetable.
            Timetable testTimetable = new Timetable(testTimetableCourseList, "S");
            ArrayList<Timetable> testTimetableList = new ArrayList<Timetable>();
            testTimetableList.add(testTimetable);

            // SETUP for InputDialog creation
            // Convert test timetable to appropriate view model
            ArrayList<OverlapTimetableViewModel> timetableViewModels = new ArrayList<>();
            for (Timetable timetable : testTimetableList) {
                timetableViewModels.add(
                        ModelToOverlapViewModelConverter.convertTimetableModel(
                                EntityConverter.generateTimetableResponse(timetable))
                );
            }

            // Set up the Session stuff for Hans and JD.
            SessionGateway sessionGateway = new SessionGateway();
            Session fall;
            try {
                fall = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");
            } catch (ParseException | IOException | InvalidSectionsException e) {
                throw new RuntimeException(e);
            }

            // Set up JD's thing
            TimetableGeneratorPresenter generatorPresenter = new TimetableGeneratorPresenter();
            TimetableGeneratorInteractor generatorInteractor = new TimetableGeneratorInteractor(generatorPresenter);
            TimetableGeneratorController generatorController = new TimetableGeneratorController(generatorInteractor);

            // He needs this.
            generatorInteractor.onNext(fall);
            GenerateTimetableScreen generateTimetableScreen = new GenerateTimetableScreen(generatorController);

            // He needs this too..
            generatorPresenter.setView(timetables -> {
                System.out.println("Timetable Size: " + timetables.length);
                for (TimetableViewModel timetableModel : timetables) {
                    System.out.println("------");
                    for (TimetableViewCourseModel courseModel : timetableModel.getCourseData()) {
                        System.out.println(courseModel.getCode());
                    }
                }
            });

            // Set up Hans' stuff.
            SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
            SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
            SectionFilterController sectionFilterController = new SectionFilterController(sectionFilterInteractor);
            ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(generateTimetableScreen, sectionFilterController);
            sectionFilterPresenter.setView(constraintsInputScreen);

            sectionFilterInteractor.onNext(fall);

            // Make my presenters and stuff.
            OverlapMaxPresenter presenter = new OverlapMaxPresenter();
            OverlapGeneratedTimetableRelayInteractor relaySubscriber = new OverlapGeneratedTimetableRelayInteractor(presenter);
            generatorInteractor.subscribe(relaySubscriber);

            // Make my interactors
            CalculateSectionHoursInteractor sectionCalculator = new CalculateSectionHoursInteractor();
            TimeTableMatchInteractor timetableMatcher = new TimeTableMatchInteractor(sectionCalculator, presenter);

            // Make my controller
            OverlapMaxInputBoundary overlapMaxController = new OverlapMaximizationController(timetableMatcher);


            // Make my Dialog
            TimetableModel testTimetableModel = EntityConverter.generateTimetableResponse(testTimetable);
            TimetableViewModel testTimetableViewModel = TimetableModelConverter.timetableToView(testTimetableModel);
            TimetableView finalTimetableView = new TimetableView(testTimetableViewModel);
            OverlapInputDialog dialog = new OverlapInputDialog(timetableViewModels, finalTimetableView,
                    constraintsInputScreen, overlapMaxController);

            // Set the presenter to include the Dialog.
            presenter.setDialogToPassTo(dialog);

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
     * A method allowing for the presenter to pass in the best matching Timetable.
     * The best matching timetable is then displayed :)
     **/
    @Override
    public void stashBestMatchingTimetable(TimetableViewModel bestMatch) {
        timetablePanel.updateViewModel(bestMatch);
        callInKai();
        System.out.println(bestMatch);

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
