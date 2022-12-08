package display_timetable_use_case.frameworks_and_drivers;

import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import java.util.*;

/**
 * Class that is used to display the timetable given timetableViewModel.
 * Instance Attributes:
 *      - timetableViewModel: model containing all timetable information displayed
 *      - courseColors: color assigned to each course
 *      - observers: list of observer that get notified when course section is clicked
 */
public class TimetableView extends JPanel implements MouseListener {

    public static final int START_TIME = 8;
    public static final int END_TIME = 22;
    public static final int NUM_ROWS = (END_TIME - START_TIME) * 2 + 1;
    public static final int NUM_COLUMNS = 6;
    public static final String[] WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private int cellWidth;
    private int cellHeight;
    private TimetableViewModel timetableViewModel;
    private final HashMap<String, Color> courseColors;
    private final List<TimetableViewEventListener> observers;

    /**
     * Constructs TimetableView from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel
     *
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableView(TimetableViewModel timetableViewModel) {
        super();
        courseColors = new HashMap<>();
        updateViewModel(timetableViewModel);
        observers = new ArrayList<>();
        addMouseListener(this);
    }

    /**
     * Constructs TimetableView from the given TimetableViewModel, containing
     * all information to be displayed in this JPanel, with preferred size set to given
     * width and height
     *
     * @param width width of the preferred size of this component
     * @param height height of the preferred size of this component
     * @param timetableViewModel timetable data displayed in this component
     */
    public TimetableView(int width, int height, TimetableViewModel timetableViewModel){
        this(timetableViewModel);
        setPreferredSize(new Dimension(width, height));
    }

    /**
     * Renders the timetable corresponding to timetableViewModel, by displaying grid indicating each timeslots
     * and colouring the grid where it is
     *
     * @param g the <code>Graphics</code> object to protect
     */
    @Override
    public void paintComponent(Graphics g){
        Rectangle r = this.getBounds();
        cellWidth = r.width / NUM_COLUMNS;
        cellHeight = r.height / NUM_ROWS;

        drawEmptyTimetable(g);
        for (int i = 0; i < timetableViewModel.getCourseData().size(); i++){
            TimetableViewCourseModel courseModel = timetableViewModel.getCourseData().get(i);
            for (TimetableViewSectionModel sectionModel : courseModel.getSectionModels()){
                for (TimetableViewBlockModel blockModel : sectionModel.getBlockModels()){
                    drawBlock(g, blockModel, courseModel.getCode(), sectionModel.getCode());
                }
            }
        }
    }

    /**
     * Draw empty timetable with NUM_COLUMNS * NUM_ROWS rectangles of size cellWidth x cellHeight.
     * It displays day of the week at the top row and time at the left column.
     *
     * @param g Graphics object used to draw
     */
    private void drawEmptyTimetable(Graphics g){
        for (int i = 0; i < NUM_COLUMNS; i++){
            for (int j = 0; j < NUM_ROWS; j++){
                g.setColor(Color.DARK_GRAY);
                int x = i * cellWidth;
                int y = j * cellHeight;

                if (i == 0){
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, cellWidth, cellHeight);
                    if (j % 2 == 1){
                        g.setColor(Color.BLACK);
                        g.drawString((j / 2 + START_TIME) + ":00", x, y + cellHeight);
                    }
                    g.setColor(Color.DARK_GRAY);
                }

                if (j == 0){
                    g.setColor(Color.GRAY);
                    g.fillRect(x, y, cellWidth, cellHeight);

                    if (i != 0){
                        g.setColor(Color.BLACK);
                        g.drawString(WEEK_DAYS[i - 1], x, y + cellHeight);
                    }
                    g.setColor(Color.DARK_GRAY);
                }
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }
    }

