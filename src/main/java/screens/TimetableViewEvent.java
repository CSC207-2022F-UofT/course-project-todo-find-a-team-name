package screens;

import java.util.EventObject;

public class TimetableViewEvent extends EventObject {
    private final String selectedCourseCode;
    private final String selectedLectureCode;

    /**
     * Constructs a timetable view event
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TimetableViewEvent(TimetableView source, String selectedCourseCode, String selectedLectureCode) {
        super(source);
        this.selectedCourseCode = selectedCourseCode;
        this.selectedLectureCode = selectedLectureCode;
    }

    public String getSelectedCourseCode() {
        return selectedCourseCode;
    }

    public String getSelectedLectureCode() {
        return selectedLectureCode;
    }
}
