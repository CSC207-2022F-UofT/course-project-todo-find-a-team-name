package display_timetable_use_case.interface_adapters;

/**
 * Class representing all information needed for the block in displaying the timetable
 * Instance Attributes:
 *      - day: day of the week for this block represented as an integer from 0-4 (e.g 0 = monday, 4 = friday)
 *      - startTime: start time of the block represented as double (e.g. 13:00 = 13, 09:30 = 9.5)
 *      - endTime: end time of the block represented as double (e.g. 13:00 = 13, 09:30 = 9.5)
 */
public class TimetableViewBlockModel {
    private final int day;
    private final double startTime;
    private final double endTime;
    private String room;

    private final String room;

    /**
     * Constructs TimetableViewBlockModel with the given day (day of the week), start time, and end time.
     *
     * @param day       day of the week for this block
     * @param startTime start time of the block
     * @param endTime   end time of the block
     * @param room      name of the room
     */
    public TimetableViewBlockModel(int day, double startTime, double endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = "";
    }

    public TimetableViewBlockModel(int day, double startTime, double endTime, String room) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
        this.room = room;
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
    public int getDay() {
        return day;
    }

    public String getRoom() {
        return this.room; }
}