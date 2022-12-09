package timetables_sort_use_case.frameworks_and_drivers;

import display_timetable_use_case.frameworks_and_drivers.*;
import generate_overlapping_timetable_use_case.frameworks_and_drivers.OverlapInputDialog;
import screens.MainUI;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;
import timetables_sort_use_case.interface_adapters.AllTimetablesController;
import timetables_sort_use_case.interface_adapters.AllTimetablesView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *  An implementation of AllTimetablesView in JSwing.
 *  ttViews are the timetable views that the user browses through
 *  timetablesPanel contains all the ttViews
 *  the other parameters are controllers and views that connect to this screen
 */
public class AllTimetablesScreen extends JPanel implements ActionListener, AllTimetablesView {

    private final JFrame frame;
    private TimetableView[] ttViews;
    private final JPanel timetablesPanel;
    private TimetablesSortMenu timetablesSortMenu;
    private final TimetablesSortController timetablesSortController;
    private final AllTimetablesController allTimetablesController;
    private final MainUI mainUI;
    private final TimetableUI timetableUI;
    private final OverlapInputDialog overlapInputDialog;

    public AllTimetablesScreen(JFrame frame, MainUI mainUI, TimetableUI timetableUI,
                               OverlapInputDialog overlapInputDialog, TimetablesSortController timetablesSortController,
                               AllTimetablesController allTimetablesController) {
        this.frame = frame;
        this.timetablesSortController = timetablesSortController;
        this.ttViews = null;
        this.setLayout(new BorderLayout());
        JPanel timetablesPanel = new JPanel();
        this.timetablesPanel = timetablesPanel;
        this.timetablesSortMenu = null;
        this.allTimetablesController = allTimetablesController;
        this.mainUI = mainUI;
        this.timetableUI = timetableUI;
        this.overlapInputDialog = overlapInputDialog;

        JPanel top = new JPanel();
        top.setSize(100, 100);
        JButton sort = new JButton("Sort");
        JButton mainMenu = new JButton("Main Menu");
        JButton overlap = new JButton("Overlap");
        top.add(sort);
        top.add(mainMenu);
        top.add(overlap);
        sort.addActionListener(this);
        mainMenu.addActionListener(this);
        overlap.addActionListener(this);

        timetablesPanel.setLayout(new GridLayout(0, 2, 10, 10));
        timetablesPanel.setSize(500, 500);
        JScrollPane scrollPane = new JScrollPane(timetablesPanel);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);


        this.add(top, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
    }

    /**
     * Returns User to MainMenuUI if mainMenu is clicked, sends User to sorter screen if sort is clicked,
     * opens TimeTableUI of the given timetable if a timetable button is clicked.
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Sort")) {
            openTimetablesSortMenu();
        } else if (e.getActionCommand().equals("Main Menu")) {
            openMainUI();
        } else if (e.getActionCommand().equals("Overlap")) {
            openOverlapUI();
        } else {
            int i = e.getActionCommand().length() - 1;
            openTimetableUI(i);
        }
    }

    /**
     * closes this view and opens a TimetablesSortMenu
     */
    public void openTimetablesSortMenu() {
        timetablesSortMenu = new TimetablesSortMenu(frame, this);
        frame.getContentPane().removeAll();
        frame.add(timetablesSortMenu);
        frame.pack();
    }

    /**
     * closes this view and opens MainUI
     */
    public void openMainUI() {
        this.setVisible(false);
        frame.getContentPane().removeAll();
        frame.add(mainUI);
        frame.revalidate();
        mainUI.setVisible(true);
    }

    /**
     * closes this view and opens TimeTableUI with the timetable that the user chose
     *
     * @param i the index of the timetable that was chosen
     */
    public void openTimetableUI(int i) {
        this.setVisible(false);
        frame.getContentPane().removeAll();
        allTimetablesController.updateSubscribers(i);
        timetableUI.updateTimetable();
        timetableUI.setPrevPanel(this);
        frame.add(timetableUI);
        frame.revalidate();
    }

    /**
     * opens OverlapInputDialogue
     */
    public void openOverlapUI() {
        overlapInputDialog.setVisible(true);
    }

    /**
     * shows this screen with the updated timetables
     *
     * @param timetableViewModels the updated timetables that we want to present
     */
    public void updateTimetables(TimetableViewModel[] timetableViewModels) {
        if (ttViews == null) {
            ttViews = new TimetableView[timetableViewModels.length];
            for (int i = 0; i < timetableViewModels.length; i++) {
                ttViews[i] = new TimetableView(600, 350, timetableViewModels[i]);
                JButton btn = new JButton("Timetable " + i);
                btn.addActionListener(this);
                JPanel container = new JPanel();
                container.setLayout(new BorderLayout());
                container.setSize(600, 400);
                container.add(ttViews[i], BorderLayout.CENTER);
                container.add(btn, BorderLayout.NORTH);
                timetablesPanel.add(container);
            }
        }
        for (int i = 0; i < ttViews.length; i++) {
            ttViews[i].updateViewModel(timetableViewModels[i]);
        }
        frame.getContentPane().removeAll();
        frame.add(this);
        this.revalidate();
        frame.revalidate();
    }

    /**
     * sorts the timetables based on the user's preference then updates the view
     *
     * @param timeButton  the timeButton that the user chose
     * @param breakButton the breakButton that the user chose
     */
    public void timetablesSort(String timeButton, String breakButton) {
        timetablesSortController.timetablesSort(timeButton, breakButton);
    }
}