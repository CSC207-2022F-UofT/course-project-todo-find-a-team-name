package edit_timetable_use_case;

import entities.Session;
import entities.Timetable;
import retrieve_timetable_use_case.RetrieveTimetableController;
import retrieve_timetable_use_case.RetrieveTimetableInteractor;
import retrieve_timetable_use_case.TimetableResponseModel;
import screens.TimetableViewModel;

import java.util.ArrayList;

/** The interactor used to remove a course from a timetable.
 * Instance Attributes:
 * timetable - the timetable being edited by the interactor.
 * presenter - the presenter attached to the use case.
 */
public class RemoveCourseInteractor implements RemoveCourseInputBoundary {

    private Timetable timetable;
    private RemoveCourseOutputBoundary presenter;


    public RemoveCourseInteractor(Timetable timetable, RemoveCourseOutputBoundary presenter) {
        this.timetable = timetable;
        this.presenter = presenter;
    }

    /**
     * @param requestModel an EditTimetableRequestModel that stores the code of the
     *                     course to be removed.
     * @return returns a EditTimetableResponseModel contains a message and the success
     * of the action. See EditTimetableResponseModel for further details.
     * @throws RemoveCourseFailedException if the interactor was unable to remove the
     *                                     course from the timetable (likely because it couldn't find a course in
     *                                     timetable with the corresponding course code).
     */
    @Override
    public void remove(EditTimetableRequestModel requestModel)
            throws RemoveCourseFailedException {
        String courseCode = requestModel.getCourseCode();
        boolean success = false;
        if (timetable.existsByCode(courseCode)) {
            timetable.removeCourse(courseCode);
            success = true;
        }

        RetrieveTimetableInteractor
                RTInteractor = new RetrieveTimetableInteractor(timetable, new Session(""));

        TimetableResponseModel updatedTimetable = RTInteractor.retrieveTimetable();
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(courseCode, new ArrayList<>(),
                        success, updatedTimetable);
        presenter.prepareView(editTimetableResponseModel);
    }
}