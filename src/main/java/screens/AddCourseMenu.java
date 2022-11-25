package screens;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

/**
 * A menu used to display courses available to a user in the add course use case.
 * session is the view model of the session for which the user is making a timetable for.
 * editScreen is the EditTimetableScreen which instantiated the menu.
 * courses is a list of the courses displayed on the menu.
 * timetable is the view model of the timetable being edited.
 * frame is the frame that the menu belongs to.
 */
public class AddCourseMenu extends JPanel implements ActionListener {

    SessionViewModel session;
    EditTimetableScreen editScreen;
    JList<String> courses;
    TimetableViewModel timetable;
    JFrame frame;

    /**
     * @param session session is the view model of the session for which the user is making a timetable for.
     * @param timetable timetable is the view model of the timetable being edited.
     * @param editScreen editScreen is the EditTimetableScreen which instantiated the menu.
     * @param frame frame is the frame that the menu belongs to.
     * The add course menu constructor displays all available courses by course code, of which only one
     *              can be selected. continueButton then continues the course adding process by moving the display
     *              on to a AddSectionsMenu. goBackButton sends the user back to the EditTimetableScreen.
     */
    public AddCourseMenu(SessionViewModel session, TimetableViewModel timetable, EditTimetableScreen editScreen,
                         JFrame frame){
        this.session = session;
        this.timetable = timetable;
        this.editScreen = editScreen;
        this.frame = frame;

        Vector<String> courseCodes = new Vector<>(session.getCourses().keySet());
        for (TimetableViewCourseModel course : timetable.getCourseData()){
            courseCodes.remove(course.getCode());
        }

        courses = new JList<>(courseCodes);
        ListSelectionModel selectionModel = new DefaultListSelectionModel();
        selectionModel.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        courses.setSelectionModel(selectionModel);
        this.add(courses);

        JButton goBackButton = new JButton("Go back");
        goBackButton.addActionListener(this);

        JButton continueButton = new JButton("Continue");
        continueButton.addActionListener(this);

        JPanel buttons = new JPanel();
        buttons.add(goBackButton);
        buttons.add(continueButton);
        this.add(buttons);

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    /**
     * @param e the event to be processed
     *          If the button pressed was continueButton, then the user input is passed on to the AddSectionsMenu
     *          which displays the available sections of the selected course.
     *          If the button pressed was the goBackButton, the user is sent back to the original EditTimetableScreen.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Continue")){
            AddSectionsMenu sectionsMenu = new AddSectionsMenu(session, editScreen, courses.getSelectedValue());
            this.setVisible(false);
            frame.add(sectionsMenu);
            sectionsMenu.setVisible(true);
        }
        else if (e.getActionCommand().equals("Go back")){
            this.setVisible(false);
            editScreen.setVisible(true);
        }
    }
}
