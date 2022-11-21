package screens;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class MainUI extends JPanel{

    public MainUI(){
        super();
        setLayout(new BorderLayout());

        JButton generateTimetable = new JButton("Generate timetable");

        JLabel title = new JLabel("Main menu");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        add(title, BorderLayout.PAGE_START);

        JPanel centerPanel = new JPanel();
//        centerPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        JPanel centerPanel2 = new JPanel();

        JPanel timetablePanel = new JPanel();
        timetablePanel.setLayout(new BoxLayout(timetablePanel, BoxLayout.PAGE_AXIS));

        TitledBorder timetableBorder = BorderFactory.createTitledBorder("Existing Timetable Operations");
        timetableBorder.setTitleJustification(TitledBorder.CENTER);
        timetablePanel.setBorder(timetableBorder);

        JPanel importTimetablePanel = new JPanel();
        JButton importTimetable = new JButton("Import timetable");
        JLabel filePath1 = new JLabel("Choose the file...");
        importTimetablePanel.add(importTimetable);
        importTimetablePanel.add(filePath1);

        timetablePanel.add(importTimetablePanel);

        JPanel timetableButtons = new JPanel();
        JButton editButton= new JButton("Edit");
        JButton displayButton = new JButton("Display");
        timetableButtons.add(editButton);
        timetableButtons.add(displayButton);

        timetablePanel.add(timetableButtons);

        centerPanel.add(timetablePanel);

        centerPanel.add(generateTimetable);


        JPanel importSessionPanel = new JPanel();
        JButton importSession = new JButton("Import session");

        JLabel filePath2 = new JLabel("Choose the file...");
        importSessionPanel.add(importSession);
        importSessionPanel.add(filePath2);

        add(centerPanel, BorderLayout.CENTER);
        add(importSessionPanel, BorderLayout.PAGE_END);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame();

        MainUI mainUI = new MainUI();
        mainUI.setPreferredSize(new Dimension(500, 500));
        frame.add(mainUI);

        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
