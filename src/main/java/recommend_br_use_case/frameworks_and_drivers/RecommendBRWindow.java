package recommend_br_use_case.frameworks_and_drivers;

import edit_timetable_use_case.interface_adapters.EditTimetableController;
import recommend_br_use_case.interface_adapters.IRecommendBRView;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRViewModel;

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
    private RecommendBRInputScreen inputScreen;
    private final RecommendBRController brController;
    private final EditTimetableController editTimetableController;

    public RecommendBRWindow(JFrame frame, RecommendBRController brController, EditTimetableController editTimetableController){
        super(frame, "Recommend BR", true);
        this.setSize(600, 400);
        this.setResizable(false);
        this.brController = brController;
        this.editTimetableController = editTimetableController;
    }

    /**
     * Displays input screen that accepts breadth categories and
     * preferred time to recommend breadth courses
     *
     */
    public void showInputView(){
        this.getContentPane().removeAll();
        inputScreen = new RecommendBRInputScreen(brController);
        add(inputScreen);
        setVisible(true);
    }

    /**
     * Displays output screen when recommend BR use case is successful.
     * It displays list of recommended courses.
     * @param viewModel data that represents all information shown in success view
     */
    @Override
    public void showSuccessView(RecommendBRViewModel viewModel) {
        this.remove(inputScreen);
        add(new RecommendBROutputScreen(viewModel, editTimetableController));
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
