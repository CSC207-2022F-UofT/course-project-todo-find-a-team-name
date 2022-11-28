package edit_timetable_use_case;

import screens.EditTimetableView;

public interface EditCourseOutputBoundary {

    /**
     * @param responseModel a response model with the codes of the courses and sections that the user tried to add.
     *                      Updates the view with an appropriate success (or failure) message.
     */
    void prepareView(EditTimetableResponseModel responseModel);

    /**
     * @param view the presenter's new view.
     *             Changes the view modified by prepareView.
     */
    void setView(EditTimetableView view);
}