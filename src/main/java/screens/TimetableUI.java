package screens;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Class used to display timetable, as well as buttons used to navigate to other screens.
 * Instance Attributes:
 *      - timetableViewModel: model containing all timetable information displayed
 *      - timetableView: JPanel that displays the timetable
 */
public class TimetableUI extends JPanel {

    private final TimetableViewModel timetableViewModel;
    private final TimetableView timetableView;

    /**
     * Constructs TimetableUI from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel
     *
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableUI(TimetableViewModel timetableViewModel){
        this.timetableViewModel = timetableViewModel;
        this.timetableView = new TimetableView(timetableViewModel);

        setLayout(new BorderLayout());

        JPanel pageStart = new JPanel();
        pageStart.setLayout(new BorderLayout());
        JLabel title = new JLabel("Timetable UI");
        title.setHorizontalAlignment(JLabel.CENTER);

        JButton match = new JButton("match");
        JButton save = new JButton("save");
        JButton edit = new JButton("edit");
        JButton goBack = new JButton("<=");

        match.addActionListener(e -> {
            // TODO: add match screen
        });

        save.addActionListener(e -> {
            // TODO: add save screen
        });

        edit.addActionListener(e -> {
            // TODO: add edit screen
        });

        goBack.addActionListener(e -> {
            // TODO: add previous screen
        });

        JPanel buttons = new JPanel();
        BoxLayout boxLayout = new BoxLayout(buttons, BoxLayout.LINE_AXIS);
        buttons.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
        buttons.setLayout(boxLayout);

        buttons.add(match);
        buttons.add(save);
        buttons.add(edit);

        pageStart.add(buttons, BorderLayout.EAST);
        pageStart.add(goBack, BorderLayout.WEST);
        pageStart.add(title, BorderLayout.CENTER);

        add(pageStart, BorderLayout.PAGE_START);

        add(timetableView, BorderLayout.CENTER);
    }

    /**
     * Constructs TimetableUI from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel, with preferred size set to given
     * width and height
     *
     * @param width width of the preferred size of this component
     * @param height height of the preferred size of this component
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableUI(int width, int height, TimetableViewModel timetableViewModel){
        this(timetableViewModel);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Returns timetableViewModel of this class, containing all timetable information displayed
     * in this class
     *
     * @return timetableViewModel containing all timetable information displayed
     */
    public TimetableViewModel getTimetableViewModel() {
        return timetableViewModel;
    }

    /**
     * Returns JPanel displaying the timetable
     *
     * @return JPanel displaying the timetable
     */
    public TimetableView getTimetableView() {
        return timetableView;
    }

    // This method is only used for testing during the development, it will be deleted soon
    // TODO: Delete this method
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        java.util.List<TimetableViewCourseModel> courseData = new ArrayList<>();
        java.util.List<TimetableViewSectionModel> sectionModels1 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels1 = new ArrayList<>();
        blockModels1.add(new TimetableViewBlockModel(0, 11, 12));
        blockModels1.add(new TimetableViewBlockModel(4, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("LEC0101", blockModels1));

        java.util.List<TimetableViewBlockModel> blockModels2 = new ArrayList<>();
        blockModels2.add(new TimetableViewBlockModel(2, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("TUT0101", blockModels2));

        courseData.add(new TimetableViewCourseModel("CSC236H1", sectionModels1));


        java.util.List<TimetableViewSectionModel> sectionModels2 = new ArrayList<>();

        java.util.List<TimetableViewBlockModel> blockModels3 = new ArrayList<>();
        blockModels3.add(new TimetableViewBlockModel(1, 16, 17));
        blockModels3.add(new TimetableViewBlockModel(4, 16, 17));
        sectionModels2.add(new TimetableViewSectionModel("LEC0401", blockModels3));

        List<TimetableViewBlockModel> blockModels4 = new ArrayList<>();
        blockModels4.add(new TimetableViewBlockModel(0, 14, 16));
        sectionModels2.add(new TimetableViewSectionModel("TUT0301", blockModels4));

        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));


        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
        TimetableUI timetableView = new TimetableUI(700, 700, timetableViewModel);

        frame.add(timetableView);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }
}
