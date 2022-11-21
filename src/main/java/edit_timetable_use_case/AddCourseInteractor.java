package edit_timetable_use_case;

import entities.*;
import retrieve_timetable_use_case.TimetableResponseModel;

import java.util.ArrayList;
import java.util.List;

public class AddCourseInteractor implements AddCourseInputBoundary{

    private Timetable timetable;
    private Session session;
    private AddCourseOutputBoundary presenter;

    /**
     * @param request
     */
    @Override
    public void add(EditTimetableRequestModel request) throws InvalidSectionsException {
        boolean success;
        CalendarCourse calCourse = session.getCalendarCourse(request.getCourseCode());
        List<Section> sections = new ArrayList<Section>();
        for (Section section : calCourse.getSections()){
            if (request.getSectionCodes().contains(section.getCode())){
                sections.add(section);
            }
        }
        TimetableCourse course = new TimetableCourse(calCourse.getTitle(), sections, calCourse.getCourseSession(),
                calCourse.getCourseCode(), calCourse.getBreadth());
        timetable.addCourse(course);
        TimetableResponseModel updatedTimetable = RTInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(request.getCourseCode(), request.getSectionCodes(),
                        success, updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }
}
