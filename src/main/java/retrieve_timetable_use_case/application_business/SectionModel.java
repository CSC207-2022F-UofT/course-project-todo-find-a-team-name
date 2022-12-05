package retrieve_timetable_use_case.application_business;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;

/**
 * A data carrier class that doubles as a request and response model containing all information
 * that a Section would contain while protecting Controllers and Presenters from changes to
 * the original entity.
 * code is the section code: (e.g. "LEC0101").
 * instructor is a string containing the name of the section's instructors.
 * blocks is a List of BlockModels that contains the corresponding BlockModel of blocks in the section.
 */
public class SectionModel {

    private final String code;
    private final String instructor;
    private final List<BlockModel> blocks;

    public SectionModel(String code, String instructor, List<BlockModel> blocks){
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

    public List<BlockModel> getBlocks() {
        return blocks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SectionModel that = (SectionModel) o;
        return getCode().equals(that.getCode()) && getInstructor().equals(that.getInstructor()) &&
                new HashSet<>(getBlocks()).equals(new HashSet<>(that.getBlocks()));
    }

    @Override
    public int hashCode() {
        return Objects.hash(code, instructor) + blocks.size();
    }
}
