package retrieve_timetable_use_case.application_business;

import retrieve_timetable_use_case.interface_adapters.TimetableModelConverter;

/**
 * The presenter used in the RetrieveTimetable use case.
 * view is the view that is updated when its methods are called.
 */
public class RetrieveTimetablePresenter implements RetrieveTimetableOutputBoundary{

    private RetrieveTimetableView view;

    public RetrieveTimetablePresenter(){}

    public void setView(RetrieveTimetableView view){
        this.view = view;
    }

    /**
     * @param sessionModel A session model containing the current session.
     *                     Updates view with a SessionViewModel equivalent to sessionModel.
     */
    @Override
    public void updateSession(SessionModel sessionModel) {
        view.updateSession(TimetableModelConverter.sessionToView(sessionModel));
    }
}
