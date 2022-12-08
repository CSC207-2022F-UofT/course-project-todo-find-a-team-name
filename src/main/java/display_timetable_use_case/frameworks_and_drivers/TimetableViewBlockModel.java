package display_timetable_use_case.frameworks_and_drivers;

import java.util.Objects;

/**
 * Class representing all information needed for the block in displaying the timetable
 * Instance Attributes:
 *      - day: day of the week for this block represented as an integer from 0-4 (e.g 0 = monday, 4 = friday)
 *      - startTime: start time of the block represented as double (e.g. 13:00 = 13, 09:30 = 9.5)
 *      - endTime: end time of the block represented as double (e.g. 13:00 = 13, 09:30 = 9.5)
 */
public class TimetableViewBlockModel{
    private final int day;
    private final double startTime;
    private final double endTime;

    /**
     * Constructs TimetableViewBlockModel with the given day (day of the week), start time, and end time.
     *
     * @param day day of the week for this block
     * @param startTime start time of the block
     * @param endTime end time of the block
     */
    public TimetableViewBlockModel(int day, double startTime, double endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns the start time of the block represented by this model
     *
     * @return start time of the block
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Returns the end time of the block represented by this model
     *
     * @return end time of the block
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * Returns the day of the week for this block
     *
     * @return day of the week for this block
     */
    public int getDay(){
        return day;
    }

    /**
     * Return whether this object is equal to obj
     * @param obj object compared
     * @return whether this object is equal to obj
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TimetableViewBlockModel)){
            return false;
        }

        TimetableViewBlockModel other = (TimetableViewBlockModel) obj;

        return day == other.day && startTime == other.startTime && endTime == other.endTime;
    }

    @Override
    public String toString() {
        return "TimetableViewBlockModel{" +
                "day=" + day +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }

    /**
     * Returns a hash code value for this object.
     * If two objects are equal based on equals method, hashCode also returns same integers.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(day, startTime, endTime);
    }
}