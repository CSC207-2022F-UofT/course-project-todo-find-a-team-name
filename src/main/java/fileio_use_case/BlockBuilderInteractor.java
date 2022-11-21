package fileio_use_case;

import entities.Block;

public class BlockBuilderInteractor {
    private final String day;
    private final String startTime;
    private final String endTime;
    private final String room;

    public BlockBuilderInteractor(String day, String startTime, String endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public Block newBlock(){
        return new Block(this.day, this.startTime, this.endTime, this.room);
    }
}
