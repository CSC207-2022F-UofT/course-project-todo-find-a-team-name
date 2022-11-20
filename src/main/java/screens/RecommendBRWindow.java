package screens;

import javax.swing.*;
import java.awt.event.WindowEvent;

/**
 * Dialog window that displays input and output screen of RecommendBR use case.
 */
public class RecommendBRWindow extends JDialog implements IRecommendBRView {

    /**
     * Constructs RecommendBRWindow from the given JFrame, controller and timetable id.
     * User cannot perform any action on the given frame until this window is closed.
     * It displays the RecommendBRInputScreen.
     */
    RecommendBRInputScreen inputScreen;
    public RecommendBRWindow(JFrame frame, RecommendBRController controller, String timetableId){
        super(frame, "Recommend BR", true);
        this.setSize(600, 400);
        this.setResizable(false);

        inputScreen = new RecommendBRInputScreen(controller, timetableId);
        add(inputScreen);

    }

    /**
     * Displays output screen when recommend BR use case is successful.
     * It displays list of recommended courses.
     * @param viewModel data that represents all information shown in success view
     */
    @Override
    public void showSuccessView(RecommendBRViewModel viewModel) {
        this.remove(inputScreen);
        add(new RecommendBROutputScreen(viewModel));
        revalidate();
    }

    /**
     * Creates pop up window explaining how recommend BR use case failed.
     *
     * @param message message explaining how it failed
     */
    @Override
    public void showFailView(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    /**
     * When this window is closed, switch the screen to input screen.
     *
     * @param e the window event
     */
    @Override
    protected void processWindowEvent(WindowEvent e){
        super.processWindowEvent(e);
        if (e.getID() == WindowEvent.WINDOW_CLOSED){
            this.getContentPane().removeAll();
            this.add(inputScreen);
            this.revalidate();
        }
    }

}
