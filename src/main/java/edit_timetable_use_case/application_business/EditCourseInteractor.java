package edit_timetable_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A concrete implementation of the EditCourseInputBoundary.
 * timetable is the Timetable being edited.
 * session is the Session being edited.
 * addInteractor is an AddCourseInputBoundary used in the edit method.
 * removeInteractor is a RemoveCourseInputBoundary used in the edit method.
 * presenter is the presenter responsible for updating the view.
 * retrieveInteractor is a RetrieveTimetableInteractor used in the edit method.
 */
public class EditCourseInteractor implements EditCourseInputBoundary {

    private Timetable timetable;
    private Session session;
    private final EditCourseOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;

    public EditCourseInteractor(EditCourseOutputBoundary presenter){
        this.presenter = presenter;
    }

    /**
     * @param request A EditTimetableRequestModel with the course code of the existing course to be edited
     *                and the section codes of all sections that the edited course should have.
     * @throws InvalidSectionsException if request contains section codes for more than 1 section of any
     * given type (LEC, PRA or TUT)
     * @throws NotInTimetableException if the course code of the course is not contained in the timetable.
     * This method works by calling the RemoveCourseInteractor to remove the existing course and the AddCourseInteractor
     * to add a new version of the course with all the input sections.
     */
    @Override
    public void edit(EditTimetableRequestModel request) throws InvalidSectionsException, NotInTimetableException {
        TimetableCourse ttCourse = null;
        for (TimetableCourse course : timetable.getCourseList()){
            if (course.getCourseCode().equals(request.getCourseCode())){
                ttCourse = course;
            }
        }

        if (ttCourse == null){
            throw new NotInTimetableException(request.getCourseCode());
        }

        timetable.removeCourse(request.getCourseCode());

        CalendarCourse calCourse = session.getCalendarCourse(request.getCourseCode());
        List<Section> newSections = new ArrayList<>();
        for (Section section : calCourse.getSections()){
            if (request.getSectionCodes().contains(section.getCode())){
                newSections.add(section);
            }
        }

        timetable.AddToCourseList(new TimetableCourse(calCourse.getTitle(), newSections,
                calCourse.getCourseSession(), calCourse.getCourseCode(), calCourse.getBreadth()));

        retrieveInteractor.setTimetable(timetable);
        retrieveInteractor.setSession(session);
        TimetableModel updatedTimetable = retrieveInteractor.retrieveTimetable();

        EditTimetableResponseModel response = new EditTimetableResponseModel(request.getCourseCode(), updatedTimetable);
        presenter.prepareView(response);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    @Override
    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor){
        this.retrieveInteractor = retrieveInteractor;
    }
}