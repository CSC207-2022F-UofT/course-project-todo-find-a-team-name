package edit_timetable_use_case;

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
    public EditTimetableResponseModel remove(EditTimetableRequestModel requestModel)
            throws RemoveCourseFailedException {
        String courseCode = requestModel.getCourseCode();
        if (timetable.existsByCode(courseCode)) {
            timetable.removeCourse(courseCode);
        }
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(courseCode,
                        timetable.existsByCode(courseCode));
        return presenter.prepareView(editTimetableResponseModel);
    }
}