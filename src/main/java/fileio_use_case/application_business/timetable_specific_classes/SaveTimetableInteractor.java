package fileio_use_case.application_business.timetable_specific_classes;

import entities.*;
import fileio_use_case.frameworks_and_drivers.TimetableGateway;
import java.util.concurrent.Flow;

public class SaveTimetableInteractor implements Flow.Subscriber<Object>{
    private final TimetableGateway timetableGateway;
    private Timetable savedTimetable;

    public SaveTimetableInteractor(TimetableGateway timetableGateway) {
        this.timetableGateway = timetableGateway;
    }

    /** Method where classes this interactor is subscribed to will have their entity passed to
     * to convert a timetable to file.
     *
     */
    public void updatingTimetableToFile() {
        timetableToFile(savedTimetable);
    }

    /** Reads Timetable into a JSON file to be stored in
     * src/main/saved_timetables/.
     * @param timetableToSave - Timetable entity that user wants to save
     */
    private void timetableToFile(Timetable timetableToSave) {
        timetableGateway.timetableToFile(timetableToSave);
    }

    /**
     * @param subscription a new subscription
     */
    @Override
    public void onSubscribe(Flow.Subscription subscription) {

    }

    /**
     * Sends desired timetable to save to be converted into JSON file and saved in /src/main/saved_timetables
     * @param timetable the timetable user wants to save (Should be a Timetable entity)
     */
    @Override
    public void onNext(Object timetable) {
        if (timetable instanceof Timetable)
            savedTimetable = (Timetable) timetable;
    }

    /**
     * Can't be removed. Part of Java Flow
     * @param throwable the exception
     */
    @Override
    public void onError(Throwable throwable) {

    }

    /**
     * Can't be removed. Part of Java flow
     */
    @Override
    public void onComplete() {

    }
}
