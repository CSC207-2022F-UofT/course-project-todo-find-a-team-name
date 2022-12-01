package blacklist_whitelist_use_case.frameworks_and_drivers;

import javax.swing.*;
import java.awt.*;

public class HelpInstructionScreen extends JDialog {
    public HelpInstructionScreen(){
        JLabel title = new JLabel("Input Format");
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));
        this.setSize(500, 500);
        setLayout(new BorderLayout());
        JTextArea textArea = new JTextArea();
        String s = "*--> For instructor Constraint input, enter the instructor names with the Initial of the first name and" +
                "full last name seperated by comma. For example, if you don't want to take the course delivered by Professor" +
                "Genius Alien and Smart Bro, select blacklist, and enter: G Alien, S Bro \n\n *--> For Room Constraint input, " +
                "enter the Building and the room number seperated by comma, SS 123, SS 234";
        textArea.setText(s);
        textArea.setLineWrap(true);
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);
        this.add(title, BorderLayout.NORTH);

        this.add(scrollPane, BorderLayout.CENTER);
    }

}
