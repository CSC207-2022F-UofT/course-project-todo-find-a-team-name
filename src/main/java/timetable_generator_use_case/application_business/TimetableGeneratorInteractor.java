package timetable_generator_use_case.application_business;

import entities.*;
import generate_timetable_course_use_case.TimetableCourseGenerator;
//import retrieve_timetable_use_case.EntityConverter;
//import retrieve_timetable_use_case.TimetableModel;
import retrieve_timetable_use_case.application_business.EntityConverter;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.*;
import java.util.concurrent.Flow;
import java.util.concurrent.Flow.Subscriber;

/**
 * Generates all possible Timetables
 */

public class TimetableGeneratorInteractor implements TimetableGeneratorInputBoundary, Subscriber<Object>{

    private Session session;
    private final TimetableGeneratorOutputBoundary presenter;

    public TimetableGeneratorInteractor(TimetableGeneratorOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /** Helper Function: Converts ArrayList of CourseModels */
    public void generateTimetable(TimetableGeneratorRequestModel requestModel) {
        List<CalendarCourse> courses = createCustomCalenderCourse(requestModel.getCourses());
        List<Timetable> generatedTimetables = generateTimetable(courses);

        List<TimetableModel> generatedTimetableModels = new ArrayList<>();

        for (Timetable timetable : generatedTimetables){
            generatedTimetableModels.add(EntityConverter.generateTimetableResponse(timetable));
        }

        presenter.prepareSuccessView(new TimetableGeneratorResponseModel(generatedTimetableModels));
    }

    private List<Timetable> generateTimetable(List<CalendarCourse> courses){
        return generateTimetable(courses, List.of(new Timetable(new ArrayList<>(), session.getSessionType())));
    }

    /**
     * Returns an ArrayList of all possible timetables by checking every possible timetable course
     * for a given course against timetables in TimetableGeneratorRequestModel having an overlap with the class.
     * Only returns empty list of Timetables if there is only conflicts with given course and all given timeables.
     * @param course, TimetableGeneratorRequestModel
     * @return ArrayList<Timetable>
     */
    public List<Timetable> addTimetableCourse(CalendarCourse course, List<Timetable> timetables){
        ArrayList<Timetable> newTimetables = new ArrayList<>();
        for(Timetable timetable : timetables){
            TimetableCourseGenerator possibleTimes = new TimetableCourseGenerator(course);
            for(TimetableCourse possibleTime: possibleTimes.generateAllTimetableCourse()){
                if (!timetable.hasCourseOverlap(possibleTime)){
                    ArrayList<TimetableCourse> currentCourses = new ArrayList<>(timetable.getCourseList());
                    currentCourses.add(possibleTime);
                    newTimetables.add(new Timetable(currentCourses, timetable.getSessionType()));
                }
            }
        }
        return newTimetables;
    }
    /**
     * Recursively creates a list of timetables that has no conflict by "adding" each possible timetable Course
     * to each possible timetable and only keeping the timetables that have no conflict.
     * @param courses List of CalendarCourse
     * @return ArrayList<Timetable>
     */
    public List<Timetable> generateTimetable(List<CalendarCourse> courses, List<Timetable> timetables){
        if (courses.isEmpty())
            return timetables;
        else{
            return generateTimetable(courses, addTimetableCourse(courses.remove(0), timetables));
        }
    }

    private List<CalendarCourse> createCustomCalenderCourse(HashMap<String, List<String>> courses){
        List<CalendarCourse> result = new ArrayList<>();
        for (String courseCode : courses.keySet()){
            CalendarCourse calCourse = session.getCalendarCourse(courseCode);
            List<String> sectionCodes = courses.get(courseCode);

            CalendarCourse copyCalCourse = new CalendarCourse(calCourse.getTitle(),
                    new ArrayList<>(calCourse.getSections()), calCourse.getCourseSession(),
                    calCourse.getCourseCode(), calCourse.getBreadth());

            for (Section section : calCourse.getSections()){
                if (!sectionCodes.contains(section.getCode())){
                    copyCalCourse.removeSection(section);
                }
            }
            result.add(copyCalCourse);
        }
        return result;
    }

    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    @Override
    public void onNext(Object item) {
        if (item instanceof Session){
            this.session = (Session) item;
        }
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
}
