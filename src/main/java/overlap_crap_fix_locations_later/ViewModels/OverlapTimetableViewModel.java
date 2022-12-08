package overlap_crap_fix_locations_later.ViewModels;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * A ViewModel representing a Timetable for the OverlapInputDialog.
 * Note that the data is exactly identical to Timetable Entity of course, this just models it.
 */
public class OverlapTimetableViewModel {
    private final List<OverlapTimetableCourseViewModel> courses;

    public OverlapTimetableViewModel(List<OverlapTimetableCourseViewModel> courses) {
        this.courses = courses;
    }

    public List<OverlapTimetableCourseViewModel> getCourses() {
        return courses;
    }


    /**
     * Two of these viewModels are equal according to data class equality.
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverlapTimetableViewModel that = (OverlapTimetableViewModel) o;
        return getCourses().equals(that.getCourses());
    }

}

/**
 * A helper ViewModel representing a Course for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 * Can be quickly initialized from a CourseModel
 */
class OverlapTimetableCourseViewModel {
    private final String title;
    private final List<OverlapTimetableSectionViewModel> sections;
    private final String courseSession;
    private final String courseCode;
    private final String breadth;

    public OverlapTimetableCourseViewModel(String title, List<OverlapTimetableSectionViewModel> sections, String courseSession,
                                           String courseCode, String breadth) {
        this.title = title;
        this.sections = sections;
        this.courseSession = courseSession;
        this.courseCode = courseCode;
        this.breadth = breadth;
    }

    public String getTitle() {
        return title;
    }

    public List<OverlapTimetableSectionViewModel> getSections() {
        return new ArrayList<>(this.sections);
    }

    public String getCourseSession() {
        return courseSession;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getBreadth() {
        return this.breadth;
    }

    /**
     * Equality for these viewModels <==> data class equality
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverlapTimetableCourseViewModel that = (OverlapTimetableCourseViewModel) o;
        return getTitle().equals(that.getTitle()) &&
                new HashSet<>(getSections()).equals(new HashSet<>(that.getSections()))
                && getCourseSession().equals(that.getCourseSession())
                && getCourseCode().equals(that.getCourseCode())
                && getBreadth().equals(that.getBreadth());
    }
}


/**
 * A helper ViewModel representing a Section for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 * Can be quickly initialized from a SectionModel.
 */
class OverlapTimetableSectionViewModel {

    private final String code;
    private final String instructor;
    private final List<OverlapTimetableBlockViewModel> blocks;

    public OverlapTimetableSectionViewModel(String code, String instructor, List<OverlapTimetableBlockViewModel> blocks) {
        this.code = code;
        this.instructor = instructor;
        this.blocks = blocks;
    }

    public String getCode() {
        return code;
    }

    public String getInstructor() {
        return instructor;
    }

    public List<OverlapTimetableBlockViewModel> getBlocks() {
        return blocks;
    }

    /**
     * Equality between two of these ViewModels is defined as being of the same type, with identical fields.
     * So, data class equality.
     **/
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverlapTimetableSectionViewModel that = (OverlapTimetableSectionViewModel) o;
        return getCode().equals(that.getCode()) && getInstructor().equals(that.getInstructor()) &&
                new HashSet<>(getBlocks()).equals(new HashSet<>(that.getBlocks()));
    }
}

/**
 * A helper ViewModel representing a Block for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 */
class OverlapTimetableBlockViewModel {
    private final int day;
    private final double startTime;
    private final double endTime;
    private final String room;

    public OverlapTimetableBlockViewModel(int day, double startTime, double endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public int getDay() {
        return day;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return room;
    }

    /**
     * An equals() method used for comparison. Essentially just compares to see if the object is of the correct
     * type and has identical fields.
     *
     * @param o - the other object to compare this one with.
     * @return - well, equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverlapTimetableBlockViewModel that = (OverlapTimetableBlockViewModel) o;
        return getDay() == that.getDay() && Double.compare(that.getStartTime(), getStartTime()) == 0
                && Double.compare(that.getEndTime(), getEndTime()) == 0 && getRoom().equals(that.getRoom());
    }

}


