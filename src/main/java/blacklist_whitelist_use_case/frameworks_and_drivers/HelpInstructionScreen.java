package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;
import java.awt.*;

/**
 * UI component of Constraint input Screen that provides help on input format to the user.
 */
public class HelpInstructionScreen extends JDialog {
    public HelpInstructionScreen(){
        JLabel title = new JLabel("Input Format");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        this.setSize(500, 500);
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        String s = "*--> For instructor Constraint input, enter the instructor names with the Initial of the first name and" +
                " full last name seperated by comma. For example, if you don't want to take the course delivered by Professor" +
                " Genius Alien and Smart Bro, select blacklist, and enter: G Alien, S Bro \n\n *--> For Room Constraint input, " +
                "enter the Building and the room number seperated by comma, SS 123, SS 234. \n\n *--> Note if you select Whitelist" +
                " constraints, you MUST enter valid preference for all the courses. eg. If you want to whitelist instructor, you are" +
                " encouraged to enter at least one of your favorite instructor for each course. " +
                "\n\n* --> IMPORTANT DEMO: \n\n Courses: MAT224H1, STA257H1, CSC236H1, CSC207H1, CSC343H1" +
                "\n\nTimeInterval Constraints: WhiteList 10:00 - 21:00" +
                "\n\nClick submit and filter" ;
        textArea.setText(s);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(title, BorderLayout.NORTH);

        this.add(scrollPane, BorderLayout.CENTER);
    }

}
