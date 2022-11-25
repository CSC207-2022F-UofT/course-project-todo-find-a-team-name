package edit_timetable_use_case;

import entities.*;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the AddCourseInputBoundary, used in the add course use case.
 * timetable is the timetable being edited.
 * session is the session corresponding to the timetable, and determines the sections available to choose from.
 * presenter is the AddCourseOutputBoundary that updates in response to the user's input.
 */
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
        timetable.AddToCourseList(course);
        RetrieveTimetableInteractor RTInteractor = new RetrieveTimetableInteractor(timetable, session);
        TimetableModel updatedTimetable = RTInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(request.getCourseCode(), request.getSectionCodes(), updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }

    /**
     * @param timetable Updates the timetable used by the interactor.
     */
    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * @param session Updates the session used by the interactor.
     */
    @Override
    public void setSession(Session session) {
        this.session = session;
    }
}
