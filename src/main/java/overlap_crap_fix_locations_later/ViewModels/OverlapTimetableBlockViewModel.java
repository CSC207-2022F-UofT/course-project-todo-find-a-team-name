package overlap_crap_fix_locations_later.ViewModels;

/**
 * A helper ViewModel representing a Block for the OverlapInput Dialog.
 * Data is identical to the Entity version.
 */
public class OverlapTimetableBlockViewModel {
    private final int day;
    private final double startTime;
    private final double endTime;
    private final String room;

    public OverlapTimetableBlockViewModel(int day, double startTime, double endTime, String room) {
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

    /**
     * An equals() method used for comparison. Essentially just compares to see if the object is of the correct
     * type and has identical fields.
     *
     * @param o - the other object to compare this one with.
     * @return - well, equal or not.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OverlapTimetableBlockViewModel that = (OverlapTimetableBlockViewModel) o;
        return getDay() == that.getDay() && Double.compare(that.getStartTime(), getStartTime()) == 0
                && Double.compare(that.getEndTime(), getEndTime()) == 0 && getRoom().equals(that.getRoom());
    }

}
