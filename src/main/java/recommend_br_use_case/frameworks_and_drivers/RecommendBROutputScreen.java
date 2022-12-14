package recommend_br_use_case.frameworks_and_drivers;

import edit_timetable_use_case.interface_adapters.EditTimetableController;
import recommend_br_use_case.interface_adapters.RecommendBRCourseViewModel;
import recommend_br_use_case.interface_adapters.RecommendBRViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 * JPanel used to display output of recommend BR use case
 */
public class RecommendBROutputScreen extends JPanel implements ListSelectionListener, ActionListener {

    private final JList<String> recommendedCourses;
    private final CourseInfoPanel courseInfoPanel;

    private final RecommendBRViewModel viewModel;
    private final EditTimetableController editTimetableController;

    /**
     * Constructs RecommendBROutputScreen from the given RecommendBRViewModel
     * that displays list of courses recommended on the left and selected course information on the right
     *
     * @param viewModel object containing all the information shown to the user in this screen
     * @param editTimetableController controller for adding selected course in the timetable
     */
    public RecommendBROutputScreen(RecommendBRViewModel viewModel, EditTimetableController editTimetableController){
        super();
        this.viewModel = viewModel;
        this.editTimetableController = editTimetableController;

        setLayout(new BorderLayout());
        recommendedCourses = createCourseJList();
        recommendedCourses.addListSelectionListener(this);

        JScrollPane scrollPane1 = new JScrollPane(recommendedCourses);
        courseInfoPanel = new CourseInfoPanel();

        JScrollPane scrollPane2 = new JScrollPane(courseInfoPanel);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(scrollPane2, BorderLayout.CENTER);
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(this);
        rightPanel.add(addCourseButton, BorderLayout.PAGE_END);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, rightPanel);
        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
    }

    /**
     * Create and return JList that displays list of recommended breadth courses.
     * Each item displays the basic course information in the following format:
     *      [course code]; [lecture code]; [tutorial code]; [practical code]
     * If one of the section codes is null in viewModel, it will be skipped.
     * For example, if tutorial code is null, we get:
     *      [course code]; [lecture code]; [practical code]
     *
     * @return JList that displays list of recommended breadth courses in the viewModel
     */
    private JList<String> createCourseJList(){
        String[] items = new String[viewModel.getCourseViewModels().size()];
        for (int i = 0; i < items.length; i++){
            String courseCode = viewModel.getCourseViewModels().get(i).getCode();
            String lectureCode = viewModel.getCourseViewModels().get(i).getLectureCode();
            String tutorialCode = viewModel.getCourseViewModels().get(i).getTutorialCode();
            String practicalCode = viewModel.getCourseViewModels().get(i).getPracticalCode();

            items[i] = courseCode + (lectureCode == null ? "":("; " + lectureCode))
                    + (tutorialCode == null ? "" : ("; " + tutorialCode))
                    + (practicalCode == null ? "": ("; " + practicalCode));
        }
        return new JList<>(items);
    }

    /**
     * When list item (i.e. course) is selected, show the course information on the right
     * @param e the event that characterizes the change.
     */
    @Override
    public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
            courseInfoPanel.showCourseInfo(viewModel.getCourseViewModels().get(recommendedCourses.getSelectedIndex()));
        }
    }

    /**
     * If event occurs, close the window and add the selected course to the timetable in EditTimetableScreen
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        RecommendBRCourseViewModel course = viewModel.getCourseViewModels().get(recommendedCourses.getSelectedIndex());
        ArrayList<String> sectionCodes = new ArrayList<>();

        if (course.getLectureCode() != null)
            sectionCodes.add(course.getLectureCode());
        if (course.getTutorialCode() != null)
            sectionCodes.add(course.getTutorialCode());
        if (course.getPracticalCode() != null)
            sectionCodes.add(course.getPracticalCode());

        try {
            editTimetableController.add(course.getCode(), sectionCodes);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        SwingUtilities.getWindowAncestor(this).dispose();
    }
}
