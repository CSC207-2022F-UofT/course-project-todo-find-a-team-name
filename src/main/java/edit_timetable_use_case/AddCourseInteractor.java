package edit_timetable_use_case;

import entities.*;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class AddCourseInteractor implements AddCourseInputBoundary{

    private Timetable timetable;
    private Session session;
    private AddCourseOutputBoundary presenter;

    public AddCourseInteractor(Timetable timetable, Session session, AddCourseOutputBoundary presenter){
        this.timetable = timetable;
        this.session = session;
        this.presenter = presenter;
    }

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
        timetable.AddToCourseList(course);
        RetrieveTimetableInteractor RTInteractor = new RetrieveTimetableInteractor(timetable, session);
        TimetableModel updatedTimetable = RTInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(request.getCourseCode(), request.getSectionCodes(), updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }
}
