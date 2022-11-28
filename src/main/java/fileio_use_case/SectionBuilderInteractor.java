package fileio_use_case;

import entities.Block;
import entities.Section;

import java.util.ArrayList;
import java.util.List;
/** Builds Section **/
public class SectionBuilderInteractor {

    private final String code;
    private final String instructorName;
    private final List<Block> blocks;

    public SectionBuilderInteractor(String code, String instructorName, ArrayList<Block> blocks) {
        this.code = code;
        this.instructorName = instructorName;
        this.blocks = blocks;
    }

    public Section newSection(){
        return new Section(this.code, this.instructorName, this.blocks);
    }
}
