package edit_timetable_use_case.application_business;

public class NotInTimetableException extends Exception{
    NotInTimetableException(String missingData){
        super(missingData + " is not in timetable.");
    }
}
