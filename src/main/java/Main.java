import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
import display_timetable_use_case.application_business.DisplayTimetableInteractor;
import display_timetable_use_case.frameworks_and_drivers.TimetableUI;
import display_timetable_use_case.interface_adapters.DisplayTimetableController;
import display_timetable_use_case.interface_adapters.DisplayTimetablePresenter;
import edit_timetable_use_case.application_business.AddCourseInteractor;
import edit_timetable_use_case.application_business.EditCourseInteractor;
import edit_timetable_use_case.application_business.RemoveCourseInteractor;
import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
import edit_timetable_use_case.interface_adapters.EditTimetableController;
import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.application_business.timetable_specific_classes.SaveTimetableInteractor;
import fileio_use_case.application_business.timetable_specific_classes.TimetableGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import fileio_use_case.interface_adapters.SaveTimetableController;
import fileio_use_case.interface_adapters.SessionFileController;
import fileio_use_case.interface_adapters.TimetableFileController;
import generate_overlapping_timetable_use_case.application_business.CalculateSectionHoursInteractor;
import generate_overlapping_timetable_use_case.application_business.OverlapGeneratedTimetableRelayInteractor;
import generate_overlapping_timetable_use_case.application_business.TimeTableMatchInteractor;
import generate_overlapping_timetable_use_case.frameworks_and_drivers.OverlapInputDialog;
import generate_overlapping_timetable_use_case.interface_adapters.OverlapMaxPresenter;
import generate_overlapping_timetable_use_case.interface_adapters.OverlapMaximizationController;
import recommend_br_use_case.application_business.CourseComparatorFactory;
import recommend_br_use_case.application_business.RecommendBRInteractor;
import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
import recommend_br_use_case.interface_adapters.RecommendBRController;
import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.application_business.RetrieveTimetablePresenter;
import retrieve_timetable_use_case.interface_adapters.RetrieveTimetableController;
import screens.MainUI;
import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
import timetable_generator_use_case.frameworks_and_drivers.GenerateTimetableScreen;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorController;
import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;
import timetables_sort_use_case.application_business.AllTimetablesInteractor;
import timetables_sort_use_case.application_business.TimetablesSortInteractor;
import timetables_sort_use_case.frameworks_and_drivers.AllTimetablesScreen;
import timetables_sort_use_case.interface_adapters.AllTimetablesController;
import timetables_sort_use_case.interface_adapters.TimetablesSortController;
import timetables_sort_use_case.interface_adapters.TimetablesSortPresenter;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class Main {

    public static void main(String[] args) {
        /*The frame that the program runs off of. Feel free to re-name or change the dimensions as necessary.*/
        JFrame frame = new JFrame();

        /* I (kai) just started setting up Emily's file io TODO: THIS SHOULD BE REVISED BY EMILY*/
        SessionGateway sessionGateway = new SessionGateway();
        SessionGatewayInteractor sessionGatewayInteractor = new SessionGatewayInteractor(sessionGateway);
        SessionFileController sessionFileController = new SessionFileController(sessionGatewayInteractor);
        TimetableGateway timetableGateway = new TimetableGateway();
        SaveTimetableInteractor saveTimetableInteractor = new SaveTimetableInteractor(timetableGateway);
        SaveTimetableController saveController = new SaveTimetableController(saveTimetableInteractor);
        TimetableGatewayInteractor timetableGatewayInteractor = new TimetableGatewayInteractor(timetableGateway);
        TimetableFileController timetableFileController = new TimetableFileController(timetableGatewayInteractor);

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
        DisplayTimetablePresenter editDisplayPresenter = new DisplayTimetablePresenter();
        DisplayTimetableInteractor editDisplayInteractor = new DisplayTimetableInteractor(editDisplayPresenter);
        DisplayTimetableController editDisplayController = new DisplayTimetableController(editDisplayInteractor);
        EditTimetableScreen editScreen = new EditTimetableScreen(frame, editController, prevPanel, editDisplayController, retrieveTimetableController, saveController);

        removePresenter.setView(editScreen);
        addPresenter.setView(editScreen);
        editPresenter.setView(editScreen);
        editDisplayPresenter.setView(editScreen);
        removePresenter.setView(editScreen);
        addPresenter.setView(editScreen);
        editPresenter.setView(editScreen);
        editDisplayPresenter.setView(editScreen);
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
        SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
        SectionFilterController sectionFilterController1 = new SectionFilterController(sectionFilterInteractor);
        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(generateTimetableScreen, sectionFilterController1);
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

        // Make my presenters and stuff.
        OverlapMaxPresenter overlapPresenter = new OverlapMaxPresenter();
        OverlapGeneratedTimetableRelayInteractor relaySubscriber = new OverlapGeneratedTimetableRelayInteractor(overlapPresenter);
        generatorInteractor.subscribe(relaySubscriber);

        // Make my interactors.
        CalculateSectionHoursInteractor sectionCalculator = new CalculateSectionHoursInteractor();
        TimeTableMatchInteractor timetableMatcher = new TimeTableMatchInteractor(sectionCalculator, overlapPresenter);

        // Make my controller and dialog.
        OverlapMaximizationController overlapMaxController = new OverlapMaximizationController(timetableMatcher);
        OverlapInputDialog overlapInputDialog = new OverlapInputDialog(constraintsInputScreen, overlapMaxController, frame);

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
        TimetableUI timetableUI = new TimetableUI(displayTimetableController, editScreen, overlapInputDialog, saveController);
        displayTimetablePresenter.setView(timetableUI);
        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editScreen, timetableUI, sessionFileController, timetableFileController);

        /* The line below must run after displayPresenter's view has been set to screen.*/
//        editScreen.updateTimetable();
//        frame.add(editScreen);

        /* Final Set up for Use case 3: Overlapping Shenanigans.
         * Please make sure I have a TimetableUI initialized beforehand.
         * **/


        /* The line below must run after displayPresenter's view has been set to screen.*/
//        editScreen.updateTimetable();
//        frame.add(editScreen);

        //  Use case 1 main requirements:
        //      1- TimetablesSortPresenter, 2- TimetablesSortInteractor(Presenter), 3- AllTimetablesPublisher,
        //      4- TimetablesSortController(TimetablesSortInteractor, AllTimetablesInteractor),
        //      5- AllTimetablesController(TimetablesSortInteractor),
        //      6- AllTimetablesScreen(JFrame, MainUI, TimetableUI, OverlapInputDialogue,
        //      TimetablesSortController, AllTimetablesController)
        //      7- TimetablesSortPresenter.setView(AllTimetablesScreen)
        //      8,9- subscribe my interactors to JD's publisher
        //      Comment: no timetables will show in AllTimetablesScreen until my subscriber gets an input

        TimetablesSortPresenter timetablesSortPresenter = new TimetablesSortPresenter();
        TimetablesSortInteractor timetablesSortInteractor = new TimetablesSortInteractor(timetablesSortPresenter);
        AllTimetablesInteractor allTimetablesInteractor = new AllTimetablesInteractor();
        TimetablesSortController timetablesSortController =
                new TimetablesSortController(timetablesSortInteractor);
        AllTimetablesController allTimetablesController =
                new AllTimetablesController(allTimetablesInteractor);
        AllTimetablesScreen allTimetablesScreen = new AllTimetablesScreen(frame, mainUI, timetableUI,
                overlapInputDialog, timetablesSortController, allTimetablesController);
        timetablesSortPresenter.setView(allTimetablesScreen);
        generatorPresenter.setView(allTimetablesScreen);

        generatorInteractor.subscribe(allTimetablesInteractor);
        generatorInteractor.subscribe(timetablesSortInteractor);


        // Set the presenter to include the Dialog.
        overlapPresenter.setDialogToPassTo(overlapInputDialog);
        // Set up my dialog. RUN  AFTER WE HAVE TIMETABLE UI.
        overlapInputDialog.setTimetablePanel(timetableUI);


        // display frame
        frame.add(mainUI);
        frame.setPreferredSize(new Dimension(1280, 720));
        frame.pack();
        frame.setVisible(true);

        // Looks like we make all of our publishers here.
        List<Flow.Subscriber<Object>> timetableAndSessionObservers = new ArrayList<>();
        timetableAndSessionObservers.add(addInteractor);
        timetableAndSessionObservers.add(removeInteractor);
        timetableAndSessionObservers.add(editInteractor);
        timetableAndSessionObservers.add(retrieveTimetableInteractor);
        timetableAndSessionObservers.add(displayTimetableInteractor);
        timetableAndSessionObservers.add(editDisplayInteractor);
        timetableAndSessionObservers.add(recommendBRInteractor);
        timetableAndSessionObservers.add(sectionFilterInteractor);
        timetableAndSessionObservers.add(generatorInteractor);
        timetableAndSessionObservers.add(relaySubscriber);
        timetableAndSessionObservers.add(saveTimetableInteractor);

        // Make the observers here
        List<Flow.Publisher<Object>> timetableAndSessionObservables = new ArrayList<>();
        timetableAndSessionObservables.add(sessionGatewayInteractor);
        timetableAndSessionObservables.add(timetableGatewayInteractor);
        timetableAndSessionObservables.add(timetableMatcher);
        timetableAndSessionObservables.add(allTimetablesInteractor);


        for (Flow.Publisher<Object> observable : timetableAndSessionObservables){
            for (Flow.Subscriber<Object> observer : timetableAndSessionObservers){
                observable.subscribe(observer);
            }
        }

    }

}
