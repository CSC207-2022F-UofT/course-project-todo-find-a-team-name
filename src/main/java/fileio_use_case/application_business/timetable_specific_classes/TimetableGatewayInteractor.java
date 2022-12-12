package fileio_use_case.application_business.timetable_specific_classes;

import entities.*;
import fileio_use_case.application_business.FileImportRequestModel;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.Flow;

/** Interactor for TimetableGateway*/
public class TimetableGatewayInteractor implements TimetableFileImportInputBoundary, Flow.Publisher<Object>{
    final ArrayList<Flow.Subscriber<Object>> receivers;
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
     * given a session type, return a TimetableModel with specified course type from the JSON file
     * (Timetable or Calendar)
     *
     * @param jsonData FileImportRequestModel, Course Type
     */
    public void readFromFile(FileImportRequestModel jsonData, String courseType) throws IOException, ParseException, InvalidSectionsException {
        String filePath = jsonData.getFilePath();
        Timetable aTimetable = this.timetableGateway.readFromFile(filePath, courseType);
        for (Flow.Subscriber<Object> subscriber : receivers){
            subscriber.onNext(aTimetable);
        }
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
