package edit_timetable_use_case.application_business;

import entities.Timetable;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

/** The interactor used to remove a course from a timetable.
 * Instance Attributes:
 * timetable - the timetable being edited by the interactor.
 * presenter - the presenter attached to the use case.
 */
public class RemoveCourseInteractor implements RemoveCourseInputBoundary {

    private Timetable timetable;
    private final RemoveCourseOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;


    public RemoveCourseInteractor(RemoveCourseOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * @param requestModel an EditTimetableRequestModel that stores the code of the
     *                     course to be removed.
     * @throws RemoveCourseFailedException if the interactor was unable to remove the
     *                                     course from the timetable (likely because it couldn't find a course in
     *                                     timetable with the corresponding course code).
     */
    @Override
    public void remove(EditTimetableRequestModel requestModel)
            throws RemoveCourseFailedException {
        String courseCode = requestModel.getCourseCode();
        if (timetable.existsByCode(courseCode)) {
            timetable.removeCourse(courseCode);
        }
        else {
            throw new RemoveCourseFailedException(courseCode);
        }
        retrieveInteractor.setTimetable(timetable);
        TimetableModel updatedTimetable = retrieveInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(courseCode, updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }

    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }

    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor){
    this.retrieveInteractor = retrieveInteractor;
    }
}