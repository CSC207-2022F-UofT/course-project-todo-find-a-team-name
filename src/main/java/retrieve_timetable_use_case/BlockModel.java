package retrieve_timetable_use_case;

public class BlockModel {

    private int day;
    private double startTime;
    private double endTime;
    private String room;

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
}
