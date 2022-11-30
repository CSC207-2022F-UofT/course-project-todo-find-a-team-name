package blacklist_whitelist_use_case;

import entities.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Blacklist, Whitelist use case Interactor.
 */
public class SectionFilterInteractor implements SectionFilterInputBoundary{
    final SectionFilterOutputBoundary presenter;
    private Session fallSession; // only for testing, delete when the team finishe the gateway.
    private Session winterSession;
    public SectionFilterInteractor(SectionFilterOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * filter() what filter does is it first creates a list of Calendar course entities with a list of courseCodes
     * provided in the requestModel, and then build a list of Constraints from the requestModel data, eventually
     * applying all the constraint to a list of CalendarCourse Domain to modify the sections of each course based on
     * the given constraints. And it make sure that if the original course has tutorial, lecture, practical sections,
     * the modified courses will have at least one of each section type as well.
     *
     * @param requestModel SectionFilterRequestModel that includes the courseCodes, and corresponding Constraints
     * @return SectionFilterResponseModel that provides an error message if the courseCode does not exists, or
     * if it is missing mandatory tutorial, lecture or practical section components.
     */
    @Override
    public void filter(SectionFilterRequestModel requestModel) {
        // delete when the gateway is done.
//        List<Block> blocks1 = new ArrayList<>();
//        blocks1.add(new Block("MO", "14:30", "15:00", "room3"));
//        blocks1.add(new Block("TU", "12:30", "14:00", "room1"));
//        blocks1.add(new Block("TH", "14:00", "15:30", "room2"));
//
//        List<Block> blocks2 = new ArrayList<>();
//        blocks2.add(new Block("MO", "14:30", "15:00", "room3"));
//        blocks2.add(new Block("FR", "20:00", "21:00", "room1"));
//
//        List<Block> blocks3 = new ArrayList<>();
//        blocks3.add(new Block("MO", "14:30", "15:30", "room3"));
//
//        List<Block> blocks4 = new ArrayList<>();
//        blocks4.add(new Block("MO", "14:30", "15:30", "room3"));
//        blocks4.add(new Block("TU", "12:30", "14:30", "room4"));
//        blocks4.add(new Block("TH", "14:00", "15:30", "room5"));
//
//        List<Block> blocks5 = new ArrayList<>();
//        blocks5.add(new Block("MO", "14:30", "15:30", "room3"));
//        blocks5.add(new Block("WE", "16:30", "18:00", "room3"));
//
//        List<Block> blocks6 = new ArrayList<>();
//        blocks6.add(new Block("FR", "11:30", "12:30", "room3"));
//        blocks6.add(new Block("FR", "11:30", "12:30", "room4"));
//
//        Section section1 = new Section("LEC-0101", "inst1", blocks1);
//        Section section2 = new Section("TUT-0401", "inst2", blocks2);
//        Section section3 = new Section("PRA-0301", "inst3", blocks3);
//        Section section4 = new Section("LEC-0201", "inst4", blocks4);
//        Section section5 = new Section("TUT-0402", "inst5", blocks5);
//        Section section6 = new Section("LEC-0509", "inst6", blocks6);
//
//        List<Section> sections1 = new ArrayList<>();
//        sections1.add(section1);
//        sections1.add(section2);
//
//        List<Section> sections2 = new ArrayList<>();
//        sections2.add(section1);
//        sections2.add(section2);
//        sections2.add(section3);
//        sections2.add(section4);
//        sections2.add(section5);
//        sections2.add(section6);
//
//
//        List<Section> sections3 = new ArrayList<>();
//        sections3.add(section1);
//        sections3.add(section2);
//        sections3.add(section3);
//        sections3.add(section4);
//        sections3.add(section5);
//        sections3.add(section6);
//
//        List<Section> sections4 = new ArrayList<>();
//        sections4.add(section1);
//        sections4.add(section2);
//        sections4.add(section3);
//        sections4.add(section4);
//        sections4.add(section5);
//        sections4.add(section6);
//
//        List<Section> sections5 = new ArrayList<>();
//        sections5.add(section1);
//        sections5.add(section4);
//
//        session = new Session(requestModel.sessionType());
//        session.addCourse(new CalendarCourse("CSC207", sections1, "S", "CSC207H1", "5"));
//        session.addCourse(new CalendarCourse("CSC258", sections2, "S", "CSC258H1", "5"));
//        session.addCourse(new CalendarCourse("MAT235", sections3, "S", "MAT235H1", "5"));
//        session.addCourse(new CalendarCourse("CSC236", sections4, "S", "CSC236H1", "5"));
//        session.addCourse(new CalendarCourse("STA247", sections5, "S", "STA247H1", "5"));
        // delete

        ArrayList<String> courseCodes = (ArrayList<String>) this.formatInputString(requestModel.getCourseCodes());
        ArrayList<CalendarCourse> calendarCourses = new ArrayList<CalendarCourse>();

        if (requestModel.getCourseCodes().isEmpty()){
            presenter.prepareFailView("No course code has been entered.");
            return;
        }
        if (formatTime(requestModel.getStartTime()) > formatTime(requestModel.getEndTime())){
            presenter.prepareFailView("StartTime Must be BEFORE EndTime");
            return;
        }
        if (requestModel.getSessionType().equals("F")){
            for (String code: courseCodes) {
                if (fallSession.checkCourseCode(code)){
                    calendarCourses.add(fallSession.getCalendarCourse(code));
                } else {
                    presenter.prepareFailView("Course Code Input: "+code + " does not exist!");
                    return;
                }
            }
        } else{
            for (String code: courseCodes) {
                if (winterSession.checkCourseCode(code)){
                    calendarCourses.add(winterSession.getCalendarCourse(code));
                } else {
                    presenter.prepareFailView("Course Code Input: "+code + " does not exist!");
                    return;
                }
            }
        }

        ArrayList<Constraint> constraints = (ArrayList<Constraint>) this.buildConstraints(requestModel);
        for (CalendarCourse course: calendarCourses) {
            for (Constraint constraint: constraints) {
                if (! constraint.filter(course)){
                    presenter.prepareFailView("Filtering Condition Failed: " + course.getCourseCode()
                            + " is missing mandatory tutorial, lecture, or practical after filtering.");
                    return;
                }
            }
        }
        HashMap<String, List<String>> courseSectionsData = new HashMap<>();
        for (CalendarCourse course: calendarCourses) {
            courseSectionsData.put(course.getCourseCode(), course.getSectionCodes());
        }
        SectionFilterResponseModel responseModel = new SectionFilterResponseModel(courseSectionsData, requestModel.getSessionType());

        presenter.prepareSuccessView(responseModel);


//  SectionFilterResponseModel sectionFilterResponseModel = new SectionFilterResponseModel();
    }

    /**
     * A helper method that creates a List of Constraints from the given requestModel that could be
     * applied to the course Entities to filter.
     * @param requestModel the SectionFilterRequestModel
     * @return a list of Constraint that
     */
    private List<Constraint> buildConstraints(SectionFilterRequestModel requestModel){
        ArrayList<Constraint> constraints = new ArrayList<>();
        if (! (requestModel.getIsInstructorBlackList().equals("/"))) {
            InstructorConstraint instructorConstraint = new InstructorConstraint(
                    formatInputString(requestModel.getInstructorConstraints()),
                    formatIsBlackList(requestModel.getIsInstructorBlackList()));
            constraints.add(instructorConstraint);
        }
        if (! (requestModel.getIsRoomBlackList().equals("/"))) {
            RoomConstraint roomConstraint = new RoomConstraint(
                    formatInputString(requestModel.getRoomConstraints()),
                    formatIsBlackList(requestModel.getIsRoomBlackList()));
            constraints.add(roomConstraint);
        }
        if (! (requestModel.getIsDayBlackList().equals("/"))) {
            WeekdayConstraint weekdayConstraint = new WeekdayConstraint(
                    requestModel.getDayConstraints(),
                    formatIsBlackList(requestModel.getIsDayBlackList()));
            constraints.add(weekdayConstraint);
        }
        if (! (requestModel.getIsTimeBlackList().equals("/"))) {
            TimeIntervalConstraint timeIntervalConstraint = new TimeIntervalConstraint(
                    formatTime(requestModel.getStartTime()),
                    formatTime(requestModel.getEndTime()),
                    formatIsBlackList(requestModel.getIsTimeBlackList()));
            constraints.add(timeIntervalConstraint);
        }
        return constraints;
    }


    /**
     * A helper method that format the string input from the request model to List data type suitable for creating the
     * Constraints Entities and check for Input Validity.
     * @param  string string of constraints seperated by comma.
     * @return a List of String removing empty string and unnecessary spaces.
     */
    private List<String> formatInputString(String string){
        String[] stringArray = string.split(",");
        ArrayList<String> stringArrayList = new ArrayList<>();
        for (String s: stringArray) {
            if (!s.isBlank()) {
                stringArrayList.add(s.trim());
            }
        }
        return stringArrayList;
    }

    /**
     * A helper method that format the string input from the request model to boolean data type suitable for creating the
     * Constraints Entities
     * @param  isBlackList string isBlackList from the SectionFilterRequestModel
     * @return a boolean indicating whether the constraint is a blackList or whiteList Constraint.
     */
    private boolean formatIsBlackList(String isBlackList){
        return "BLACKLIST".equals(isBlackList);

    }

    public void setFallSession(Session session) {
        this.fallSession = session;
    }

    public void setWinterSession(Session session) {
        this.winterSession = session;
    }

    /**
     * A helper method that format the string time input from the request model to double type to
     * create the TimeInterval Constraints entities.
     * @param time a string representation of the time
     * @return a double representation of time.
     */
    private double formatTime(String time){
        String[] time_to_format = time.split(":");
        double hour = Integer.parseInt(time_to_format[0]);
        double min = Integer.parseInt(time_to_format[1]);
        return hour + min / 60;
    }

}


