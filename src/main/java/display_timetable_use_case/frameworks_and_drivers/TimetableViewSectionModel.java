package display_timetable_use_case.frameworks_and_drivers;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * Class representing all information needed for the section in displaying the timetable
 * Instance Attributes:
 *      - code: code of this section
 *      - sectionModels: models representing block contained in this section, each storing all information needed for
 *      block to display the timetable
 */
public class TimetableViewSectionModel {
    private final String code;
    private final List<TimetableViewBlockModel> blockModels;

    /**
     * Constructs TimetableViewSectionModel with the section code and block models
     *
     * @param code course code of this course
     * @param blockModels models representing blocks contained in this section
     */
    public TimetableViewSectionModel(String code, List<TimetableViewBlockModel> blockModels) {
        this.code = code;
        this.blockModels = blockModels;
    }

    /**
     * Returns code of this section
     *
     * @return section code
     */
    public String getCode() {
        return code;
    }

    /**
     * Returns models representing block contained in this section, each storing all information for
     * block needed to display the timetable
     *
     * @return models representing block contained in this course
     */
    public List<TimetableViewBlockModel> getBlockModels() {
        return blockModels;
    }

    /**
     * Return whether this object is equal to obj
     * @param obj object compared
     * @return whether this object is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimetableViewSectionModel)){
            return false;
        }

        TimetableViewSectionModel other = (TimetableViewSectionModel) obj;

        return code.equals(other.code) && (new HashSet<>(blockModels)).equals(new HashSet<>(other.blockModels));
    }

    /**
     * Returns the string representation of this object
     * @return the string representation of this object
     */
    @Override
    public String toString() {
        return "TimetableViewSectionModel{" +
                "code='" + code + '\'' +
                ", blockModels=" + blockModels +
                '}';
    }

    /**
     * Returns a hash code value for this object.
     * If two objects are equal based on equals method, hashCode also returns same integers.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(code, new HashSet<>(blockModels));
    }
}
