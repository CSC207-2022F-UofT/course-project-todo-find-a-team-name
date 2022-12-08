import display_timetable_use_case.application_business.DisplayTimetableInteractor;
import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.DisplayTimetablePresenter;
import edit_timetable_use_case.application_business.*;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
import timetables_sort_use_case.application_business.AllTimetablesInteractor;
import timetables_sort_use_case.application_business.TimetablesSortInteractor;
import timetables_sort_use_case.frameworks_and_drivers.AllTimetablesScreen;
import timetables_sort_use_case.interface_adapters.AllTimetablesController;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;
import timetables_sort_use_case.interface_adapters.TimetablesSortPresenter;

import javax.swing.*;

public class Main {

    public static void main(String[] args) {
        /*The frame that the program runs off of. Feel free to re-name or change the dimensions as necessary.*/
        JFrame frame = new JFrame();

        /*A combined initialization of the Edit Timetable and Retrieve Timetable use cases. This block should be after
        the RecommendBRWindow is created,

         * Kai: note that editTimetableScreen needs a RecommendBRWindow on the final merge.
         (it's called recommendBRWindow in the code, but feel free to rename it.)

         * Yahya, Emily, and anyone else that creates a Timetable/Session publisher: addCourseInteractor,
         * removeCourseInteractor, editCourseInteractor and retrieveTimetableInteractor all need to be included as
         * subscribers and updated as appropriate. (displayTimetableInteractor should also be included, but that's
         more Kai's side of things.)

         * Yahya and anyone that opens the timetable editor (Emily?): make sure that the displayTimetableInteractor is
         updated with the appropriate timetable and session before pulling up the edit timetable screen, and
         make sure to call editTimetableScreen.updateTimetable(ttViewModel),
         editTimetableScreen.updateSession(sessionViewModel), editTimetableScreen.initializeTimetable(), and
         editTimetableScreen.setPreviousPanel(previousPanel) before setting it to visible. You may need to use the
         retrieveTimetable use case to do this if you don't already have the view models (although you probably should
         have it already).
         */

        RetrieveTimetableInputBoundary retrieveInteractor = new RetrieveTimetableInteractor();

        RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
        RemoveCourseInteractor removeInteractor = new RemoveCourseInteractor(removePresenter);
        removeInteractor.setRetrieveInteractor(retrieveInteractor);
        AddCoursePresenter addPresenter = new AddCoursePresenter();
        AddCourseInteractor addInteractor = new AddCourseInteractor(addPresenter);
        addInteractor.setRetrieveInteractor(retrieveInteractor);
        EditCoursePresenter editPresenter = new EditCoursePresenter();
        EditCourseInteractor editInteractor = new EditCourseInteractor(editPresenter);
        EditTimetableController controller = new EditTimetableController(removeInteractor, addInteractor, editInteractor);
        editInteractor.setRetrieveInteractor(retrieveInteractor);
        JPanel prevPanel = new JPanel();
        DisplayTimetablePresenter displayPresenter = new DisplayTimetablePresenter();
        DisplayTimetableController updateController = new DisplayTimetableController(new DisplayTimetableInteractor(displayPresenter));
//        EditTimetableScreen screen = new EditTimetableScreen(frame, controller, prevPanel, updateController);

//        screen.setBRWindow(recommendBRWindow);
//        removePresenter.setView(screen);
//        addPresenter.setView(screen);
//        editPresenter.setView(screen);
//        displayPresenter.setView(screen);

        //  Use case 1 main requirements:
        //      1- TimetablesSortPresenter, 2- TimetablesSortInteractor(Presenter), 3- AllTimetablesPublisher,
        //      4- TimetablesSortController(TimetablesSortInteractor, AllTimetablesInteractor),
        //      5- AllTimetablesController(TimetablesSortInteractor),
        //      6- AllTimetablesScreen(JFrame, MainUI, TimetableUI, OverlapInputDialogue,
        //      TimetablesSortController, AllTimetablesController)
        //      7- TimetablesSortPresenter.setView(AllTimetablesScreen)
        //      8,9- subscribe my interactors to JD's publisher
        //      Comment: no timetables will show in AllTimetablesScreen until my subscriber gets an input
        //      Comment2: lots of things i depend on are not in main yet, uncomment it when they are here
//        TimetablesSortPresenter timetablesSortPresenter = new TimetablesSortPresenter();
//        TimetablesSortInteractor timetablesSortInteractor = new TimetablesSortInteractor(timetablesSortPresenter);
//        AllTimetablesInteractor allTimetablesInteractor = new AllTimetablesInteractor();
//        TimetablesSortController timetablesSortController =
//                new TimetablesSortController(timetablesSortInteractor);
//        AllTimetablesController allTimetablesController =
//                new AllTimetablesController(allTimetablesInteractor);
//        AllTimetablesScreen allTimetablesScreen = new AllTimetablesScreen(frame, mainUI, timetableUI,
//                overlapInputDialog, timetablesSortController, allTimetablesController);
//        allTimetablesScreen.updateTimetables(timetableViewModels); // this is only for testing, updateTimetables
//        // should be called by JD when he displays my screen
//        timetablesSortPresenter.setView(allTimetablesScreen);
//
//        timetableGeneratorInteractor.subscribe(allTimetablesInteractor);
//        timetableGeneratorInteractor.subscribe(timetablesSortInteractor);
        /* The line below must run after displayPresenter's view has been set to screen.*/
//        screen.updateTimetable();
//        frame.add(screen);
    }

}
