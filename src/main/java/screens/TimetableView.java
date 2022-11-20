package screens;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TimetableView extends JPanel {

    public static final int START_TIME = 8;
    public static final int END_TIME = 22;
    public static final int NUM_ROWS = (END_TIME - START_TIME) * 2 + 1;
    public static final int NUM_COLUMNS = 6;
    public static final String[] WEEK_DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
    private final TimetableViewModel timetableViewModel;
    private final Color[] courseColors;

    public TimetableView(int width, int height, TimetableViewModel timetableViewModel){
        super();
        this.timetableViewModel = timetableViewModel;
        setPreferredSize(new Dimension(width, height));

        courseColors = new Color[timetableViewModel.getCourseData().size()];
        Random rand = new Random();
        for (int i = 0; i < courseColors.length; i++){
            courseColors[i] = new Color(rand.nextInt(256), rand.nextInt(256),
                    rand.nextInt(256), 125);
        }
    }

    @Override
    public void paintComponent(Graphics g){

        Rectangle r = this.getBounds();

        int cellWidth = r.width / NUM_COLUMNS;
        int cellHeight = r.height / NUM_ROWS;

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
                    g.setColor(Color.BLACK);

                    if (i != 0)
                        g.drawString(WEEK_DAYS[i - 1], x, y + cellHeight);
                    g.setColor(Color.DARK_GRAY);
                }
                g.drawRect(x, y, cellWidth, cellHeight);
            }
        }

        for (int i = 0; i < timetableViewModel.getCourseData().size(); i++){
            TimetableViewCourseModel courseModel = timetableViewModel.getCourseData().get(i);
            for (TimetableViewSectionModel sectionModel : courseModel.getSectionModels()){
                for (TimetableViewBlockModel blockModel : sectionModel.getBlockModels()){
                    int y1 = (int) ((blockModel.getStartTime() - START_TIME) * 2 * cellHeight) + cellHeight;
                    int y2 = (int) ((blockModel.getEndTime() - START_TIME) * 2 *  cellHeight) + cellHeight;
                    int x1 = (blockModel.getDay() + 1) * cellWidth;

                    g.setColor(courseColors[i]);
                    g.fillRect(x1, y1, cellWidth, y2 - y1);

                    g.setColor(Color.BLACK);
                    String text = courseModel.getCode() + "; " + sectionModel.getCode();

                    Font font = g.getFont();
                    float fontSize = ((float) cellWidth / g.getFontMetrics(font).stringWidth(text)) * font.getSize();
                    g.setFont(font.deriveFont(fontSize));

                    g.drawString(text, x1, y1 + cellHeight);
                    g.setFont(font);

                }
            }
        }
    }


    // This method is only used for testing during the development, it will be deleted soon
    // TODO: Delete this method
    public static void main(String[] args) {
        JFrame frame = new JFrame();

        List<TimetableViewCourseModel> courseData = new ArrayList<>();
        List<TimetableViewSectionModel> sectionModels1 = new ArrayList<>();

        List<TimetableViewBlockModel> blockModels1 = new ArrayList<>();
        blockModels1.add(new TimetableViewBlockModel(0, 11, 12));
        blockModels1.add(new TimetableViewBlockModel(4, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("LEC0101", blockModels1));

        List<TimetableViewBlockModel> blockModels2 = new ArrayList<>();
        blockModels2.add(new TimetableViewBlockModel(2, 11, 12));
        sectionModels1.add(new TimetableViewSectionModel("TUT0101", blockModels2));

        courseData.add(new TimetableViewCourseModel("CSC236H1", sectionModels1));


        List<TimetableViewSectionModel> sectionModels2 = new ArrayList<>();

        List<TimetableViewBlockModel> blockModels3 = new ArrayList<>();
        blockModels3.add(new TimetableViewBlockModel(1, 16, 17));
        blockModels3.add(new TimetableViewBlockModel(4, 16, 17));
        sectionModels2.add(new TimetableViewSectionModel("LEC0401", blockModels3));

        List<TimetableViewBlockModel> blockModels4 = new ArrayList<>();
        blockModels4.add(new TimetableViewBlockModel(0, 14, 16));
        sectionModels2.add(new TimetableViewSectionModel("TUT0301", blockModels4));

        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));

        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
        frame.add(new TimetableView(500, 600, timetableViewModel));

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
