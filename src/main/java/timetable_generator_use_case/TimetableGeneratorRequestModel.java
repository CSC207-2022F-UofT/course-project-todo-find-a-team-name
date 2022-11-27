package timetable_generator_use_case;

import entities.Timetable;

import java.util.ArrayList;

public class TimetableGeneratorRequestModel {
    private ArrayList<Timetable> timetables;
    public TimetableGeneratorRequestModel(ArrayList<Timetable> timetables) {
        this.timetables = timetables;
    }
    public ArrayList<Timetable> getTimetables() {
        return this.timetables;
    }
}
