package entities;

import java.util.Objects;

public class Block {
//  a block has 4 elements, day as an integer (0,1,2,3,4), start and end time as doubles, room as a string
    private final int day;
    private final double startTime;
    private final double endTime;
    private final String room;

    public Block(String day, String startTime, String endTime, String room) {
        switch (day) {
            case "MO":
            case "0":
                this.day = 0;
                break;
            case "TU":
            case "1":
                this.day = 1;
                break;
            case "WE":
            case "2":
                this.day = 2;
                break;
            case "TH":
            case "3":
                this.day = 3;
                break;
            default:
                this.day = 4;
                break;
        }


        String[] start = startTime.split(":");
        double startHour = Integer.parseInt(start[0]);
        double startMin = Integer.parseInt(start[1]);
        this.startTime = startHour + startMin / 60;

        String[] end = endTime.split(":");
        double endHour = Integer.parseInt(end[0]);
        double endMin = Integer.parseInt(end[1]);
        this.endTime = endHour + endMin / 60;

        this.room = room;
    }

    public int getDay() { return day; }

    public double getStartTime() { return startTime; }

    public double getEndTime() { return endTime; }

    public String getRoom() { return room; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Block)) {
            return false;
        }

        return day == ((Block) obj).day && startTime == ((Block) obj).startTime
                && endTime == ((Block) obj).endTime && room.equals(((Block) obj).room);
    }

    /**
     * Returns a hash code value for this block.
     * If two objects are equal based on equals method, hashCode also returns same integers.
     *
     * @return a hash code value for this block.
     */
    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime, room);
    }
}