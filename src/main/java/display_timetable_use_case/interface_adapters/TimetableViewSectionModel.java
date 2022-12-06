package display_timetable_use_case.interface_adapters;

import java.util.List;

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
}
