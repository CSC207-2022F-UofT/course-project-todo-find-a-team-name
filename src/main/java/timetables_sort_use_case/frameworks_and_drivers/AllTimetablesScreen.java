package timetables_sort_use_case.frameworks_and_drivers;

import display_timetable_use_case.frameworks_and_drivers.TimetableView;
import display_timetable_use_case.frameworks_and_drivers.TimetableViewModel;
import timetables_sort_use_case.interface_adapters.AllTimetablesView;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class AllTimetablesScreen extends JPanel implements ActionListener, AllTimetablesView {

    private final JFrame frame;
    private TimetableViewModel[] timetableViewModels;
    private TimetableView[] ttViews;
    private final JPanel timetablesPanel;
    private TimetablesSortMenu timetablesSortMenu;
    private final TimetablesSortController controller;
    public AllTimetablesScreen(JFrame frame, TimetablesSortController controller) {
        this.frame = frame;
        this.controller = controller;
        this.ttViews = null;
        this.setLayout(new BorderLayout());
        JPanel timetablesPanel = new JPanel();
        this.timetablesPanel = timetablesPanel;
        this.timetablesSortMenu = null;

        JPanel top = new JPanel();
        top.setSize(100, 100);
        JButton sort = new JButton("Sort");
        JButton mainMenu = new JButton("Main Menu");
        top.add(sort);
        top.add(mainMenu);
        sort.addActionListener(this);
        mainMenu.addActionListener(this);

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
        System.out.println("Clicked " + e.getActionCommand());

        if (e.getActionCommand().equals("Sort")) {
            openTimetablesSortMenu();
        } else if (e.getActionCommand().equals("mainMenu")) {

        } else {
            int i = e.getActionCommand().length() - 1;
            TimetableViewModel timetable = this.timetableViewModels[i];

        }
    }

    public void openTimetablesSortMenu() {
        if (timetablesSortMenu != null) {
            timetablesSortMenu.setVisible(false);
            frame.remove(timetablesSortMenu);
        }
        timetablesSortMenu = new TimetablesSortMenu(this.frame, this);
        frame.add(timetablesSortMenu);
        this.setVisible(false);
        timetablesSortMenu.setVisible(true);
        this.frame.pack();
    }

    public void updateTimetables(TimetableViewModel[] timetableViewModels) {
        this.timetableViewModels = timetableViewModels;
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
        this.setVisible(true);
    }

    public void timetablesSort(String timeButton, String breakButton) {
        controller.timetablesSort(timeButton, breakButton);
    }

}

