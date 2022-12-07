package fileio_use_case.application_business.timetable_specific_classes;

import entities.*;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Flow;

/** Interactor for TimetableGateway*/
public class TimetableGatewayInteractor implements TimetableFileImportInputBoundary, Flow.Publisher<Object>{
    ArrayList<Flow.Subscriber<Object>> receivers;
    private final TimetableGateway timetableGateway;
    /**
     * Constructor
     * @param timetableGateway - Pass in Timetable Gateway
     */
    public TimetableGatewayInteractor(TimetableGateway timetableGateway) {
        this.timetableGateway = timetableGateway;
        this.receivers = new ArrayList<>();
    }
    /**
     * Given FileImportRequestModel, which holds a string of the JSON file path and
     * given a session type, return a TimetableModel with specified session type
     * from the JSON file.
     * @param jsonData FileImportRequestModel, Session Type
     * @return TimetableModel
     */
    public TimetableModel readFromFile(FileImportRequestModel jsonData, String sessionType) throws IOException, ParseException, java.text.ParseException, InvalidSectionsException {
        String filePath = jsonData.getFilePath();
        Timetable aTimetable = this.timetableGateway.readFromFile(filePath, sessionType);
        for (Flow.Subscriber<Object> subscriber : receivers){
            subscriber.onNext(aTimetable);
        }
        return timetableToTimetableModel(aTimetable);
    }
    /** Helper method for readFromFile */
    private TimetableModel timetableToTimetableModel(Timetable aTimetable) {
        List<CourseModel> allCourses = new ArrayList<>();
        for (TimetableCourse course : aTimetable.getCourseList()) {
            allCourses.add(timetableCourseToCourseModel(course));
        }
        return new TimetableModel(allCourses);
    }
    /** Helper method for timetableToTimetableModel */
    private CourseModel timetableCourseToCourseModel(TimetableCourse course) {
        List<SectionModel> allSections = new ArrayList<>();
        for (Section section : course.getSections()) {
            List<BlockModel> allBlocks = new ArrayList<>();
            for (Block block : section.getBlocks()){
                allBlocks.add(new BlockModel(block.getDay(), block.getStartTime(),
                        block.getEndTime(), block.getRoom()));
            }
            allSections.add(new SectionModel(section.getCode(), section.getInstructorName(), allBlocks));
        }
        return new CourseModel(course.getTitle(),
                allSections, course.getCourseSession(), course.getCourseCode(),
                course.getBreadth());
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
     * Add subscribers/observers to this class
     * @param subscriber - a subscriber
     * */
    @Override
    public void subscribe(Flow.Subscriber<? super Object> subscriber) {
        this.receivers.add(subscriber);
    }

}
