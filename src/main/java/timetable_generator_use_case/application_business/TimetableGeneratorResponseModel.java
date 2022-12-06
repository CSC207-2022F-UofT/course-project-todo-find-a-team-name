package timetable_generator_use_case.application_business;

import retrieve_timetable_use_case.TimetableModel;

import java.util.List;

public class TimetableGeneratorResponseModel {
    private final List<TimetableModel> generatedTimetables;

    public TimetableGeneratorResponseModel(List<TimetableModel> generatedTimetables) {
        this.generatedTimetables = generatedTimetables;
    }

    public List<TimetableModel> getGeneratedTimetables() {
        return this.generatedTimetables;
    }
}