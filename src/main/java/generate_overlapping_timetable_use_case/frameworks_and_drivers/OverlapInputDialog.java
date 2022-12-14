package generate_overlapping_timetable_use_case.frameworks_and_drivers;

import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import com.intellij.uiDesigner.core.GridConstraints;
import com.intellij.uiDesigner.core.GridLayoutManager;
import com.intellij.uiDesigner.core.Spacer;
import display_timetable_use_case.frameworks_and_drivers.TimetableUI;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import generate_overlapping_timetable_use_case.interface_adapters.OverlapInputView;
import generate_overlapping_timetable_use_case.interface_adapters.OverlapMaximizationController;
import generate_overlapping_timetable_use_case.interface_adapters.OverlapTimetableViewModel;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.Flow;

public class OverlapInputDialog extends JDialog implements Flow.Subscriber<Object>, OverlapInputView {


    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JComboBox<String> timeTableComboBox;
    private JLabel textLabel;

    private final HashMap<String, OverlapTimetableViewModel> timeTableRepresentations = new HashMap<>();
    private final OverlapMaximizationController overlapMaxController;
    private ArrayList<OverlapTimetableViewModel> timeTableOptions = new ArrayList<>();

    private final ConstraintsInputScreen hansInputScreen;

    // This needs to be initialized later, since TimetableUI requires a complete version of this dialog.
    private TimetableUI timetablePanel = null;

    private final JFrame mainFrame;
    private String selectedItemName;
    private Boolean waitingForNewData = false;

    /**
     * Generating the Dialog also serves as the entry point for the Use Case. The dialog will call the controller
     * and interactor and such.
     */
    public OverlapInputDialog(ConstraintsInputScreen hansInputScreen, OverlapMaximizationController overlapMaxController,
                              JFrame mainFrame) {
        this.mainFrame = mainFrame;
        this.hansInputScreen = hansInputScreen;
        this.overlapMaxController = overlapMaxController;
        $$$setupUI$$$();
        setUpUiWithNewTimetables(timeTableOptions);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);
        setUpCancelFunctionality();
        setUpInputPassing();
        this.setSize(512, 360);
    }

    /**
     * A helper that sets up the UI with new timetables. Basically re-initializes the Dialog.
     * FIXME: UNTESTED
     **/
    private void setUpUiWithNewTimetables(ArrayList<OverlapTimetableViewModel> newTimetables) {
        this.timeTableOptions = newTimetables;
        timeTableRepresentations.clear();
        for (int i = 0; i < timeTableOptions.size(); i++) {
            timeTableRepresentations.put("Timetable " + i, timeTableOptions.get(i));
        }
        this.timeTableComboBox = initialiseComboBox();
        $$$setupUI$$$();
        setContentPane(contentPane);
        mainFrame.revalidate();
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
        OverlapTimetableViewModel selectedMainTimetable = timeTableRepresentations.get(selectedItemName);

        // Get the bloody timeTable.
        overlapMaxController.getBestMatchingTimetable(selectedMainTimetable, candidateTimetables);

        // Now when the output comes back, the presenter will call a method to display it :).
    }


    /**
     * A method to help call in Hans' UI to take over some data transfer stuff. Gives the necessary set up
     * to display his UI< basically.
     */
    private void callInHans() {
        // Rewrite the main frame for his UI
        mainFrame.getContentPane().removeAll();
        CardLayout cardLayout = new CardLayout();
        JPanel screen = new JPanel(cardLayout);
        screen.add(hansInputScreen);
        mainFrame.add(screen);
        mainFrame.setSize(1280, 720);
        mainFrame.revalidate();
    }

    /**
     * A method to help call in Kai's UI to display the best matching Timetable we got.
     */
    private void callInKai() {
        // Rewrite the mainFrame for his UI.
        mainFrame.getContentPane().removeAll();
        CardLayout cardLayout = new CardLayout();
        JPanel screen = new JPanel(cardLayout);
        screen.add(timetablePanel);
        mainFrame.add(screen);
        mainFrame.setSize(1280, 720);
        mainFrame.revalidate();
    }

    private void onCancel() {
        dispose();
    }

    // TODO: Delete this method and all its dependencies.
    // TODO: Also delete the freaking options thing eventually, keep it for now for initialization.
    //  I mean making it out of ViewModels doesn't hurt but like still.
