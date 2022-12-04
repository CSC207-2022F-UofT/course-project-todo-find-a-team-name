import display_timetable_use_case.application_business.DisplayTimetableInteractor;
import display_timetable_use_case.frameworks_and_drivers.DisplayTimetableController;
import display_timetable_use_case.frameworks_and_drivers.DisplayTimetablePresenter;
import edit_timetable_use_case.application_business.*;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;

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
         editTimetableScreen.updateSession(sessionViewModel), editTimetableScreen.initializeTimetable()
          before setting it to visible. You may need to use the retrieveTimetable use case to do this if you don't
          already have the view models (although you probably should have it already).
         */

        RetrieveTimetableInputBoundary retrieveInteractor = new RetrieveTimetableInteractor(timetable, session);

        RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
        RemoveCourseInputBoundary removeInteractor = new RemoveCourseInteractor(removePresenter);
        removeInteractor.setRetrieveInteractor(retrieveInteractor);
        AddCourseOutputBoundary addPresenter = new AddCoursePresenter();
        AddCourseInputBoundary addInteractor = new AddCourseInteractor(addPresenter);
        addInteractor.setRetrieveInteractor(retrieveInteractor);
        EditCourseOutputBoundary editPresenter = new EditCoursePresenter();
        EditCourseInputBoundary editInteractor = new EditCourseInteractor(editPresenter);
        EditTimetableController controller = new EditTimetableController(removeInteractor, addInteractor, editInteractor);
        editInteractor.setRetrieveInteractor(retrieveInteractor);
        JPanel prevPanel = new JPanel();
        DisplayTimetablePresenter displayPresenter = new DisplayTimetablePresenter();
        DisplayTimetableController updateController = new DisplayTimetableController(new DisplayTimetableInteractor(displayPresenter));
        EditTimetableScreen screen = new EditTimetableScreen(frame, controller, prevPanel, updateController);

        screen.setBRWindow(recommendBRWindow);
        removePresenter.setView(screen);
        addPresenter.setView(screen);
        editPresenter.setView(screen);
        displayPresenter.setView(screen);
        /* The line below must run after displayPresenter's view has been set to screen.*/
        screen.initializeTimetable();
        frame.add(screen);
    }

}