    /**
     * Draw the given block as a rectangle in the appropriate position with the given course code and section code
     * displayed inside the box.
     *
     * @param g Graphics object used to draw
     * @param blockModel model storing all the information needed for displaying block
     * @param courseCode code of the course for the given block
     * @param sectionCode code of the section for the given block
     */
    private void drawBlock(Graphics g, TimetableViewBlockModel blockModel, String courseCode, String sectionCode){
        int y1 = (int) ((blockModel.getStartTime() - START_TIME) * 2 * cellHeight) + cellHeight;
        int y2 = (int) ((blockModel.getEndTime() - START_TIME) * 2 *  cellHeight) + cellHeight;
        int x1 = (blockModel.getDay() + 1) * cellWidth;

        g.setColor(courseColors.get(courseCode));
        g.fillRect(x1, y1, cellWidth, y2 - y1);
        String text = courseCode + "; " + sectionCode;

        g.setColor(Color.BLACK);

        Font font = g.getFont();
        float fontSize = ((float) cellWidth / g.getFontMetrics(font).stringWidth(text)) * font.getSize();
        g.setFont(font.deriveFont(fontSize));

        g.drawString(text, x1, y1 + cellHeight);
        g.setFont(font);
    }

    /**
     * Add the given observer to this class, that get notified when course section is clicked.
     *
     * @param observer new observer added
     */
    public void addTimetableViewListener(TimetableViewEventListener observer){
        observers.add(observer);
    }

    /**
     * Remove the given observer from this class
     *
     * @param observer observer that is going to be deleted
     */
    public void removeTimetableViewListener(TimetableViewEventListener observer){
        observers.remove(observer);
    }

    /**
     * Notify the observers that course section was displayed by calling courseClicked with
     * the given event as an argument
     *
     * @param event event describing the details of clicking course event
     */
    public void notifyObservers(TimetableViewEvent event){
        for (TimetableViewEventListener observer : observers){
            observer.courseClicked(event);
        }
    }

    /**
     * Call notifyObservers by passing in event detail as TimetableViewEvent when
     * course section is clicked by the user
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        Rectangle r = this.getBounds();
        int cellWidth = r.width / NUM_COLUMNS;
        int cellHeight = r.height / NUM_ROWS;

        for (int i = 0; i < timetableViewModel.getCourseData().size(); i++){
            TimetableViewCourseModel courseModel = timetableViewModel.getCourseData().get(i);
            for (TimetableViewSectionModel sectionModel : courseModel.getSectionModels()) {
                for (TimetableViewBlockModel blockModel : sectionModel.getBlockModels()) {
                    int y1 = (int) ((blockModel.getStartTime() - START_TIME) * 2 * cellHeight) + cellHeight;
                    int y2 = (int) ((blockModel.getEndTime() - START_TIME) * 2 *  cellHeight) + cellHeight;
                    int x1 = (blockModel.getDay() + 1) * cellWidth;
                    int x2 = x1 + cellWidth;

                    if (x1 <= e.getX() && e.getX() <= x2 && y1 <= e.getY() && e.getY() <= y2){
                        notifyObservers(new TimetableViewEvent(this, courseModel.getCode(), sectionModel.getCode()));
                    }
                }
            }
        }
    }

    /**
     * Unused method that is called when mouse is pressed
     *
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {}

    /**
     * Unused method that is called when mouse is released
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseReleased(MouseEvent e) {}

    /**
     * Unused method that is called when mouse is entered
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseEntered(MouseEvent e) {}

    /**
     * Unused method that is called when mouse is exited
     *
     * @param e the event to be processed
     */
    @Override
    public void mouseExited(MouseEvent e) {}

    /**
     * Sets the timetableViewModel to the given TimetableViewModel, and
     * update courseColors to assign new color to newly added courses.
     *
     * @param timetableViewModel new TimetableViewModel
     */
    public void updateViewModel(TimetableViewModel timetableViewModel){
        this.timetableViewModel = timetableViewModel;

        Random rand = new Random();
        HashSet<String> diff = new HashSet<>();
        for (TimetableViewCourseModel course : timetableViewModel.getCourseData()){
            diff.add(course.getCode());
            if (!courseColors.containsKey(course.getCode())) {
                Color color = new Color(rand.nextInt(256), rand.nextInt(256), rand.nextInt(256), 125);
                courseColors.put(course.getCode(), color);
            }
        }
        for (String code : new HashSet<>(courseColors.keySet())) {
            if (!diff.contains(code)){
                courseColors.remove(code);
            }
        }
    }
}
