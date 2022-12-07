package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.interface_adapters.TimetableViewSectionModel;
import entities.Block;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;
import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller responsible for subscribing to ALlTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    DisplayTimetableInputBoundary interactor;
    AllTimetablesInputBoundary publisher;
    TimetableModel[] timetables;
    public AllTimetablesController(DisplayTimetableInputBoundary interactor, AllTimetablesInputBoundary publisher) {
        this.interactor = interactor;
        this.publisher = publisher;
        this.timetables = null;
    }
    public void setTTUI(int i) {
        publisher.updateTimetable(timetables[i]);
    }
    public void setTimetables(TimetableModel[] timetables) {
        this.timetables = timetables;
    }
}
