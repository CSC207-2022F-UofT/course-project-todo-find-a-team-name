package screens;

import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import display_timetable_use_case.frameworks_and_drivers.TimetableUI;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import fileio_use_case.interface_adapters.SessionFileController;
import fileio_use_case.interface_adapters.TimetableFileController;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Class used to display the main menu of this program that allow user to import files and navigates user to
 * other screens.
 * Instance Attributes:
 *      - fallSessionFilePath: JLabel representing the filepath of the session data that user imports.
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 *      - filePathTimetable: filepath of the timetable data user imports
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 */
public class MainUI extends JPanel implements ActionListener {

    private JLabel fallSessionFilePath;
    private JLabel timetableFilePath;
    private JLabel winterSessionFilePath;
    private final SessionFileController sessionController;
    private final TimetableFileController timetableController;

    private final ConstraintsInputScreen constraintsInputScreen;
    private final EditTimetableScreen editTimetableScreen;
    private final TimetableUI timetableUI;
    private final JFrame frame;
    private final JPanel timetablePanel;
    private boolean isTimetableImported;

    /**
     * Constructs MainUI with title, import timetable/session buttons, and display/edit/generate timetable buttons.
     *
     * @param frame window that this screen belongs to
     * @param constraintsInputScreen input screen for generate timetable button
     * @param editTimetableScreen screen for editing timetable
     * @param timetableUI screen for displaying timetable
     * @param sessionController controller used for importing sessions
     * @param timetableFileController controller used for importing timetable
     */
    public MainUI(JFrame frame, ConstraintsInputScreen constraintsInputScreen, EditTimetableScreen editTimetableScreen,
                  TimetableUI timetableUI, SessionFileController sessionController, TimetableFileController timetableFileController){
        super();
        this.constraintsInputScreen = constraintsInputScreen;
        this.editTimetableScreen = editTimetableScreen;
        this.timetableUI = timetableUI;
        this.frame = frame;
        this.sessionController = sessionController;
        this.timetableController = timetableFileController;
        this.isTimetableImported = false;
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Main menu");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        add(title, BorderLayout.PAGE_START);

        JPanel wrapperPanel = new JPanel();
        wrapperPanel.setLayout(new BoxLayout(wrapperPanel, BoxLayout.Y_AXIS));

        timetablePanel = createTimetablePanel();

        JButton generateTimetable = new JButton("Generate timetable");
        generateTimetable.addActionListener(this);
        generateTimetable.setAlignmentX(JButton.CENTER_ALIGNMENT);

        wrapperPanel.add(Box.createVerticalStrut(50));
        wrapperPanel.add(timetablePanel);
        wrapperPanel.add(Box.createVerticalStrut(50));
        wrapperPanel.add(generateTimetable);

        JPanel centerPanel = new JPanel();
        centerPanel.add(wrapperPanel);

        JPanel importSessionPanel = createImportSessionPanel();

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
    }

