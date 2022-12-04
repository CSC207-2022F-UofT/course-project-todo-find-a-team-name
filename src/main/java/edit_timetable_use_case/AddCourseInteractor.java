package edit_timetable_use_case;

import entities.*;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.List;

public class AddCourseInteractor implements AddCourseInputBoundary{

    private Timetable timetable;
    private Session session;
    private final AddCourseOutputBoundary presenter;

    public AddCourseInteractor(AddCourseOutputBoundary presenter){
        this.presenter = presenter;
    }

    /**
     * @param request The request model given by the controller, containing a course code and section codes
     *                that specify the course to be added, as well as the sections from the course to be added.
     *                This method creates a new TimetableCourse object and adds it to the Timetable.
     * @throws InvalidSectionsException when the sections given create an invalid TimetableCourse (ie: more than 1
     * TUT, PRA, or LEC session was in the list of course codes).
     */
    @Override
    public void add(EditTimetableRequestModel request) throws InvalidSectionsException {
        CalendarCourse calCourse = session.getCalendarCourse(request.getCourseCode());
        List<Section> sections = new ArrayList<>();

        for (Section section : calCourse.getSections()){
            if (request.getSectionCodes().contains(section.getCode())){
                sections.add(section);
            }
        }
        TimetableCourse course = new TimetableCourse(calCourse.getTitle(), sections, calCourse.getCourseSession(),
                calCourse.getCourseCode(), calCourse.getBreadth());
        timetable.addToCourseList(course);
        RetrieveTimetableInteractor RTInteractor = new RetrieveTimetableInteractor(timetable, session);
        TimetableModel updatedTimetable = RTInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(request.getCourseCode(), request.getSectionCodes(), updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }

    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }
}
