package timetable_generator_use_case.application_business;

import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;

public class TimetableGeneratorResponseModel {
    private final ArrayList<TimetableModel> generatedTimetables;

    public TimetableGeneratorResponseModel(ArrayList<TimetableModel> generatedTimetables) {
        this.generatedTimetables = generatedTimetables;
    }

    public ArrayList<TimetableModel> getGeneratedTimetables() {
        return this.generatedTimetables;
    }
}