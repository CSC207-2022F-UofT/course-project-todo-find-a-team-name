package retrieve_timetable_use_case;

import java.util.List;

public class SectionResponseModel {

    private String code;
    private String instructor;
    private List<BlockResponseModel> blocks;

    public SectionResponseModel(String code, String instructor, List<BlockResponseModel> blocks){
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

    public List<BlockResponseModel> getBlocks() {
        return blocks;
    }
}
