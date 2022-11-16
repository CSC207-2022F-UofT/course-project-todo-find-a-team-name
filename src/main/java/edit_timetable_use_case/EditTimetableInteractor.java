package edit_timetable_use_case;

/** The interactor used to remove, add or edit a course in a timetable.
 * Instance Attributes:
 * timetable - the timetable being edited by the interactor.
 * session - the currently loaded session that determines which courses are available.
 * presenter - the presenter used by the user.
 */
public class EditTimetableInteractor implements EditTimetableInputBoundary {

    private Timetable timetable;
    private Session session;
    private final EditTimetableOutputBoundary presenter;


    public EditTimetableInteractor(Timetable timetable, Session session, EditTimetableOutputBoundary presenter){
        this.timetable = timetable;
        this.session = session;
        this.presenter = presenter;
    }

    /**
     * @param requestModel an EditTimetableRequestModel that stores the code of the
     *                     course to be removed.
     * @return returns a EditTimetableResponseModel contains a message and the success
     * of the action.
     * @throws RemoveCourseFailedException if the interactor was unable to remove the
     * course from the timetable (likely because it couldn't find a course in timetable
     * with the corresponding course code).
     */
    @Override
    public EditTimetableResponseModel remove(EditTimetableRequestModel requestModel)
            throws RemoveCourseFailedException {
        String courseCode = requestModel.getCourseCode();
        if (timetable.existsByCode(courseCode)){
            timetable.removeCourse(courseCode);
        }
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(courseCode,
                        timetable.existsByCode(courseCode));
            return presenter.prepareRemoveView(editTimetableResponseModel);
        }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public EditTimetableResponseModel add(EditTimetableRequestModel requestModel) {
        return null;
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public EditTimetableResponseModel edit(EditTimetableRequestModel requestModel) {
        return null;
    }
}