//    public static void main(String[] args)
//
//        // Initialise a test section with one block.
//        Block testBlock = new Block("Monday", "18:00", "21:00", "Castle Badr");
//        ArrayList<Block> testBlockList = new ArrayList<>();
//        testBlockList.add(testBlock);
//
//        Section testSection = new Section("LEC0101", "Mario", testBlockList);
//        ArrayList<Section> testSectionList = new ArrayList<>();
//        testSectionList.add(testSection);
//
//        try {
//            // Make a test course.
//            TimetableCourse testCourse = new TimetableCourse("C1", testSectionList, "S", "CLA215", "4");
//            ArrayList<TimetableCourse> testTimetableCourseList = new ArrayList<>();
//            testTimetableCourseList.add(testCourse);
//
//            // Now make a test timetable.
//            Timetable testTimetable = new Timetable(testTimetableCourseList, "S");
//            ArrayList<Timetable> testTimetableList = new ArrayList<>();
//            testTimetableList.add(testTimetable);
//
//            // SETUP for InputDialog creation
//            // Convert test timetable to appropriate view model
//            ArrayList<OverlapTimetableViewModel> timetableViewModels = new ArrayList<>();
//            for (Timetable timetable : testTimetableList) {
//                timetableViewModels.add(
//                        ModelToOverlapViewModelConverter.convertTimetableModel(
//                                EntityConverter.generateTimetableResponse(timetable))
//                );
//            }
//
//            // Set up the main frame.
//            JFrame frame = new JFrame();
//
//            /*A combined initialization of the Edit Timetable and Retrieve Timetable use cases. This block should be after
//            the RecommendBRWindow is created,
//             * Kai: note that editTimetableScreen needs a RecommendBRWindow on the final merge.
//             (it's called recommendBRWindow in the code, but feel free to rename it.)
//             * Yahya, Emily, and anyone else that creates a Timetable/Session publisher: addCourseInteractor,
//             * removeCourseInteractor, editCourseInteractor and retrieveTimetableInteractor all need to be included as
//             * subscribers and updated as appropriate. (displayTimetableInteractor should also be included, but that's
//             more Kai's side of things.)
//             * Yahya and anyone that opens the timetable editor (Emily?): make sure that the displayTimetableInteractor is
//             updated with the appropriate timetable and session before pulling up the edit timetable screen, and
//             make sure to call editTimetableScreen.updateTimetable(ttViewModel),
//             editTimetableScreen.updateSession(), editTimetableScreen.updateTimetable(), and
//             editTimetableScreen.setPreviousPanel(previousPanel) before setting it to visible. You may need to use the
//             retrieveTimetable use case to do this if you don't already have the view models (although you probably should
//             have it already).
//             */
//            RetrieveTimetableInteractor retrieveTimetableInteractor = new RetrieveTimetableInteractor();
//
//            RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
//            RemoveCourseInteractor removeInteractor = new RemoveCourseInteractor(removePresenter);
//            removeInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
//            AddCoursePresenter addPresenter = new AddCoursePresenter();
//            AddCourseInteractor addInteractor = new AddCourseInteractor(addPresenter);
//            addInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
//            EditCoursePresenter editPresenter = new EditCoursePresenter();
//            EditCourseInteractor editInteractor = new EditCourseInteractor(editPresenter);
//            EditTimetableController editController = new EditTimetableController(removeInteractor, addInteractor, editInteractor);
//            editInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
//            JPanel prevPanel = new JPanel();
//            DisplayTimetablePresenter displayPresenter = new DisplayTimetablePresenter();
//            DisplayTimetableController updateController = new DisplayTimetableController(new DisplayTimetableInteractor(displayPresenter));
//            EditTimetableScreen editScreen = new EditTimetableScreen(frame, editController, prevPanel, updateController);
//
//            removePresenter.setView(editScreen);
//            addPresenter.setView(editScreen);
//            editPresenter.setView(editScreen);
//            displayPresenter.setView(editScreen);
//
//            /*
//             * Set up for BR recommendation:
//             *
//             * Emily, Yahya, and anybody who implements publisher for timetable and session should subscribe
//             * RecommendBRInteractor
//             */
//            RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter();
//            CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
//            RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter, courseComparatorFactory);
//            RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);
//            RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, recommendBRController, editController);
//            editScreen.setBRWindow(recommendBRWindow);
//            recommendBRPresenter.setView(recommendBRWindow);
//
//            // Set up the Session stuff for Hans and JD.
//            SessionGateway sessionGateway = new SessionGateway();
//            Session fall;
//            try {
//                fall = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");
//            } catch (ParseException | IOException | InvalidSectionsException e) {
//                throw new RuntimeException(e);
//            }
//
//            // Set up JD's thing
//            TimetableGeneratorPresenter generatorPresenter = new TimetableGeneratorPresenter();
//            TimetableGeneratorInteractor generatorInteractor = new TimetableGeneratorInteractor(generatorPresenter);
//            TimetableGeneratorController generatorController = new TimetableGeneratorController(generatorInteractor);
//
//            // He needs this.
//            generatorInteractor.onNext(fall);
//            GenerateTimetableScreen generateTimetableScreen = new GenerateTimetableScreen(generatorController);
//
//            // He needs this too..
//            generatorPresenter.setView(timetables -> {
//                System.out.println("Timetable Size: " + timetables.length);
//                for (TimetableViewModel timetableModel : timetables) {
//                    System.out.println("------");
//                    for (TimetableViewCourseModel courseModel : timetableModel.getCourseData()) {
//                        System.out.println(courseModel.getCode());
//                    }
//                }
//            });
//
//            /*
//             * This is temporary since timetable view and main menu ui branch haven't merged yet!
//             *
//             * Emily, Yahya, and anybody who implements publisher for timetable and session should subscribe
//             * displayTimetableInteractor
//             *
//             * Anyone who displays the timetableUI should call updateTimetable() to update view model
//             * and setPrevPanel() to set the previous panel to the appropriate JPanel
//             *
//             * Hans, I need ConstraintInputScreen for mainUI
//             * Emily, I need SessionFileController and TimetableFileController for mainUI
//             *
//             */
//
//            // Make my presenters and stuff.
//            OverlapMaxPresenter presenter = new OverlapMaxPresenter();
//            OverlapGeneratedTimetableRelayInteractor relaySubscriber = new OverlapGeneratedTimetableRelayInteractor(presenter);
//            generatorInteractor.subscribe(relaySubscriber);
//
//            // Make my interactors
//            CalculateSectionHoursInteractor sectionCalculator = new CalculateSectionHoursInteractor();
//            TimeTableMatchInteractor timetableMatcher = new TimeTableMatchInteractor(sectionCalculator, presenter);
//
//            // Make my controller
//            OverlapMaximizationController overlapMaxController = new OverlapMaximizationController(timetableMatcher);
//
//            DisplayTimetablePresenter displayTimetablePresenter = new DisplayTimetablePresenter();
//            DisplayTimetableInteractor displayTimetableInteractor = new DisplayTimetableInteractor(displayTimetablePresenter);
//            DisplayTimetableController displayTimetableController = new DisplayTimetableController(displayTimetableInteractor);
//
//            // Set up Hans' stuff.
//            SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
//            SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
//            SectionFilterController sectionFilterController = new SectionFilterController(sectionFilterInteractor);
//            ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(generateTimetableScreen, sectionFilterController);
//            sectionFilterPresenter.setView(constraintsInputScreen);
//            OverlapInputDialog dialog = new OverlapInputDialog(timetableViewModels,
//                    constraintsInputScreen, overlapMaxController, frame);
//
//            TimetableGateway timetableGateway = new TimetableGateway();
//            SaveTimetableInteractor saveTimetableInteractor = new SaveTimetableInteractor(timetableGateway);
//            SaveTimetableController saveTimetableController = new SaveTimetableController(saveTimetableInteractor);
//            TimetableUI timetableUI = new TimetableUI(displayTimetableController, editScreen, dialog,
//                    saveTimetableController);
//            displayTimetablePresenter.setView(timetableUI);
//            dialog.setTimetablePanel(timetableUI);
//            /* The line below must run after displayPresenter's view has been set to screen.*/
//            editScreen.updateTimetable();
//            frame.add(editScreen);
//
//
//            sectionFilterInteractor.onNext(fall);
//            // Set the presenter to include the Dialog.
//            presenter.setDialogToPassTo(dialog);
//
//            frame.pack();
//            frame.setSize(1280, 720);
//            frame.setVisible(true);
//            dialog.pack();
//            dialog.setVisible(true);
//
//        } catch (InvalidSectionsException e) {
//            throw new RuntimeException(e);
//        }
//

    /**
     * A method generated by the Swing Framework. Custom initialization of components should be handled in this method.
     */
    private void createUIComponents() {
        timeTableComboBox = initialiseComboBox();
    }

    // Custom initialize a ComboBox object and return it.
    private JComboBox<String> initialiseComboBox() {
        return new JComboBox<>(timeTableRepresentations.keySet().toArray(new String[0]));
    }

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

    /**
     * Initialise this OverlapInputDialog's accompanying timetableUI so that it can display the finished timetable.
     * Although you can technically initialise the dialog without calling it, don't try! It's NOT a good idea!!!
     *
     * @param timetableUI - the companion timetableUI.
     */
    public void setTimetablePanel(TimetableUI timetableUI) {
        this.timetablePanel = timetableUI;
    }

    @Override
    public void stashTimetableViewModels(List<OverlapTimetableViewModel> viewModels) {
        timeTableOptions = new ArrayList<>(viewModels);


        // If we're waiting on data, activate the calculations with this one.
        // Make the calculation part
        // only activate if a flag of currently calculating is true. Otherwise just take in the data. That way
        // we can kind of wait for JD's thing to finish.
        if (waitingForNewData) {
            activateCalculations();
        } else {
            // If we're not waiting on new data, update the UI.
            setUpUiWithNewTimetables(timeTableOptions);
        }
    }

    /**
     * A method allowing for the presenter to pass in the best matching Timetable.
     * The best matching timetable is then displayed :)
     **/
    @Override
    public void stashBestMatchingTimetable(TimetableViewModel bestMatch) {
        timetablePanel.updateTimetable();
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
        contentPane.setName("OverlapInputDialog");
        contentPane.setBorder(BorderFactory.createTitledBorder(null, this.$$$getMessageFromBundle$$$("strings", "overlapInputDialogTitle"), TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, null, null));
        final JPanel panel1 = new JPanel();
        panel1.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel1, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, 1, null, null, null, 0, false));
        final Spacer spacer1 = new Spacer();
        panel1.add(spacer1, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_WANT_GROW, 1, null, null, null, 0, false));
        final JPanel panel2 = new JPanel();
        panel2.setLayout(new GridLayoutManager(1, 2, new Insets(0, 0, 0, 0), -1, -1, true, false));
        panel1.add(panel2, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        buttonOK = new JButton();
        this.$$$loadButtonText$$$(buttonOK, this.$$$getMessageFromBundle$$$("strings", "finishDataInputPrompt"));
        panel2.add(buttonOK, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        buttonCancel = new JButton();
        this.$$$loadButtonText$$$(buttonCancel, this.$$$getMessageFromBundle$$$("strings", "cancelPrompt"));
        panel2.add(buttonCancel, new GridConstraints(0, 1, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final JPanel panel3 = new JPanel();
        panel3.setLayout(new GridLayoutManager(4, 2, new Insets(0, 0, 0, 0), -1, -1));
        contentPane.add(panel3, new GridConstraints(0, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_BOTH, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_CAN_SHRINK | GridConstraints.SIZEPOLICY_CAN_GROW, null, null, null, 0, false));
        textLabel = new JLabel();
        this.$$$loadLabelText$$$(textLabel, this.$$$getMessageFromBundle$$$("strings", "overlapInputDialogPrompt"));
        panel3.add(textLabel, new GridConstraints(0, 0, 1, 2, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_NONE, GridConstraints.SIZEPOLICY_FIXED, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer2 = new Spacer();
        panel3.add(spacer2, new GridConstraints(1, 1, 2, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        timeTableComboBox.setName("timeTableComboBox");
        timeTableComboBox.setOpaque(false);
        panel3.add(timeTableComboBox, new GridConstraints(2, 0, 1, 1, GridConstraints.ANCHOR_WEST, GridConstraints.FILL_HORIZONTAL, GridConstraints.SIZEPOLICY_CAN_GROW, GridConstraints.SIZEPOLICY_FIXED, null, null, null, 0, false));
        final Spacer spacer3 = new Spacer();
        panel3.add(spacer3, new GridConstraints(1, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        final Spacer spacer4 = new Spacer();
        panel3.add(spacer4, new GridConstraints(3, 0, 1, 1, GridConstraints.ANCHOR_CENTER, GridConstraints.FILL_VERTICAL, 1, GridConstraints.SIZEPOLICY_WANT_GROW, null, null, null, 0, false));
        textLabel.setLabelFor(timeTableComboBox);
    }

    private static Method $$$cachedGetBundleMethod$$$ = null;

    private String $$$getMessageFromBundle$$$(String path, String key) {
        ResourceBundle bundle;
        try {
            Class<?> thisClass = this.getClass();
            if ($$$cachedGetBundleMethod$$$ == null) {
                Class<?> dynamicBundleClass = thisClass.getClassLoader().loadClass("com.intellij.DynamicBundle");
                $$$cachedGetBundleMethod$$$ = dynamicBundleClass.getMethod("getBundle", String.class, Class.class);
            }
            bundle = (ResourceBundle) $$$cachedGetBundleMethod$$$.invoke(null, path, thisClass);
        } catch (Exception e) {
            bundle = ResourceBundle.getBundle(path);
        }
        return bundle.getString(key);
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadLabelText$$$(JLabel component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setDisplayedMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    private void $$$loadButtonText$$$(AbstractButton component, String text) {
        StringBuffer result = new StringBuffer();
        boolean haveMnemonic = false;
        char mnemonic = '\0';
        int mnemonicIndex = -1;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == '&') {
                i++;
                if (i == text.length()) break;
                if (!haveMnemonic && text.charAt(i) != '&') {
                    haveMnemonic = true;
                    mnemonic = text.charAt(i);
                    mnemonicIndex = result.length();
                }
            }
            result.append(text.charAt(i));
        }
        component.setText(result.toString());
        if (haveMnemonic) {
            component.setMnemonic(mnemonic);
            component.setDisplayedMnemonicIndex(mnemonicIndex);
        }
    }

    /**
     * @noinspection ALL
     */
    public JComponent $$$getRootComponent$$$() {
        return contentPane;
    }

}
