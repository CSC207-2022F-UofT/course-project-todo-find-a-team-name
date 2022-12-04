package timetable_generator_use_case.application_business;

import entities.InvalidSectionsException;

public interface TimetableGeneratorInputBoundary {
    TimetableGeneratorResponseModel generateTimetable(TimetableGeneratorRequestModel lst, String sessionType) throws InvalidSectionsException;
}
