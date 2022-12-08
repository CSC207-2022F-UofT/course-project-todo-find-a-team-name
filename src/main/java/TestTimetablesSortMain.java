//import blacklist_whitelist_use_case.application_business.SectionFilterInteractor;
//import blacklist_whitelist_use_case.frameworks_and_drivers.ConstraintsInputScreen;
//import blacklist_whitelist_use_case.interface_adapters.SectionFilterController;
//import blacklist_whitelist_use_case.interface_adapters.SectionFilterPresenter;
//import display_timetable_use_case.application_business.DisplayTimetableInteractor;
//import display_timetable_use_case.frameworks_and_drivers.*;
//import display_timetable_use_case.interface_adapters.DisplayTimetableController;
//import display_timetable_use_case.interface_adapters.DisplayTimetablePresenter;
//import display_timetable_use_case.frameworks_and_drivers.TimetableViewBlockModel;
//import edit_timetable_use_case.application_business.AddCourseInteractor;
//import edit_timetable_use_case.application_business.EditCourseInteractor;
//import edit_timetable_use_case.application_business.RemoveCourseInteractor;
//import edit_timetable_use_case.frameworks_and_drivers.EditTimetableScreen;
//import edit_timetable_use_case.interface_adapters.AddCoursePresenter;
//import edit_timetable_use_case.interface_adapters.EditCoursePresenter;
//import edit_timetable_use_case.interface_adapters.EditTimetableController;
//import edit_timetable_use_case.interface_adapters.RemoveCoursePresenter;
//import entities.*;
//import fileio_use_case.application_business.FileImportRequestModel;
//import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
//import fileio_use_case.application_business.timetable_specific_classes.TimetableFileImportInputBoundary;
//import fileio_use_case.frameworks_and_drivers.SessionGateway;
//import fileio_use_case.interface_adapters.SessionFileController;
//import fileio_use_case.interface_adapters.TimetableFileController;
//import org.json.simple.parser.ParseException;
//import overlap_crap_fix_locations_later.CalculateSectionHoursInteractor;
//import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
//import overlap_crap_fix_locations_later.InputBoundaries.SectionHoursInputBoundary;
//import overlap_crap_fix_locations_later.OverlapInputDialog;
//import overlap_crap_fix_locations_later.OverlapMaximizationController;
//import overlap_crap_fix_locations_later.TimeTableMatchInteractor;
//import overlap_crap_fix_locations_later.ViewModels.ModelToOverlapViewModelConverter;
//import overlap_crap_fix_locations_later.ViewModels.OverlapTimetableViewModel;
//import overlap_crap_fix_locations_later.presenters.OverlapMaxPresenter;
//import recommend_br_use_case.application_business.CourseComparatorFactory;
//import recommend_br_use_case.application_business.RecommendBRInteractor;
//import recommend_br_use_case.application_business.TargetTimeCourseComparatorFactory;
//import recommend_br_use_case.frameworks_and_drivers.RecommendBRWindow;
//import recommend_br_use_case.interface_adapters.RecommendBRController;
//import recommend_br_use_case.interface_adapters.RecommendBRPresenter;
//import retrieve_timetable_use_case.application_business.EntityConverter;
//import retrieve_timetable_use_case.application_business.RetrieveTimetableInteractor;
//import retrieve_timetable_use_case.application_business.TimetableModel;
//import screens.MainUI;
//import timetable_generator_use_case.application_business.TimetableGeneratorInteractor;
//import timetable_generator_use_case.frameworks_and_drivers.GenerateTimetableScreen;
//import timetable_generator_use_case.interface_adapters.TimetableGeneratorController;
//import timetable_generator_use_case.interface_adapters.TimetableGeneratorPresenter;
//import timetables_sort_use_case.application_business.AllTimetablesInteractor;
//import timetables_sort_use_case.application_business.TimetablesSortInteractor;
//import timetables_sort_use_case.frameworks_and_drivers.AllTimetablesScreen;
//import timetables_sort_use_case.interface_adapters.AllTimetablesController;
//import timetables_sort_use_case.interface_adapters.TimetablesSortController;
//import timetables_sort_use_case.interface_adapters.TimetablesSortPresenter;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.List;
//
//public class TestTimetablesSortMain {
//    public static void main(String[] args) throws InvalidSectionsException {
//        java.util.List<TimetableViewCourseModel> courseData = new ArrayList<>();
//        java.util.List<TimetableViewSectionModel> sectionModels1 = new ArrayList<>();
//
//        java.util.List<TimetableViewBlockModel> blockModels1 = new ArrayList<>();
//        blockModels1.add(new TimetableViewBlockModel(0, 11, 12));
//        blockModels1.add(new TimetableViewBlockModel(4, 11, 12));
//        sectionModels1.add(new TimetableViewSectionModel("LEC0101", blockModels1));
//
//        java.util.List<TimetableViewBlockModel> blockModels2 = new ArrayList<>();
//        blockModels2.add(new TimetableViewBlockModel(2, 11, 12));
//        sectionModels1.add(new TimetableViewSectionModel("TUT0101", blockModels2));
//
//        courseData.add(new TimetableViewCourseModel("CSC236H1", sectionModels1));
//
//
//        java.util.List<TimetableViewSectionModel> sectionModels2 = new ArrayList<>();
//
//        java.util.List<TimetableViewBlockModel> blockModels3 = new ArrayList<>();
//        blockModels3.add(new TimetableViewBlockModel(1, 16, 17));
//        blockModels3.add(new TimetableViewBlockModel(4, 16, 17));
//        sectionModels2.add(new TimetableViewSectionModel("LEC0401", blockModels3));
//
//        List<TimetableViewBlockModel> blockModels4 = new ArrayList<>();
//        blockModels4.add(new TimetableViewBlockModel(0, 14, 16));
//        sectionModels2.add(new TimetableViewSectionModel("TUT0301", blockModels4));
//
//        courseData.add(new TimetableViewCourseModel("CSC207H1", sectionModels2));
//
//        TimetableViewModel timetableViewModel = new TimetableViewModel(courseData);
//        TimetableViewModel[] timetableViewModels = new TimetableViewModel[10];
//
//        for (int i = 0; i < 10; i++) {
//            timetableViewModels[i] = timetableViewModel;
//        }
//
//        JFrame frame = new JFrame();
//
//        RecommendBRPresenter recommendBRPresenter = new RecommendBRPresenter();
//        CourseComparatorFactory courseComparatorFactory = new TargetTimeCourseComparatorFactory();
//        RecommendBRInteractor recommendBRInteractor = new RecommendBRInteractor(recommendBRPresenter,
//                courseComparatorFactory);
//        RecommendBRController recommendBRController = new RecommendBRController(recommendBRInteractor);
//
//        AddCoursePresenter addCoursePresenter = new AddCoursePresenter();
//        AddCourseInteractor addCourseInteractor = new AddCourseInteractor(addCoursePresenter);
//        EditCoursePresenter editCoursePresenter = new EditCoursePresenter();
//        EditCourseInteractor editCourseInteractor = new EditCourseInteractor(editCoursePresenter);
//        RemoveCoursePresenter removeCoursePresenter = new RemoveCoursePresenter();
//        RemoveCourseInteractor removeCourseInteractor = new RemoveCourseInteractor(removeCoursePresenter);
//        EditTimetableController editTimetableController = new EditTimetableController(removeCourseInteractor, addCourseInteractor, editCourseInteractor);
//
//        RetrieveTimetableInteractor retrieveTimetableInteractor = new RetrieveTimetableInteractor();
//        addCourseInteractor.setRetrieveInteractor(retrieveTimetableInteractor);
//
//        DisplayTimetablePresenter displayTimetablePresenter1 = new DisplayTimetablePresenter();
//        DisplayTimetableInteractor displayTimetableInteractor1 = new DisplayTimetableInteractor(displayTimetablePresenter1);
//        DisplayTimetableController displayTimetableController1 = new DisplayTimetableController(displayTimetableInteractor1);
//
//        RecommendBRWindow recommendBRWindow = new RecommendBRWindow(frame, recommendBRController, editTimetableController);
//        EditTimetableScreen editTimetableScreen = new EditTimetableScreen(frame, editTimetableController, null, displayTimetableController1);
//        displayTimetablePresenter1.setView(editTimetableScreen);
//        addCoursePresenter.setView(editTimetableScreen);
//        editCoursePresenter.setView(editTimetableScreen);
//        addCoursePresenter.setView(editTimetableScreen);
//        removeCoursePresenter.setView(editTimetableScreen);
//
//        editTimetableScreen.setBRWindow(recommendBRWindow);
//        editTimetableScreen.updateTimetable(new TimetableViewModel(new ArrayList<>()));
//
//        Block block1 = new Block("MO", "11:00", "12:00", "");
//        Block block2 = new Block("FR", "11:00", "12:00", "");
//        java.util.List<Block> blocks1 = new ArrayList<>();
//        blocks1.add(block1);
//        blocks1.add(block2);
//
//        Block block3 = new Block("WE", "11:00", "12:00", "");
//        java.util.List<Block> blocks2 = new ArrayList<>();
//        blocks2.add(block3);
//
//        Block block4 = new Block("TU", "16:00", "17:00", "");
//        Block block5 = new Block("FR", "16:00", "17:00", "");
//        java.util.List<Block> blocks3 = new ArrayList<>();
//        blocks3.add(block4);
//        blocks3.add(block5);
//
//        Block block6 = new Block("MO", "14:00", "16:00", "");
//        java.util.List<Block> blocks4 = new ArrayList<>();
//        blocks4.add(block6);
//
//        Section s1 = new Section("LEC0101", "", blocks1);
//        Section s2 = new Section("TUT0101", "", blocks2);
//
//        Section s3 = new Section("LEC0401", "", blocks3);
//        Section s4 = new Section("TUT0301", "", blocks4);
//
//        java.util.List<Section> sections1 = new ArrayList<>();
//        sections1.add(s1);
//        sections1.add(s2);
//        List<Section> sections2 = new ArrayList<>();
//        sections2.add(s3);
//        sections2.add(s4);
//
//        TimetableCourse c1;
//        TimetableCourse c2;
//
//        try {
//            c1 = new TimetableCourse("some title", sections1, "", "CSC236H1", "");
//            c2 = new TimetableCourse("some other title", sections2, "", "CSC207H1", "");
//        } catch (InvalidSectionsException e) {
//            throw new RuntimeException(e);
//        }
//
//        ArrayList<TimetableCourse> courses = new ArrayList<>();
//        courses.add(c1);
//        courses.add(c2);
//        Timetable timetable = new Timetable(courses, "F");
//
//        recommendBRPresenter.setView(recommendBRWindow);
//        recommendBRInteractor.setTimetable(timetable);
//
//        SessionGateway sessionGateway = new SessionGateway();
//        Session fSession;
//        try {
//            fSession = sessionGateway.readFromFile("src/main/resources/courses_cleaned.json", "F");
//        } catch (IOException | ParseException | InvalidSectionsException e) {
//            throw new RuntimeException(e);
//        }
//        addCourseInteractor.setSession(fSession);
//        addCourseInteractor.setTimetable(timetable);
//        removeCourseInteractor.setTimetable(timetable);
//        editCourseInteractor.setSession(fSession);
//        editCourseInteractor.setTimetable(timetable);
//        recommendBRInteractor.onNext(fSession);
//
//        SectionFilterPresenter sectionFilterPresenter = new SectionFilterPresenter();
//        SectionFilterInteractor sectionFilterInteractor = new SectionFilterInteractor(sectionFilterPresenter);
//        SectionFilterController sectionFilterController = new SectionFilterController(sectionFilterInteractor);
//        TimetableGeneratorPresenter timetableGeneratorPresenter = new TimetableGeneratorPresenter();
//        TimetableGeneratorInteractor timetableGeneratorInteractor =
//                new TimetableGeneratorInteractor(timetableGeneratorPresenter);
//        TimetableGeneratorController timetableGeneratorController = new TimetableGeneratorController(timetableGeneratorInteractor);
//        GenerateTimetableScreen generateTimetableScreen = new GenerateTimetableScreen(timetableGeneratorController);
//        ConstraintsInputScreen constraintsInputScreen = new ConstraintsInputScreen(generateTimetableScreen, sectionFilterController);
//        sectionFilterPresenter.setView(constraintsInputScreen);
//
//        DisplayTimetablePresenter displayTimetablePresenter2 = new DisplayTimetablePresenter();
//        DisplayTimetableInteractor displayTimetableInteractor2 = new DisplayTimetableInteractor(displayTimetablePresenter2);
//        DisplayTimetableController displayTimetableController2 = new DisplayTimetableController(displayTimetableInteractor2);
//        ArrayList<Timetable> timetables = new ArrayList<Timetable>();
//
//        Block testBlock = new Block("Monday", "18:00", "21:00", "Castle Badr");
//        ArrayList<Block> testBlockList = new ArrayList<>();
//        testBlockList.add(testBlock);
//
//        Section testSection = new Section("LEC0101", "Mario", testBlockList);
//        ArrayList<Section> testSectionList = new ArrayList<>();
//        testSectionList.add(testSection);
//
//        ArrayList<OverlapTimetableViewModel> timetableViewModels1;
//        try {
//            // Make a test course.
//            TimetableCourse testCourse = new TimetableCourse("C1", testSectionList, "S", "CLA215", "4");
//            ArrayList<TimetableCourse> testTimetableCourseList = new ArrayList<>();
//            testTimetableCourseList.add(testCourse);
//
//            // Now make a test timetable.
//            Timetable testTimetable = new Timetable(testTimetableCourseList, "S");
//            ArrayList<Timetable> testTimetableList = new ArrayList<Timetable>();
//            testTimetableList.add(testTimetable);
//
//            // SETUP for InputDialog creation
//            // Convert test timetable to appropriate view model
//            timetableViewModels1 = new ArrayList<>();
//            for (Timetable timetable2 : testTimetableList) {
//                timetableViewModels1.add(
//                        ModelToOverlapViewModelConverter.convertTimetableModel(
//                                EntityConverter.generateTimetableResponse(timetable2))
//                );
//            }
//        } catch (InvalidSectionsException e) {
//            throw new RuntimeException(e);
//        }
//        OverlapMaxPresenter presenter = new OverlapMaxPresenter();
//        SectionHoursInputBoundary sectionHoursInteractor = new CalculateSectionHoursInteractor();
//        TimeTableMatchInteractor timeTableMatchInteractor = new TimeTableMatchInteractor(sectionHoursInteractor, presenter);
//        OverlapMaxInputBoundary overlapMaxController = new OverlapMaximizationController(timeTableMatchInteractor);
//        TimetableView timetableV = new TimetableView(1000, 1000, timetableViewModel);
//        OverlapInputDialog overlapInputDialog = new OverlapInputDialog(timetableViewModels1, timetableV, constraintsInputScreen, overlapMaxController);
//        displayTimetableInteractor2.setTimetable(timetable);
//        TimetableFileImportInputBoundary timetableFileImportInputBoundary = new TimetableFileImportInputBoundary() {
//            @Override
//            public TimetableModel readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException {
//                return null;
//            }
//
//            @Override
//            public void timetableToFile(TimetableModel timetableRequestModel, String sessionType) throws InvalidSectionsException {
//
//            }
//        };
//        TimetableFileController timetableFileController = new TimetableFileController(timetableFileImportInputBoundary);
//        TimetableUI timetableUI = new TimetableUI(displayTimetableController2, editTimetableScreen, overlapInputDialog, timetableFileController);
//        displayTimetablePresenter2.setView(timetableUI);
//
//        displayTimetableInteractor1.setTimetable(timetable);
//
//        SessionGateway gateway = new SessionGateway();
//        SessionGatewayInteractor sessionGatewayInteractor = new SessionGatewayInteractor(gateway);
//        SessionFileController sessionFileController = new SessionFileController(sessionGatewayInteractor);
//
//        MainUI mainUI = new MainUI(frame, constraintsInputScreen, editTimetableScreen, timetableUI, sessionFileController);
//        timetableUI.setPrevPanel(mainUI);
//        editTimetableScreen.setPreviousPanel(mainUI);
//
//
//        mainUI.setPreferredSize(new Dimension(1280, 720));
//
//
//        Timetable[] timetables1 = new Timetable[10];
//
//        for (int i = 0; i < 10; i++) {
//            timetables1[i] = timetable;
//        }
//
//
//        //  Use case 1 main requirements:
//        //      1- TimetablesSortPresenter, 2- TimetablesSortInteractor(Presenter), 3- AllTimetablesPublisher,
//        //      4- TimetablesSortController(TimetablesSortInteractor, AllTimetablesInteractor),
//        //      5- AllTimetablesController(TimetablesSortInteractor),
//        //      6- AllTimetablesScreen(JFrame, MainUI, TimetableUI, OverlapInputDialogue,
//        //      TimetablesSortController, AllTimetablesController)
//        //      7- TimetablesSortPresenter.setView(AllTimetablesScreen)
//        //      8,9- subscribe my interactors to JD's publisher
//
//        TimetablesSortPresenter timetablesSortPresenter = new TimetablesSortPresenter();
//        TimetablesSortInteractor timetablesSortInteractor = new TimetablesSortInteractor(timetablesSortPresenter);
//        AllTimetablesInteractor allTimetablesInteractor = new AllTimetablesInteractor();
//        TimetablesSortController timetablesSortController =
//                new TimetablesSortController(timetablesSortInteractor);
//        timetablesSortInteractor.setTimetables(timetables1); // this is only for testing, it should automatically
//        // update when its publisher publishes // comment #2: removed it since i dont use it anywhere
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
//
//
//        frame.add(allTimetablesScreen);
//        frame.pack();
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setVisible(true);
//
//    }
//}
