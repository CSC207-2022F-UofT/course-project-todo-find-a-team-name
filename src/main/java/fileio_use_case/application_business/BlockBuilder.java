package fileio_use_case.application_business;

import entities.Block;
/** Builds Block class **/
public class BlockBuilder {
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String room;

    public BlockBuilder(String day, String startTime, String endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public Block newBlock(){
        return new Block(this.day, this.startTime, this.endTime, this.room);
    }
}
