package retrieve_timetable_use_case.application_business;

import java.util.Objects;

/**
 * A data carrier class that doubles as a request and resposne model containing all information
 * that a Block would contain while protecting Controllers and Presenters from changes to
 * the original entity.
 * Day refers to the day that a given block occurs.
 * startTime and endTime refer to the start and end-time of a given class/tutorial/practical class.
 * room refers to the location of the class.
 */
public class BlockModel {

    private final int day;
    private final double startTime;
    private final double endTime;
    private final String room;

    public BlockModel(int day, double startTime, double endTime, String room){
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
    }

    public int getDay() {
        return day;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public String getRoom() {
        return room;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BlockModel that = (BlockModel) o;
        return getDay() == that.getDay() && Double.compare(that.getStartTime(), getStartTime()) == 0 && Double.compare(that.getEndTime(), getEndTime()) == 0 && getRoom().equals(that.getRoom());
    }

    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime, room);
    }

}
