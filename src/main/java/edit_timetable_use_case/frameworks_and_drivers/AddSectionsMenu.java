package edit_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.interface_adapters.TimetableViewSectionModel;
import screens.SessionViewModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * A menu used to display available sections of a given course in either the add course or edit course use case.
 * session is the view model of the session that determines available courses and sections.
 * timetable is the view model of the timetable being edited.
 * editScreen is the screen that the sections menu belongs to.
 * courseCode is the code of the course that the menu displays the sections of.
 * sections is the JList that stores the section options, of which multiple can be selected.
 * sectionCodes is a List such that sectionCodes.get(i) refers to the section code of the sections[i] for all i.
 */
public class AddSectionsMenu extends JPanel implements ActionListener {

    private final SessionViewModel session;
    private final TimetableViewModel timetable;
    private final EditTimetableScreen editScreen;
    private final String courseCode;
    JList<String> sections;
    List<String> sectionCodes;

    public AddSectionsMenu(SessionViewModel session, TimetableViewModel timetable,
                           EditTimetableScreen editScreen, String courseCode){
        this.session = session;
        this.timetable = timetable;
        this.editScreen = editScreen;
        this.courseCode = courseCode;

        display();
    }

    public AddSectionsMenu(SessionViewModel session,
                           EditTimetableScreen editScreen, String courseCode){
        this.session = session;
        this.editScreen = editScreen;
        this.courseCode = courseCode;
        this.timetable = null;

        display();
    }

    /**
     * Creates the menu's UI. If the menu is being used in the add course use case, the course's current sections are
     * already selected.
     */
    private void display(){
        TimetableViewCourseModel calendarCourse = session.getCourses().get(courseCode);

        Vector<String> sectionDescriptions = new Vector<>();
        sectionCodes = new ArrayList<>();
        for (TimetableViewSectionModel section : calendarCourse.getSectionModels()){
            sectionDescriptions.add(describe(section));
            sectionCodes.add(section.getCode());
        }
        sections = new JList<>(sectionDescriptions);
        sections.setSelectionMode(
                ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);


        if (timetable != null){
            TimetableViewCourseModel timetableCourse = timetable.getCourse(courseCode);
            if (timetableCourse != null){
                List<Integer> currentSessionIndices = new ArrayList<>();
                for (TimetableViewSectionModel timetableSection : timetableCourse.getSectionModels()){
                    for (int j = 0; j < calendarCourse.getSectionModels().size(); j++){
                        if (calendarCourse.getSectionModels().get(j).getCode().equals(timetableSection.getCode())){
                            currentSessionIndices.add(j);
                        }
                    }
                }
                int[] indices = new int[currentSessionIndices.size()];
                for (int i = 0; i < currentSessionIndices.size(); i++){
                    indices[i] = currentSessionIndices.get(i);
                }
                sections.setSelectedIndices(indices);
            }
        }


        this.add(new JLabel("Select sections. Shift + Left Click to select multiple."));

        this.add(sections);

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
     * @param section the view model of the section being displayed
     * @return a String that describes the section (eg: "LEC0101: Thursday 10:00-12:00, Friday 13:00-14:00")
     */
    private String describe(TimetableViewSectionModel section) {
        StringBuilder description = new StringBuilder(section.getCode());
        description.append(": ");
        for (TimetableViewBlockModel block : section.getBlockModels()){
            description.append(describe(block)).append(", ");
        }
        return description.substring(0, description.length() - 2);
    }

    /**
     * @param block the view model of the block being displayed
     * @return a String describing the block (eg: "Thursday 10:00-12:00, AB106" or "Friday 13:00-14:00").
     */
    private String describe(TimetableViewBlockModel block){
        StringBuilder description = new StringBuilder();
        if (block.getDay() == 0){
            description.append("Monday ");
        }
        else if (block.getDay() == 1){
            description.append("Tuesday ");
        }
        else if (block.getDay() == 2){
            description.append("Wednesday ");
        }
        else if (block.getDay() == 3){
            description.append("Thursday ");
        }
        else if (block.getDay() == 4){
            description.append("Friday ");
        }

        description.append((int) block.getStartTime());
        description.append(":00 - ");
        description.append((int) block.getEndTime());
        description.append(":00");

        return description.toString();
    }

    /**
     * @param e the event to be processed
     *          If continueButton is pressed, the display returns to editScreen, and either the AddCourse or EditCourse
     *          use case is called, depending on whether Timetable is null or not.
     *          If the goBackButton is pressed, the display returns to editScreen without calling any use case.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Continue")){
            this.setVisible(false);
            editScreen.setVisible(true);
            ArrayList<String> selectedCodes = new ArrayList<>();
            for (int i : sections.getSelectedIndices()){
                selectedCodes.add(sectionCodes.get(i));
            }
            if (this.timetable == null){
                editScreen.add(courseCode, selectedCodes);
            }
            else{
                editScreen.edit(courseCode, selectedCodes);
            }
        }
        else if(e.getActionCommand().equals("Go back")){
            this.setVisible(false);
            editScreen.setVisible(true);
        }
    }
}
