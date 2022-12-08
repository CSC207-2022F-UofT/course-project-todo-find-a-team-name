package fileio_use_case.application_business.timetable_specific_classes;

import entities.*;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

public class SaveTimetableInteractor implements Flow.Subscriber<Object>{
    private final TimetableGateway timetableGateway;

    public SaveTimetableInteractor(TimetableGateway timetableGateway) {
        this.timetableGateway = timetableGateway;
    }
    /** Reads timetableRequestModel into a JSON file to be stored in
     * src/main/saved_timetables/.
     * @param timetableRequestModel - a request model for Timetable
     */
    public void timetableToFile(TimetableModel timetableRequestModel, String sessionType) throws InvalidSectionsException {
        Timetable aTimetable = timetableModelToTimetable(timetableRequestModel, sessionType);
        timetableGateway.timetableToFile(aTimetable);
    }
    /** Helper method for timetableToFile */
    private Timetable timetableModelToTimetable(TimetableModel model, String sessionType) throws InvalidSectionsException {
        ArrayList<TimetableCourse> allCourses = new ArrayList<>();
        for (CourseModel course : model.getCourses()) {
            allCourses.add(courseModelToTimetableCourse(course));
        }
        return new Timetable(allCourses, sessionType);
    }
    /** Helper method for helper method timetableModelTimetable */
    private TimetableCourse courseModelToTimetableCourse(CourseModel course) throws InvalidSectionsException {
        List<Section> allSections= new ArrayList<>();
        for (SectionModel section : course.getSections()) {
            List<Block> allBlocks= new ArrayList<>();
            for (BlockModel block : section.getBlocks()){
                allBlocks.add(new Block(String.valueOf(block.getDay()), String.valueOf((block.getStartTime())),
                        String.valueOf(block.getEndTime()), String.valueOf(block.getRoom())));
            }
            allSections.add(new Section(section.getCode(), section.getInstructor(), allBlocks));
        }
        return new TimetableCourse(course.getTitle(), allSections, course.getCourseSession(), course.getCourseCode(), course.getBreadth());
    }

    /**
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     * @param item the item
     */
    @Override
    public void onNext(Object item) {

    }

    /**
     * @param throwable the exception
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     *
     */
    @Override
    public void onComplete() {

    }
}
