package overlap_crap_fix_locations_later.interface_adapters;

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


