package entities;

import java.util.HashSet;
import java.util.List;

/**
 * An entity representing a Section (e.g. LEC-0101, TUT-0201, PRA-0101) of a course.
 * Instance Attributes:
 *      - code: section code (e.g. "LEC-0101")
 *      - instructorName: name of the instructor for this section
 *      - blocks: list of blocks of this section
 */
public class Section {
    private final String code;
    private final String instructorName;
    private final List<Block> blocks;

    /**
     * Creates Section entity with given blocks, section code, and
     * instructor name.
     *
     * @param code section code (e.g. LEC0101)
     * @param instructorName instructor name
     * @param blocks List of Block entities
     */
    public Section(String code, String instructorName, List<Block> blocks){
        this.code = code;
        this.instructorName = instructorName;
        this.blocks = blocks;
    }

    /**
     * Returns whether time conflict exists between
     * this section and the given section called other,
     * assuming that both section exist in same session
     *
     * @param other another section entity
     * @return whether there is time conflict between this and other
     */
    public boolean isConflicted(Section other){
        for (Block block1 : this.getBlocks()){
            for (Block block2 : other.getBlocks()){
                if (block1.getDay() == block2.getDay() && block1.getStartTime() < block2.getEndTime()
                    && block1.getEndTime() > block2.getStartTime()) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * returns section code of this entity
     *
     * @return section code of this entity
     */
    public String getCode() {
        return code;
    }

    /**
     * returns instructor name of this section
     *
     * @return instructor name of this section
     */
    public String getInstructorName() {
        return instructorName;
    }

    /**
     * returns List of Block contained in this section
     *
     * @return List of Block contained in this section
     */
    public List<Block> getBlocks() {
        return blocks;
    }

    /**
     * returns String representation of this section with the format:
     *          "Section {code: [code], instructor name: [instructorName], # of blocks: [blocks.size()]}"
     *
     * @return  String representation of this section
     */
    @Override
    public String toString() {
        return "Section {" + "code: " + code + ", instructor name: " + instructorName +
                ", # of blocks: " + blocks.size() + "}";
    }

    /**
     * returns whether this section is equal to obj, based on the value of
     * instance attributes
     *
     * @param obj object compared with this section
     * @return whether this section is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Section)){
            return false;
        }

        Section other = (Section) obj;

        return code.equals(other.code) && instructorName.equals(other.instructorName)
                && new HashSet<>(blocks).equals(new HashSet<>(other.blocks));
    }

    /**
     * Returns whether this section is lecture
     * (i.e. whether section code starts with "LEC")
     * @return whether this section is lecture
     */
    public boolean isLecture(){
        return code.startsWith("LEC");
    }

    /**
     * Returns whether this section is tutorial
     * (i.e. whether section code starts with "TUT")
     * @return whether this section is tutorial
     */
    public boolean isTutorial(){
        return code.startsWith("TUT");
    }

    /**
     * Returns whether this section is practical
     * (i.e. whether section code starts with "PRA")
     * @return whether this section is practical
     */
    public boolean isPractical(){
        return code.startsWith("PRA");
    }
}




