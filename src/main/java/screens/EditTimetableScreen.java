package screens;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.JOptionPane.showMessageDialog;


public class EditTimetableScreen extends JPanel implements ActionListener {

    private final EditTimetableController controller;


    public EditTimetableScreen(EditTimetableController controller, ){
        this.controller = controller;

        JButton recommendBR = new JButton("Recommend BR courses");
        JButton save = new JButton("Save");
        JButton addCourse = new JButton("Add a course");

        JPanel buttons = new JPanel();
        buttons.add(save);
        buttons.add(recommendBR);
        buttons.add(addCourse);

        save.addActionListener(this);
        /* not sure about this one */
        recommendBR.addActionListener(this);
        addCourse.addActionListener(this);


        this.setSize(1280, 720);
        GridLayout layout = new GridLayout();
        this.setLayout(layout);

        this.add(buttons);

    }


    /**
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
