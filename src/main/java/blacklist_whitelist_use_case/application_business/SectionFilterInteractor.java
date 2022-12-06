package blacklist_whitelist_use_case.application_business;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

/**
 * Blacklist, Whitelist use case Interactor.
 */
public class SectionFilterInteractor implements SectionFilterInputBoundary, Flow.Subscriber<Object> {
    final SectionFilterOutputBoundary presenter;
    private Session session;
    public SectionFilterInteractor(SectionFilterOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * filter() what filter does is it first creates a list of Calendar course entities with a list of courseCodes
     * provided in the requestModel, and then build a list of Constraints from the requestModel data, eventually
     * applying all the constraint to a list of CalendarCourse Domain to modify the sections of each course based on
     * the given constraints. And it makes sure that if the original course has tutorial, lecture, practical sections,
     * the modified courses will have at least one of each section type as well.
     *
     * @param requestModel SectionFilterRequestModel that includes the courseCodes, and corresponding Constraints
     *
     */
    @Override
    public void filter(SectionFilterRequestModel requestModel) {
        ArrayList<String> courseCodes = (ArrayList<String>) this.formatInputString(requestModel.getCourseCodes());
        ArrayList<CalendarCourse> calendarCourses = new ArrayList<>();
        if (requestModel.getCourseCodes().isEmpty()){
            presenter.prepareFailView("No course code has been entered.");
            return;
        }
        if (formatTime(requestModel.getStartTime()) > formatTime(requestModel.getEndTime())){
            presenter.prepareFailView("StartTime Must be BEFORE EndTime");
            return;
        }

            for (String code: courseCodes) {
                if (session.checkCourseCode(code)){
                    calendarCourses.add(session.getCalendarCourse(code));
                } else {
                    presenter.prepareFailView("Course Code Input: "+code + " does not exist!");
                    return;
                }
            }
        ArrayList<CalendarCourse> calendarCoursesCopy = (ArrayList<CalendarCourse>) copyCalendarCourseList(calendarCourses);
        ArrayList<Constraint> constraints = (ArrayList<Constraint>) this.buildConstraints(requestModel);
        for (CalendarCourse course: calendarCoursesCopy) {
            for (Constraint constraint: constraints) {
                if (! constraint.filter(course)){
                    presenter.prepareFailView("Filtering Condition Failed: " + course.getCourseCode()
                            + " is missing mandatory tutorial, lecture, or practical after filtering.");
                    return;
                }
            }
        }
        HashMap<String, List<String>> courseSectionsData = new HashMap<>();
        for (CalendarCourse course: calendarCoursesCopy) {
            courseSectionsData.put(course.getCourseCode(), course.getSectionCodes());
        }
        SectionFilterResponseModel responseModel = new SectionFilterResponseModel(courseSectionsData);

        presenter.prepareSuccessView(responseModel);
    }
    private CalendarCourse copyCalendarCourse(CalendarCourse course){
        return new CalendarCourse(course.getCourseCode(),
                new ArrayList<>(course.getSections()),
                course.getCourseSession(),
                course.getCourseCode(),
                course.getBreadth());
    }

    private List<CalendarCourse> copyCalendarCourseList(List<CalendarCourse> calendarCourses){
        ArrayList<CalendarCourse> copy = new ArrayList<>();
        for (CalendarCourse course: calendarCourses) {
            copy.add(copyCalendarCourse(course));
        }
        return copy;
    }

    /**
     * A helper method that creates a List of Constraints from the given requestModel that could be
     * applied to the course Entities to filter.
     * @param requestModel the SectionFilterRequestModel
     * @return a list of Constraint that
     */
    private List<Constraint> buildConstraints(SectionFilterRequestModel requestModel){
        ArrayList<Constraint> constraints = new ArrayList<>();
        if (! (requestModel.getIsInstructorBlackList().equals("/"))) {
            InstructorConstraint instructorConstraint = new InstructorConstraint(
                    formatInputString(requestModel.getInstructorConstraints()),
                    formatIsBlackList(requestModel.getIsInstructorBlackList()));
            constraints.add(instructorConstraint);
        }
        if (! (requestModel.getIsRoomBlackList().equals("/"))) {
            RoomConstraint roomConstraint = new RoomConstraint(
                    formatInputString(requestModel.getRoomConstraints()),
                    formatIsBlackList(requestModel.getIsRoomBlackList()));
            constraints.add(roomConstraint);
        }
        if (! (requestModel.getIsDayBlackList().equals("/"))) {
            WeekdayConstraint weekdayConstraint = new WeekdayConstraint(
                    requestModel.getDayConstraints(),
                    formatIsBlackList(requestModel.getIsDayBlackList()));
            constraints.add(weekdayConstraint);
        }
        if (! (requestModel.getIsTimeBlackList().equals("/"))) {
            TimeIntervalConstraint timeIntervalConstraint = new TimeIntervalConstraint(
                    formatTime(requestModel.getStartTime()),
                    formatTime(requestModel.getEndTime()),
                    formatIsBlackList(requestModel.getIsTimeBlackList()));
            constraints.add(timeIntervalConstraint);
        }
        return constraints;
    }


    /**
     * A helper method that format the string input from the request model to List data type suitable for creating the
     * Constraints Entities and check for Input Validity.
     * @param  string string of constraints seperated by comma.
     * @return a List of String removing empty string and unnecessary spaces.
     */
    private List<String> formatInputString(String string){
        String[] stringArray = string.split(",");
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String s: stringArray) {
            if (!s.isBlank()) {
                stringArrayList.add(s.trim());
            }
        }
        return stringArrayList;
    }

    /**
     * A helper method that format the string input from the request model to boolean data type suitable for creating the
     * Constraints Entities
     * @param  isBlackList string isBlackList from the SectionFilterRequestModel
     * @return a boolean indicating whether the constraint is a blackList or whiteList Constraint.
     */
    private boolean formatIsBlackList(String isBlackList){
        return "BLACKLIST".equals(isBlackList);

    }

    /**
     * A helper method that format the string time input from the request model to double type to
     * create the TimeInterval Constraints entities.
     * @param time a string representation of the time
     * @return a double representation of time.
     */
    private double formatTime(String time){
        String[] time_to_format = time.split(":");
        double hour = Integer.parseInt(time_to_format[0]);
        double min = Integer.parseInt(time_to_format[1]);
        return hour + min / 60;
    }

    /**
     * Method invoked prior to invoking any other Subscriber
     * methods for the given Subscription. If this method throws
     * an exception, resulting behavior is not guaranteed, but may
     * cause the Subscription not to be established or to be cancelled.
     *
     * <p>Typically, implementations of this method invoke {@code
     * subscription.request} to enable receiving items.
     *
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     * Method invoked with a Subscription's next item.  If this
     * method throws an exception, resulting behavior is not
     * guaranteed, but may cause the Subscription to be cancelled.
     *
     * @param item the item
     */
    @Override
    public void onNext(Object item) {
        if (item instanceof Session){
            this.session = (Session) item;
        }
    }

    /**
     * Method invoked upon an unrecoverable error encountered by a
     * Publisher or Subscription, after which no other Subscriber
     * methods are invoked by the Subscription.  If this method
     * itself throws an exception, resulting behavior is
     * undefined.
     *
     * @param throwable the exception
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     * Method invoked when it is known that no additional
     * Subscriber method invocations will occur for a Subscription
     * that is not already terminated by error, after which no
     * other Subscriber methods are invoked by the Subscription.
     * If this method throws an exception, resulting behavior is
     * undefined.
     */
    @Override
    public void onComplete() {

    }
}


