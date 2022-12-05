package screens;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import fileio_use_case.interface_adapters.SessionFileController;
import org.json.simple.parser.ParseException;

import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class MainUI extends JPanel implements ActionListener {

    private final JLabel filePathSession;
    private final JLabel filePathSession2;
    private final JLabel filePathTimetable;
    public SessionFileController sessionController;

    public MainUI(SessionFileController sessionController){
        super();
        this.sessionController = sessionController;
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

        // Importing Sessions
        JPanel importSessionPanel = new JPanel();
        importSessionPanel.setLayout(new BoxLayout(importSessionPanel, BoxLayout.PAGE_AXIS));
        JPanel importFallSessionPanel = new JPanel();
        JPanel importWinterSessionPanel = new JPanel();
        // Creates border for each session panel
        TitledBorder sessionBorder = BorderFactory.createTitledBorder("");
        importFallSessionPanel.setBorder(sessionBorder);
        importWinterSessionPanel.setBorder(sessionBorder);
        // Import fall session button
        JButton importFallSession = new JButton("Import fall session");
        importFallSession.addActionListener(this);
        filePathSession = new JLabel("Choose the file... ");
        importFallSessionPanel.add(importFallSession);
        importFallSessionPanel.add(filePathSession);
        // Import winter session button
        JButton importWinterSession = new JButton("Import winter session");
        importWinterSession.addActionListener(this);
        filePathSession2 = new JLabel("Choose the file... ");
        importWinterSessionPanel.add(importWinterSession);
        importWinterSessionPanel.add(filePathSession2);

        importSessionPanel.add(importFallSessionPanel);
        importSessionPanel.add(importWinterSessionPanel);

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
    }

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
            case "Import fall session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathSession.setText(importedSessionFilePath);
                    // Add SessionFileController
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "F");
                    } catch (IOException | ParseException | java.text.ParseException | InvalidSectionsException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                break;
            }
            case "Import winter session": {
                JFileChooser fileChooser = new JFileChooser(FileSystemView.getFileSystemView());
                if (JFileChooser.APPROVE_OPTION == fileChooser.showOpenDialog(this)) {
                    String importedSessionFilePath = fileChooser.getSelectedFile().getAbsolutePath();
                    filePathSession2.setText(importedSessionFilePath);
                    // Add SessionFileController
                    try {
                        sessionController.createSessionFile(importedSessionFilePath, "S");
                    } catch (IOException | ParseException | java.text.ParseException | InvalidSectionsException ex) {
                        throw new RuntimeException(ex);
                    }
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

        SessionGateway gateway = new SessionGateway();

        SessionGatewayInteractor hi = new SessionGatewayInteractor(gateway);

        SessionFileController controller = new SessionFileController(hi);

        MainUI mainUI = new MainUI(controller);
        mainUI.setPreferredSize(new Dimension(500, 400));
        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
