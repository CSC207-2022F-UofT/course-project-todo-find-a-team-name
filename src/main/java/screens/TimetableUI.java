package screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class used to display timetable, as well as buttons used to navigate to other screens.
 * Instance Attributes:
 *      - timetableViewModel: model containing all timetable information displayed
 *      - timetableView: JPanel that displays the timetable
 *      - prevPanel: previous panel displayed before this panel (null if it doesn't exist)
 */
public class TimetableUI extends JPanel implements ActionListener {

    private final TimetableViewModel timetableViewModel;
    private final TimetableView timetableView;
    private final EditTimetableScreen editTimetableScreen;
    private JPanel prevPanel = null;


    /**
     * Constructs TimetableUI from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel
     *
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableUI(TimetableViewModel timetableViewModel, EditTimetableScreen editTimetableScreen){
        this.timetableViewModel = timetableViewModel;
        this.timetableView = new TimetableView(timetableViewModel);
        this.editTimetableScreen = editTimetableScreen;

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
     * @param width width of the preferred size of this component
     * @param height height of the preferred size of this component
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableUI(int width, int height, TimetableViewModel timetableViewModel, EditTimetableScreen editTimetableScreen){
        this(timetableViewModel, editTimetableScreen);
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
        frame.getContentPane().removeAll();
        frame.add(panel);
        frame.revalidate();
        this.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        switch (command) {
            case "match":
                // TODO: implement match
                break;
            case "save":
                // TODO: implement save
                break;
            case "edit":
                changeScreen(editTimetableScreen);
                break;
            case "<=":
                if (prevPanel != null) {
                    changeScreen(prevPanel);
                }
                break;
        }
    }
}
