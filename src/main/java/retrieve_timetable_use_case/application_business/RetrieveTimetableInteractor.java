package retrieve_timetable_use_case.application_business;

import entities.Course;
import entities.Session;
import entities.Timetable;

import java.util.ArrayList;
import java.util.concurrent.Flow;

/**
 * The main interactor used in the retrieve timetable use case. It uses EntityConverter's methods
 * to accept a timetable/session data entity and convert it into the corresponding model, which
 * can then be used and passed between controllers and presenters as necessary.
 * timetable refers to the Timetable currently loaded into the interactor, and any retrieveTimetableCourse
 * calls will search through timetable.
 * session similarly refers to the Session currently into the interavtor, and retrieveCalendarCourse
 * calls will search through session.
 */
public class RetrieveTimetableInteractor implements RetrieveTimetableInputBoundary, Flow.Subscriber<Object> {

    private Timetable timetable;
    private Session session;

    public RetrieveTimetableInteractor(){}


    /**
     * @param requestModel contains the course code of the desired course in timetable.
     * @return a CourseModel of the given course containing equivalent information.
     */
    @Override
    public CourseModel retrieveTimetableCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(timetable.getCourse(requestModel.getCourseCode()));
    }

    /**
     * @param requestModel contains the course code of the desired course in session.
     * @return a CourseModel of the given course containing equivalent information.
     */
    @Override
    public CourseModel retrieveCalendarCourse(RetrieveTimetableRequestModel requestModel) {
        return EntityConverter.generateCourseResponse(session.getCalendarCourse(requestModel.getCourseCode()));
    }

    /**
     * @return a SessionModel containing all information in session.
     */
    @Override
    public SessionModel retrieveSession(){
        return EntityConverter.generateSessionResponse(session);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    /**
     * @return a TimetableModel containing all information in timetable.
     */
    @Override
    public TimetableModel retrieveTimetable(){
        return EntityConverter.generateTimetableResponse(timetable);
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
