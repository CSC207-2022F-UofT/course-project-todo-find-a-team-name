import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import display_timetable_use_case.application_business.DisplayTimetableInteractor;
import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.DisplayTimetablePresenter;
import edit_timetable_use_case.application_business.*;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import recommend_br_use_case.application_business.CourseComparatorFactory;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.application_business.RetrieveTimetablePresenter;
import retrieve_timetable_use_case.interface_adapters.RetrieveTimetableController;
import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
import timetable_generator_use_case.frameworks_and_drivers.GenerateTimetableScreen;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorController;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;

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
         editTimetableScreen.updateSession(), editTimetableScreen.updateTimetable(), and
         editTimetableScreen.setPreviousPanel(previousPanel) before setting it to visible. You may need to use the
         retrieveTimetable use case to do this if you don't already have the view models (although you probably should
         have it already).

         Emily: I've named the SaveTimetableController used in my codeblock saveController. Rename it as you like
         during final integration.
         */

        RetrieveTimetablePresenter retrieveTimetablePresenter = new RetrieveTimetablePresenter();
        RetrieveTimetableInteractor retrieveTimetableInteractor = new RetrieveTimetableInteractor(retrieveTimetablePresenter);
        RetrieveTimetableController retrieveTimetableController = new RetrieveTimetableController(retrieveTimetableInteractor);

        RemoveCoursePresenter removePresenter = new RemoveCoursePresenter();
        RemoveCourseInteractor removeInteractor = new RemoveCourseInteractor(removePresenter);
        removeInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
        AddCoursePresenter addPresenter = new AddCoursePresenter();
        AddCourseInteractor addInteractor = new AddCourseInteractor(addPresenter);
        addInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
        EditCoursePresenter editPresenter = new EditCoursePresenter();
        EditCourseInteractor editInteractor = new EditCourseInteractor(editPresenter);
        EditTimetableController editController = new EditTimetableController(removeInteractor, addInteractor, editInteractor);
        editInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
        JPanel prevPanel = new JPanel();
        DisplayTimetablePresenter displayPresenter = new DisplayTimetablePresenter();
        DisplayTimetableController updateController = new DisplayTimetableController(new DisplayTimetableInteractor(displayPresenter));
        EditTimetableScreen editScreen = new EditTimetableScreen(frame, editController, prevPanel, updateController, retrieveTimetableController, saveController);

        removePresenter.setView(editScreen);
        addPresenter.setView(editScreen);
        editPresenter.setView(editScreen);
        displayPresenter.setView(editScreen);
        removePresenter.setView(editScreen);
        addPresenter.setView(editScreen);
        editPresenter.setView(editScreen);
        displayPresenter.setView(editScreen);
        retrieveTimetablePresenter.setView(editScreen);


        /* Set up for Generating Timetable:
         */
        TimetableGeneratorPresenter generatorPresenter = new TimetableGeneratorPresenter();
        TimetableGeneratorInteractor generatorInteractor = new TimetableGeneratorInteractor(generatorPresenter);
        TimetableGeneratorController generatorController = new TimetableGeneratorController(generatorInteractor);
        GenerateTimetableScreen generateTimetableScreen = new GenerateTimetableScreen(generatorController);

        /*Set up for BlackList/Whitelist:
         */
        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
        SectionFilterInteractor sectionFilterInterator = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInterator);
        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(generateTimetableScreen, sectionFilterController1);
        /*
        Todo: constraintsInputScreen.setPrevPanel(PANEL);
         */
        sectionFilterPresenter.setView(constraintsInputScreen);


        /*
         * Set up for BR recommendation:
         *
         * Emily, Yahya, and anybody who implements publisher for timetable and session should subscribe
         * RecommendBRInteractor
         */
        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter();
        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter, courseComparatorFactory);
        RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);
        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, recommendBRController, editController);
        editScreen.setBRWindow(recommendBRWindow);
        recommendBRPresenter.setView(recommendBRWindow);

        /*
         * This is temporary since timetable view branch haven't merged yet!
         *
         * Emily, Yahya, and anybody who implements publisher for timetable and session should subscribe
         * displayTimetableInteractor
         *
         * Anyone who displays the timetableUI should call updateTimetable() to update view model
         * and setPrevPanel() to set the previous panel to the appropriate JPanel
         *
         * Hans, I need ConstraintInputScreen for mainUI
         * Emily, I need SessionFileController and TimetableFileController for mainUI
         *
         */
        DisplayTimetablePresenter displayTimetablePresenter = new DisplayTimetablePresenter();
        DisplayTimetableInteractor displayTimetableInteractor = new DisplayTimetableInteractor(displayTimetablePresenter);
        DisplayTimetableController displayTimetableController = new DisplayTimetableController(displayTimetableInteractor);
        // TimetableUI timetableUI = new TimetableUI(displayTimetableController, editScreen, timetableFileController);
        // displayTimetablePresenter.setView(timetableUI);

        // MainUI mainUI = new MainUI(frame, constraintInputScreen, editScreen, timetableUI, sessionFileController, timetableFileController);

        /* The line below must run after displayPresenter's view has been set to screen.*/
        editScreen.updateTimetable();
        frame.add(editScreen);
    }

}
