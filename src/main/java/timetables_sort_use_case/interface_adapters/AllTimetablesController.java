package timetables_sort_use_case.interface_adapters;

import display_timetable_use_case.application_business.DisplayTimetableInputBoundary;
import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.interface_adapters.TimetableViewSectionModel;
import entities.Block;
import entities.Section;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import timetables_sort_use_case.application_business.AllTimetablesInputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller responsible for subscribing to ALlTimetablesScreen publisher and updating TimetableUI
 */
public class AllTimetablesController {
    DisplayTimetableInputBoundary interactor;
    AllTimetablesInputBoundary publisher;

    public AllTimetablesController(DisplayTimetableInputBoundary interactor, AllTimetablesInputBoundary publisher) {
        this.interactor = interactor;
        this.publisher = publisher;
    }

    public void updateSubscribers(int i) {
        publisher.updateSubscribers(i);
    }

}
