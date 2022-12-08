package overlap_crap_fix_locations_later.ViewModels;

import java.util.List;

/**
 * A helper ViewModel representing a Section for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 * Can be quickly initialized from a SectionModel.
 */
public class OverlapTimetableSectionViewModel {

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
        if (o instanceof OverlapTimetableSectionViewModel) {
            OverlapTimetableSectionViewModel other = (OverlapTimetableSectionViewModel) o;
            return this.code.equals(other.code) && this.instructor.equals(other.instructor) &&
                    this.blocks.equals(other.blocks);
        } else {
            return false;
        }
    }
}