    /**
     * Create and return panel that allow user to import existing timetable.
     * filePathTimetable instance attribute is also updated to the JLabel representing the current
     * file path imported for timetable
     *
     * @return panel that allow user to import, edit and display existing timetable
     */
    private JPanel createTimetablePanel(){
        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.PAGE_AXIS));

        TitledBorder timetableBorder = BorderFactory.createTitledBorder("");
        timetableBorder.setTitleJustification(TitledBorder.CENTER);
        timetablePanel.setBorder(timetableBorder);
        JPanel importTimetablePanel = new JPanel();
        JButton importTimetable = new JButton("Import timetable");
        importTimetable.addActionListener(this);
        timetableFilePath = new JLabel("Choose the file...");
        importTimetablePanel.add(importTimetable);
        importTimetablePanel.add(timetableFilePath);

        timetablePanel.add(importTimetablePanel);
        return timetablePanel;
    }

    /**
     * Add edit and display button to timetablePanel located at the top-center of the screen
     */
    private void addTimetableButtons(){
        JPanel timetableButtons = new JPanel();
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(this);

        timetableButtons.add(editButton);
        timetableButtons.add(displayButton);
        timetablePanel.add(timetableButtons);
    }

    /**
     * Create and returns panel that allow user to import session.
     * fallSessionFilePath instance attribute is also updated to the JLabel representing the current
     * file path imported for session.
     *
     * @return panel that allow user to import session
     */
    private JPanel createImportSessionPanel(){
        JPanel importSessionPanel = new JPanel();

        importSessionPanel.setLayout(new BoxLayout(importSessionPanel, BoxLayout.PAGE_AXIS));
        JPanel importFallSessionPanel = new JPanel();
        JPanel importWinterSessionPanel = new JPanel();

        // Import fall session button
        JButton importFallSession = new JButton("Import fall session");
        importFallSession.addActionListener(this);
        fallSessionFilePath = new JLabel("Choose the file... ");
        importFallSessionPanel.add(importFallSession);
        importFallSessionPanel.add(fallSessionFilePath);

        // Import winter session button
        JButton importWinterSession = new JButton("Import winter session");
        importWinterSession.addActionListener(this);
        winterSessionFilePath = new JLabel("Choose the file... ");
        importWinterSessionPanel.add(importWinterSession);
        importWinterSessionPanel.add(winterSessionFilePath);

        importSessionPanel.add(importFallSessionPanel);
        importSessionPanel.add(importWinterSessionPanel);

        return importSessionPanel;
    }

    /**
     * Change the screen of the frame to the given panel
     *
     * @param panel new screen
     */
    private void changeScreen(JPanel panel){
        this.setVisible(false);
        frame.getContentPane().removeAll();
        panel.setVisible(true);
        frame.add(panel);
        frame.revalidate();
        this.setVisible(true);
    }

    /**
     * Process the user's interaction with buttons by navigating to appropriate screen/window when
     * button is pressed by user.
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();
        switch (command) {
            case "Import timetable": {
                JFileChooser fileChooser = new JFileChooser("src/main");
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog( this)) {
                    String importTimetableFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        timetableController.createTimetableFile(importTimetableFilePath);
                        timetableFilePath.setText(importTimetableFilePath);
                        if (!isTimetableImported){
                            addTimetableButtons();
                        }
                        isTimetableImported = true;
                    } catch (IOException | ParseException | java.text.ParseException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid File!");
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Invalid File! " + ex.getMessage());
                    }
                }
                break;
            }
            case "Import fall session": {
                JFileChooser fileChooser = new JFileChooser("src/main");
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "F");
                        fallSessionFilePath.setText(importedSessionFilePath);
                        winterSessionFilePath.setText("Choose the file... ");
                    } catch (IOException | ParseException | java.text.ParseException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid File!");
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Invalid File! " + ex.getMessage());
                    }
                }
                break;
            }
            case "Import winter session": {
                JFileChooser fileChooser = new JFileChooser("src/main");
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "S");
                        winterSessionFilePath.setText(importedSessionFilePath);
                        fallSessionFilePath.setText("Choose the file... ");
                    } catch (IOException | ParseException | java.text.ParseException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid File!");
                    } catch (Exception ex){
                        JOptionPane.showMessageDialog(this, "Invalid File! " + ex.getMessage());
                    }
                }
                break;
            }
            case "Generate timetable": {
                changeScreen(constraintsInputScreen);
                constraintsInputScreen.setPrevPanel(this);
                break;
            }
            case "Edit":
                changeScreen(editTimetableScreen);
                editTimetableScreen.updateTimetable();
                editTimetableScreen.updateSession();
                editTimetableScreen.setPreviousPanel(this);
                break;
            case "Display": {
                changeScreen(timetableUI);
                timetableUI.updateTimetable();
                timetableUI.setPrevPanel(this);
                break;
            }
        }
    }
}
