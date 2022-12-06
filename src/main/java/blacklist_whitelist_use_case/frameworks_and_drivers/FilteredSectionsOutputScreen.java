package blacklist_whitelist_use_case.frameworks_and_drivers;

import blacklist_whitelist_use_case.interface_adapters.SectionFilterViewModel;

import javax.swing.*;
import java.awt.*;

/**
 * JPanel used to display output of Filtered Sections.
 */
public class FilteredSectionsOutputScreen extends JDialog {
    private final JPanel generateTimeTableScreen;
    private final SectionFilterViewModel viewModel;
    private final JFrame frame;

    /**
     * Constructs the FilteredSectionsOutputScreen from the given ViewModel.
     * @param frame the main frame.
     * @param viewModel data structure storing data needed for displaying courseCodes and the filtered section codes
     *                  for BlackList/Whitelist use case.
     */
    public FilteredSectionsOutputScreen(JPanel generateTimeTableScreen, JFrame frame, SectionFilterViewModel viewModel){
        super(frame, true);
        this.generateTimeTableScreen = generateTimeTableScreen;
        this.frame = frame;
        this.viewModel = viewModel;
        this.setSize(500, 500);
        setLayout(new BorderLayout());

        JLabel title = new JLabel( "Course Sections Filter COMPLETE!" );
        title.setHorizontalAlignment(JLabel.CENTER);
        title.setFont(title.getFont().deriveFont(18F));

        this.add(title, BorderLayout.PAGE_START);

        JTextArea textArea = new JTextArea();

        StringBuilder sb = new StringBuilder();
        for (String courseCode : viewModel.getModifiedCourses().keySet()){
            sb.append("      |-----").append(courseCode).append(": \n");
            for (String sectionCode: viewModel.getModifiedCourses().get(courseCode)){
                sb.append("              > ").append(sectionCode).append("\n");
            }
        }
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(textArea);

        JButton button = new JButton("Prepare to Generate");

        button.addActionListener(e -> {
            frame.getContentPane().removeAll();
            frame.add(generateTimeTableScreen);
//            generateTimeTableScreen.setSessionType(viewModel.getSessionType());
//            generateTimeTableScreen.setModifiedCourses(viewModel.getModifiedCourses());
            this.dispose();
            frame.repaint();
            frame.revalidate();
        });
        this.add(button, BorderLayout.PAGE_END);
        this.add(scrollPane, BorderLayout.CENTER);
    }
}