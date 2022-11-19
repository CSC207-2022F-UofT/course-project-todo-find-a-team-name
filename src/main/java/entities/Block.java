package entities;

public class Block {
//  a block has 4 elements, day as an integer (0,1,2,3,4), start and end time as doubles, room as a string
    private final int day;
    private final double startTime;
    private final double endTime;
    private final String room;

    public Block(String day, String startTime, String endTime, String room) {
        if (day.equals("MO")) {
            this.day = 0;
        } else if (day.equals("TU")) {
            this.day = 1;
        } else if (day.equals("WE")) {
            this.day = 2;
        } else if (day.equals("TH")) {
            this.day = 3;
        } else {
            this.day = 4;
        }


        String[] start = startTime.split(":");
        double startHour = Integer.parseInt(start[0]);
        double startMin = Integer.parseInt(start[1]);
        this.startTime = startHour + startMin / 100;

        String[] end = endTime.split(":");
        double endHour = Integer.parseInt(end[0]);
        double endMin = Integer.parseInt(end[1]);
        this.endTime = endHour + endMin / 100;

        this.room = room;
    }

    public int getDay() { return day; }

    public double getStartTime() { return startTime; }

    public double getEndTime() { return endTime; }

    public String getRoom() { return room; }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Block other)){
            return false;
        }

        return day == other.day && startTime == other.startTime && endTime == other.endTime && room.equals(other.room);
    }
}