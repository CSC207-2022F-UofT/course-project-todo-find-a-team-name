package timetable_generator_use_case.interface_adapters;

import entities.InvalidSectionsException;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.application_business.session_specific_classes.SessionGatewayInteractor;
import fileio_use_case.frameworks_and_drivers.SessionGateway;
import org.json.simple.parser.ParseException;
import retrieve_timetable_use_case.CourseModel;
import retrieve_timetable_use_case.SessionModel;
import timetable_generator_use_case.application_business.TimetableGeneratorInputBoundary;
import timetable_generator_use_case.application_business.TimetableGeneratorRequestModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * TimetableGeneratorController sends information to the TimetableGeneratorInteractor in the form of a
 * TimetableGeneratorRequestModel.
 */

public class TimetableGeneratorController {
    private final HashMap<String, List<String>> constraints;
    private final String sessionType;
    private final FileImportRequestModel sessionGatewayFilePath;
    private final TimetableGeneratorInputBoundary interactor;

    public TimetableGeneratorController(HashMap<String, List<String>> constraints, String sessionType,
                                        FileImportRequestModel sessionGatewayFilePath, TimetableGeneratorInputBoundary
                                                interactor) {
        this.constraints = constraints;
        this.sessionType = sessionType;
        this.sessionGatewayFilePath = sessionGatewayFilePath;
        this.interactor = interactor;
    }
    /**
     * Creates: CourseModel based on constraints, creates TimetableGeneratorRequestModel,
     * Initializes TimetableGeneratorInteractor
     */
    void create() throws IOException, ParseException, InvalidSectionsException, java.text.ParseException {
        ArrayList<CourseModel> lst = new ArrayList<>();
        // Create CourseModel
        SessionGateway temp = new SessionGateway();
        SessionModel sessionModel = new SessionGatewayInteractor(temp).readFromFile(this.sessionGatewayFilePath,
                this.sessionType);
        for (String courseCode : this.constraints.keySet()) {
            CourseModel existingInfo = sessionModel.getCourses().get(courseCode);
            CourseModel modifiedCourse = new CourseModel(existingInfo.getTitle(),
                    existingInfo.getSections(), existingInfo.getCourseSession(), existingInfo.getCourseCode(),
                    existingInfo.getBreadth());
            lst.add(modifiedCourse);
        }
        // Creates TimetableGeneratorRequestModel
        TimetableGeneratorRequestModel requestModel = new TimetableGeneratorRequestModel(lst);
        // TimetableGeneratorInteractor takes in requestModel and sessionType
        this.interactor.generateTimetable(requestModel, sessionType);
    }

}