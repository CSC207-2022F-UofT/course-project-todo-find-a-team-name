package screens;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;

/**
 * JPanel used to display output of recommend BR use case
 */
public class RecommendBROutputScreen extends JPanel implements ListSelectionListener{

    private final JList<String> recommendedCourses;
    private final CourseInfoPanel courseInfoPanel;

    private final RecommendBRViewModel viewModel;

    /**
     * Constructs RecommendBROutputScreen from the given RecommendBRViewModel
     * that displays list of courses recommended on the left and selected course information on the right
     *
     * @param viewModel object containing all the information shown to the user in this screen
     */
    public RecommendBROutputScreen(RecommendBRViewModel viewModel){
        super();
        this.viewModel = viewModel;
        setLayout(new BorderLayout());

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
        recommendedCourses = new JList<>(items);

        JScrollPane scrollPane1 = new JScrollPane(recommendedCourses);
        courseInfoPanel = new CourseInfoPanel();

        JScrollPane scrollPane2 = new JScrollPane(courseInfoPanel);
        recommendedCourses.addListSelectionListener(this);

        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new BorderLayout());
        rightPanel.add(scrollPane2, BorderLayout.CENTER);
        JButton addCourseButton = new JButton("Add Course");
        addCourseButton.addActionListener(e -> {
            // Create request model from viewModel and send it to another use case
            SwingUtilities.getWindowAncestor(this).dispose();
        });
        rightPanel.add(addCourseButton, BorderLayout.PAGE_END);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, scrollPane1, rightPanel);
        add(splitPane, BorderLayout.CENTER);
        setVisible(true);
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
}
