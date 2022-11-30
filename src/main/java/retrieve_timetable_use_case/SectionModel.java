package retrieve_timetable_use_case;

import java.util.List;

public class SectionModel {

    private String code;
    private String instructor;
    private List<BlockModel> blocks;

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
}
