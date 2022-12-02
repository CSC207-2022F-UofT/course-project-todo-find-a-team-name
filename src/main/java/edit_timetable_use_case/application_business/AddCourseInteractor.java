package edit_timetable_use_case.application_business;

import entities.*;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * A concrete implementation of the AddCourseInputBoundary, used in the add course use case.
 * timetable is the timetable being edited.
 * session is the session corresponding to the timetable, and determines the sections available to choose from.
 * presenter is the AddCourseOutputBoundary that updates in response to the user's input.
 */
public class AddCourseInteractor implements AddCourseInputBoundary, Flow.Subscriber<Object> {

    private Timetable timetable;
    private Session session;
    private final AddCourseOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;

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
        retrieveInteractor.setTimetable(timetable);
        retrieveInteractor.setSession(session);
        TimetableModel updatedTimetable = retrieveInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(request.getCourseCode(), updatedTimetable);
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

    /**
     * @param retrieveInteractor the RetrieveTimetableInputBoundary used to create a view model of
     *                           the updated timetable.
     */
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
