package display_timetable_use_case.interface_adapters;

import java.util.EventObject;

/**
 * Class representing the information on even performed on timetableView.
 * Instance Attributes:
 *      - selectedCourseCode: code of the course selected by the user in this event
 *      - selectedSectionCode: code of the section selected by the user in this event
 */
public class TimetableViewEvent extends EventObject {
    private final String selectedCourseCode;
    private final String selectedSectionCode;

    /**
     * Constructs a timetable view event from the given TimetableView and selected course and section codes.
     *
     * @param source the object on which the Event initially occurred
     * @throws IllegalArgumentException if source is null
     */
    public TimetableViewEvent(TimetableView source, String selectedCourseCode, String selectedSectionCode) {
        super(source);
        this.selectedCourseCode = selectedCourseCode;
        this.selectedSectionCode = selectedSectionCode;
    }

    /**
     * Returns code of the course selected from the user in this event
     *
     * @return code of the course selected from the user in this event
     */
    public String getSelectedCourseCode() {
        return selectedCourseCode;
    }

    /**
     * Returns code of the section selected from the user in this event
     *
     * @return code of the section selected from the user in this event
     */
    public String getSelectedSectionCode() {
        return selectedSectionCode;
    }
}
