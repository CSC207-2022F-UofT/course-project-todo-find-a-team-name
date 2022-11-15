package edit_timetable_use_case;

public class EditTimetableInteractor implements EditTimetableInputBoundary {

    private Timetable timetable;
    private Session session;
    private final EditTimetablePresenter presenter;


    public EditTimetableInteractor(Timetable timetable, Session session, EditTimetablePresenter presenter){
        this.timetable = timetable;
        this.session = session;
        this.presenter = presenter;
    }

    /**
     * @param requestModel
     * @return
     */
    @Override
    public EditTimetableResponseModel remove(EditTimetableRequestModel requestModel) {
        if (timetable.existsByCode(requestModel.getCourseCode())){
            timetable.removeCourse(requestModel.getCourseCode());
        }
        EditTimetableResponseModel editTimetableResponseModel =
                new EditTimetableResponseModel(requestModel.getCourseCode(),
                        timetable.existsByCode(requestModel.getCourseCode()));
            return presenter.prepareView(editTimetableResponseModel);
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
