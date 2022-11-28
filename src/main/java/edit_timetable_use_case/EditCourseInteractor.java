package edit_timetable_use_case;

import entities.*;
import retrieve_timetable_use_case.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.TimetableModel;

import java.util.ArrayList;

public class EditCourseInteractor implements EditCourseInputBoundary {

    private Timetable timetable;
    private Session session;
    private final AddCourseInteractor addInteractor;
    private final RemoveCourseInteractor removeInteractor;
    private final EditCourseOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;

    public EditCourseInteractor(EditCoursePresenter presenter, AddCourseInteractor addInteractor,
                                RemoveCourseInteractor removeInteractor){
        this.presenter = presenter;
        this.addInteractor = addInteractor;
        this.removeInteractor = removeInteractor;
    }

    @Override
    public void edit(EditTimetableRequestModel request) throws InvalidSectionsException, NotInTimetableException {
        EditTimetableRequestModel removeRequest = new EditTimetableRequestModel(request.getCourseCode(),
                new ArrayList<>());
        removeInteractor.setTimetable(timetable);
        try{
            removeInteractor.remove(removeRequest);
        }
        catch (RemoveCourseFailedException e){
            throw new NotInTimetableException(request.getCourseCode());
        }
        addInteractor.setSession(session);
        addInteractor.setTimetable(timetable);
        EditTimetableRequestModel addRequest = new EditTimetableRequestModel(request.getCourseCode(), request.getSectionCodes());
        addInteractor.add(addRequest);

        retrieveInteractor.setTimetable(timetable);
        retrieveInteractor.setSession(session);
        TimetableModel updatedTimetable = retrieveInteractor.retrieveTimetable();

        EditTimetableResponseModel response = new EditTimetableResponseModel(request.getCourseCode(),
                request.getSectionCodes(), updatedTimetable);
        presenter.prepareView(response);
    }

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void setTimetable(Timetable timetable) {
        this.timetable = timetable;
    }
}