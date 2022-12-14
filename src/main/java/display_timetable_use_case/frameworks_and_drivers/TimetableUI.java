package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.ITimetableUI;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import fileio_use_case.interface_adapters.SaveTimetableController;
import generate_overlapping_timetable_use_case.frameworks_and_drivers.OverlapInputDialog;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * Class used to display timetable, as well as buttons used to navigate to other screens.
 * Instance Attributes:
 *      - timetableViewModel: model containing all timetable information displayed
 *      - timetableView: JPanel that displays the timetable
 *      - prevPanel: previous panel displayed before this panel (null if it doesn't exist)
 */
public class TimetableUI extends JPanel implements ActionListener, ITimetableUI {

    private final DisplayTimetableController displayTimetableController;
    private final TimetableViewModel timetableViewModel;
    private final TimetableView timetableView;
    private final EditTimetableScreen editTimetableScreen;
    private final OverlapInputDialog overlapInputDialog;
    private final SaveTimetableController saveTimetableController;
    private JPanel prevPanel = null;


    /**
     * Constructs TimetableUI from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel
     *
     * @param displayTimetableController controller used for displaying timetable
     * @param editTimetableScreen        screen for editing timetable
     * @param overlapInputDialog         dialog for input screen for overlap maximization use case
     * @param saveTimetableController    controller used to save timetable
     */
    public TimetableUI(DisplayTimetableController displayTimetableController,
                       EditTimetableScreen editTimetableScreen, OverlapInputDialog overlapInputDialog, SaveTimetableController saveTimetableController){
        this.displayTimetableController = displayTimetableController;
        this.saveTimetableController = saveTimetableController;
        this.timetableViewModel = new TimetableViewModel(new ArrayList<>());
        this.timetableView = new TimetableView(timetableViewModel);
        this.editTimetableScreen = editTimetableScreen;
        this.overlapInputDialog = overlapInputDialog;

        setLayout(new BorderLayout());

        JPanel pageStart = new JPanel();
        pageStart.setLayout(new BorderLayout());
        JLabel title = new JLabel("Timetable UI");
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton match = new JButton("match");
        JButton save = new JButton("save");
        JButton edit = new JButton("edit");
        JButton goBack = new JButton("<=");
        match.addActionListener(this);
        save.addActionListener(this);
        edit.addActionListener(this);
        goBack.addActionListener(this);

        JPanel buttons = new JPanel();
        BoxLayout boxLayout = new BoxLayout(buttons, BoxLayout.LINE_AXIS);
        buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        buttons.setLayout(boxLayout);

        buttons.add(match);
        buttons.add(save);
        buttons.add(edit);

        pageStart.add(buttons, BorderLayout.EAST);
        pageStart.add(goBack, BorderLayout.WEST);
        pageStart.add(title, BorderLayout.CENTER);

        add(pageStart, BorderLayout.PAGE_START);

        add(timetableView, BorderLayout.CENTER);

    }

    /**
     * Constructs TimetableUI from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel, with preferred size set to given
     * width and height
     *
     * @param width                      width of the preferred size of this component
     * @param height                     height of the preferred size of this component
     * @param editTimetableScreen        screen for editing timetable
     * @param displayTimetableController controller used for displaying timetable
     * @param overlapInputDialog         dialog for input screen for overlap maximization use case
     * @param saveTimetableController    controller used to save timetable
     */
    public TimetableUI(int width, int height, EditTimetableScreen editTimetableScreen,
                       DisplayTimetableController displayTimetableController, OverlapInputDialog overlapInputDialog, SaveTimetableController saveTimetableController){
        this(displayTimetableController, editTimetableScreen, overlapInputDialog, saveTimetableController);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns timetableViewModel of this class, containing all timetable information displayed
     * in this class
     *
     * @return timetableViewModel containing all timetable information displayed
     */
    public TimetableViewModel getTimetableViewModel() {
        return timetableViewModel;
    }

    /**
     * Returns JPanel displaying the timetable
     *
     * @return JPanel displaying the timetable
     */
    public TimetableView getTimetableView() {
        return timetableView;
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
        if (frame != null){
            frame.getContentPane().remove(this);
            frame.add(panel);
            frame.revalidate();
        }
        panel.setVisible(true);
    }

    /**
     * Method called when button is pressed which navigate to other screen
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "match":
                overlapInputDialog.pack();
                overlapInputDialog.setVisible(true);
                break;
            case "save":
                saveTimetableController.saveTimetable();
                break;
            case "edit":
                changeScreen(editTimetableScreen);
                editTimetableScreen.updateTimetable();
                editTimetableScreen.updateSession();
                editTimetableScreen.setPreviousPanel(this);
                break;
            case "<=":
                if (prevPanel != null) {
                    changeScreen(prevPanel);
                }
                break;
        }
    }

    /**
     * Update the timetable for this view to the imported timetable
     */
    public void updateTimetable(){
        displayTimetableController.displayTimetable();
    }

    /**
     * Update the timetable for this view to the given timetable view model
     *
     * @param viewModel object storing all information needed for displaying timetable
     */
    @Override
    public void updateTimetable(TimetableViewModel viewModel) {
        setVisible(false);
        timetableView.updateViewModel(viewModel);
        setVisible(true);
    }

    /**
     * Show message to the user
     * @param message error message
     */
    @Override
    public void showTimetableFailView(String message) {
        changeScreen(prevPanel);
        JOptionPane.showMessageDialog(SwingUtilities.getWindowAncestor(this), message);
    }
}
