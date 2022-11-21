package screens;

public class TimetableViewBlockModel{
    private final int day;
    private final double startTime;
    private final double endTime;

    public TimetableViewBlockModel(int day, double startTime, double endTime) {
        this.day = day;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    public int getDay(){
        return day;
    }
}