package edit_timetable_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * A concrete implementation of the EditCourseInputBoundary.
 * timetable is the Timetable being edited.
 * session is the Session being edited.
 * addInteractor is an AddCourseInputBoundary used in the edit method.
 * removeInteractor is a RemoveCourseInputBoundary used in the edit method.
 * presenter is the presenter responsible for updating the view.
 * retrieveInteractor is a RetrieveTimetableInteractor used in the edit method.
 */
public class EditCourseInteractor implements EditCourseInputBoundary, Flow.Subscriber<Object> {

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

        timetable.addToCourseList(new TimetableCourse(calCourse.getTitle(), newSections,
                calCourse.getCourseSession(), calCourse.getCourseCode(), calCourse.getBreadth()));

        retrieveInteractor.setTimetable(timetable);
        retrieveInteractor.setSession(session);
        TimetableModel updatedTimetable = retrieveInteractor.retrieveTimetable();

        EditTimetableResponseModel response = new EditTimetableResponseModel(request.getCourseCode(), updatedTimetable);
        presenter.prepareView(response);
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor){
        this.retrieveInteractor = retrieveInteractor;
    }


    /**
     * @param subscription a new subscription.
     *                     A method called when the interactor subscribes to a new Subscription. Currently does nothing.
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     * @param item the item passed to the interactor by its publishers, which will be other interactors.
     *             item can either be a Timetable or Session, in which case the interactor's corresponding
     *             instance attribute is updated to match it.
     */
    @Override
    public void onNext(Object item) {
        if (item instanceof Timetable){
            this.timetable = (Timetable) item;
        }
        else if (item instanceof Session){
            this.session = (Session) item;
        }
    }

    /**
     * @param throwable the exception encountered by either the Subscriber or Publisher.
     *                  This method is called when a throwable is thrown by the Subscriber or Publisher, and
     *                  currently does nothing.
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     * Method invoked when no other Subscriber method invocations will occur. Currently does nothing.
     */
    @Override
    public void onComplete() {

    }
}