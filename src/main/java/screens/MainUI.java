package screens;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class used to display the main menu of this program that allow user to import files and navigates user to
 * other screens.
 * Instance Attributes:
 *      - filePathSession: JLabel representing the filepath of the session data that user imports.
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 *      - filePathTimetable: filepath of the timetable data user imports
 *      If user have not selected the filepath, it's text becomes "Choose the file..."
 */
public class MainUI extends JPanel implements ActionListener {

    private final JLabel filePathSession;
    private final JLabel filePathTimetable;

    /**
     * Constructs MainUI with title, import timetable/session buttons, and display/edit/generate timetable buttons.
     */
    public MainUI(){
        super();
        setLayout(new BorderLayout());

        JLabel title = new JLabel("Main menu");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        add(title, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.PAGE_AXIS));


        TitledBorder timetableBorder = BorderFactory.createTitledBorder("Existing Timetable Operations");
        timetableBorder.setTitleJustification(TitledBorder.CENTER);
        timetablePanel.setBorder(timetableBorder);

        JPanel importTimetablePanel = new JPanel();
        JButton importTimetable = new JButton("Import timetable");
        importTimetable.addActionListener(this);
        filePathTimetable = new JLabel("Choose the file...");
        importTimetablePanel.add(importTimetable);
        importTimetablePanel.add(filePathTimetable);

        timetablePanel.add(importTimetablePanel);

        JPanel timetableButtons = new JPanel();
        JButton editButton = new JButton("Edit");
        editButton.addActionListener(this);
        JButton displayButton = new JButton("Display");
        displayButton.addActionListener(this);

        timetableButtons.add(editButton);
        timetableButtons.add(displayButton);
        timetablePanel.add(timetableButtons);

        JButton generateTimetable = new JButton("Generate timetable");
        generateTimetable.setAlignmentX(JButton.CENTER_ALIGNMENT);


        panel.add(Box.createVerticalStrut(50));

        panel.add(timetablePanel);

        panel.add(Box.createVerticalStrut(50));

        panel.add(generateTimetable);

        centerPanel.add(panel);


        JPanel importSessionPanel = new JPanel();
        JButton importSession = new JButton("Import session");
        importSession.addActionListener(this);

        filePathSession = new JLabel("Choose the file...");
        importSessionPanel.add(importSession);
        importSessionPanel.add(filePathSession);

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
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
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    filePathTimetable.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
                break;
            }
            case "Import session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    filePathSession.setText(fileChooser.getSelectedFile().getAbsolutePath());
                }
                break;
            }
            case "Edit":
            case "Display":
                break;
        }
    }

    // TODO: Remove this
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        MainUI mainUI = new MainUI();
        mainUI.setPreferredSize(new Dimension(500, 400));
        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
