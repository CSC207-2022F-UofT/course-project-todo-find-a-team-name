package edit_timetable_use_case.application_business;

import edit_timetable_use_case.interface_adapters.EditTimetableView;

/**
 * The output boundary of the RemoveCourse use case.
 */
public interface RemoveCourseOutputBoundary {

    /**
     * @param responseModel The response model passed by the interactor.
     * @throws RemoveCourseFailedException when the course code does not match any of the Timetable's courses.
     */
    void prepareView(EditTimetableResponseModel responseModel) throws RemoveCourseFailedException;

    void setView(EditTimetableView view);
}
