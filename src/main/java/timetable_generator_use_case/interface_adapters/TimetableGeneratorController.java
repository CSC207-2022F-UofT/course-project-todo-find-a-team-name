package timetable_generator_use_case.interface_adapters;

import timetable_generator_use_case.application_business.TimetableGeneratorInputBoundary;
import timetable_generator_use_case.application_business.TimetableGeneratorRequestModel;

import java.util.HashMap;
import java.util.List;

/**
 * TimetableGeneratorController sends information to the TimetableGeneratorInteractor in the form of a
 * TimetableGeneratorRequestModel.
 */

public class TimetableGeneratorController {
    private final TimetableGeneratorInputBoundary interactor;

    public TimetableGeneratorController(TimetableGeneratorInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Creates: CourseModel based on constraints, creates TimetableGeneratorRequestModel,
     * Initializes TimetableGeneratorInteractor
     */
    public void generateTimetable(HashMap<String, List<String>> courses) {
        TimetableGeneratorRequestModel requestModel = new TimetableGeneratorRequestModel(courses);
        this.interactor.generateTimetable(requestModel);
    }

}