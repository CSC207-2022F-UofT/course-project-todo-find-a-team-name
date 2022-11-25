package recommend_br_use_case;

/**
 * The class containing day, start time and end time of the block.
 * It is used only to bundle up data in the block.
 *
 */
public class BRBlockResponseModel {
    private int day;
    private double startTime;
    private double endTime;

    /**
     * Create data structure representing the block,
     * given day, startTime, and endTime of the block
     *
     * @param day day of the block
     * @param startTime start time of the block
     * @param endTime end time of the block
     */
    public BRBlockResponseModel(int day, double startTime, double endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Returns day of the block this model is representing
     *
     * @return day of the block this model is representing
     */
    public int getDay() {
        return day;
    }

    /**
     * Returns start time of the block this model is representing
     *
     * @return start time of the block this model is representing
     */
    public double getStartTime() {
        return startTime;
    }

    /**
     * Returns end time of the block this model is representing
     *
     * @return end time of the block this model is representing
     */
    public double getEndTime() {
        return endTime;
    }

    /**
     * Set the day of the block this model is representing to given day
     *
     * @param day new day of the block
     */
    public void setDay(int day) {
        this.day = day;
    }

    /**
     * Set the start time of the block this model is representing to given startTime
     *
     * @param startTime new start time of the block
     */
    public void setStartTime(double startTime) {
        this.startTime = startTime;
    }

    /**
     * Set the end time of the block this model is representing to given endTime
     *
     *
     * @param endTime new end time of the block
     */
    public void setEndTime(double endTime) {
        this.endTime = endTime;
    }
}
