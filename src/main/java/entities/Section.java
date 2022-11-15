package entities;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Section {
    private final String code;
    private final String instructorName;
    private final List<Block> blocks;


    // Rep invariant:
    //      blocks is sorted based on start time of each block

    private static class BlockComparator implements Comparator<Block> {

        /**
         * Returns a negative integer, zero, or positive integer
         * as the b1 is less than, equal to, or greater than b2
         *
         * @param b1 the first object to be compared.
         * @param b2 the second object to be compared.
         * @return a negative integer, zero, or positive integer
         *         as the b1 is less than, equal to, or greater than b2
         */
        @Override
        public int compare(Block b1, Block b2) {
            return Double.compare(b1.getDay() * 24 + b1.getStartTime(), b2.getDay() * 24 - b2.getStartTime());
        }
    }

    /**
     * Creates Section entity with given blocks, section code, and
     * instructor name.
     *
     * @param code section code (e.g. LEC0101)
     * @param instructorName instructor name
     * @param blocks List of Block entities
     */
    public Section(String code, String instructorName, List<Block> blocks){
        blocks.sort(new BlockComparator());
        this.code = code;
        this.instructorName = instructorName;
        this.blocks = blocks;
    }

    /**
     * Returns whether time conflict exists between
     * this section and the given section called other
     *
     * @param other another section entity
     * @return whether there is time conflict between this and other
     */
    public boolean isConflicted(Section other){
        List<Block> merged = mergeBlocks(blocks, other.blocks);
        for (int i = 0; i < merged.size() - 1; i++){
            if (merged.get(i).getDay() == merged.get(i + 1).getDay() &&
                    merged.get(i).getEndTime() > merged.get(i + 1).getStartTime())
                return false;
        }
        return true;

    }

    /**
     * Returns sorted List containing elements of blocks1 and blocks2
     *
     * @param blocks1 sorted list of Block entities
     * @param blocks2 sorted list of Block entities
     * @return sorted List containing elements of blocks1 and blocks2
     */
    private static List<Block> mergeBlocks(List<Block> blocks1, List<Block> blocks2) {
        int i = 0, j = 0;
        Comparator<Block> comparator = new BlockComparator();
        List<Block> merged = new ArrayList<>();
        while (i < blocks1.size() && j < blocks2.size()){
            if (comparator.compare(blocks1.get(i), blocks2.get(j)) < 0){
                merged.add(blocks1.get(i));
                i++;
            }
            else {
                merged.add(blocks2.get(j));
                j++;
            }
        }

        if (i == blocks1.size()){
            merged.addAll(blocks2.subList(j, blocks2.size() - 1));
        }
        else {
            merged.addAll(blocks1.subList(i, blocks1.size() - 1));
        }
        return merged;
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
}




