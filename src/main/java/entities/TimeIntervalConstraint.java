package entities;

import java.util.ArrayList;
/**
 * An entity representing an TimeConstraint.
 * Instance Attributes:
 * - startTime: A Limit for startTime.
 * - endTime: A Limit for EndTime.
 * - super(isBlackList): a boolean showing b/w lists.
 */
public class TimeIntervalConstraint extends Constraint{
    private final double startTime;
    private final double endTime;

    public TimeIntervalConstraint(double startTime, double endTime, boolean isBlackList){
        super(isBlackList);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * startTime limit and endTime limit.
     *
     * @param course the course to be modified with filtered sections.
     * @return a boolean value. First check if the course sections contain a lecture, practical, or tutorial, and
     * return true if the modified course sections still contain at least one of each original course section type.
     */
    @Override
    public boolean filter(CalendarCourse course) {
        ArrayList<Section> copy = new ArrayList<>(course.getSections());
        boolean hasTutorial = course.hasTutorial();
        boolean hasLecture = course.hasLecture();
        boolean hasPractical = course.hasPractical();
        if (isBlackList()){
            for (Section section : copy){
                if (evalBlackListRemoveCondition(section)){
                    course.removeSection(section);
                }
                if (hasTutorial != course.hasTutorial() || hasLecture != course.hasLecture() || hasPractical != course.hasPractical()) {
                    return false;
                }
            }
        } else {
            for (Section section : copy){
                if (evalWhiteListRemoveCondition(section)) {
                    course.removeSection(section);
                }
                if (hasTutorial != course.hasTutorial() || hasLecture != course.hasLecture() || hasPractical != course.hasPractical()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * a helper method that loops through the blocks of a section to evaluate the whether the section should be
     * removed if the timeConstraint is a blacklist.
     *
     * @param section a Section Entity
     * @return a boolean indicating the RemoveCondition of a BlackList
     */
    private boolean evalBlackListRemoveCondition(Section section) {
        ArrayList<Block> blocks = new ArrayList<>(section.getBlocks());
        for (Block block : blocks){
            if (checkOverLap(block.getStartTime(), block.getEndTime())){
                return true;
            }
        }
        return false;
    }
    /**
     * a helper method that loops through the blocks of a section to evaluate the whether the section should be
     * removed if the timeConstraint is a whitelist.
     *
     * @param section a Section Entity
     * @return a boolean indicating the RemoveCondition of a BlackList.
     */
    private boolean evalWhiteListRemoveCondition(Section section) {
        for (Block block: section.getBlocks()){
            if (block.getEndTime() <= startTime || block.getStartTime() >= endTime){
                return true;
            }
        }
        return false;
    }

    /**
     * a helper method that checks the overlaps between interval of TimeConstraint startTime and endTime
     * and interval of block startTime and endTime.
     *
     * @param blockStartTime startTime of a block
     * @param blockEndTime endTime of a block
     * @return a boolean indicating the RemoveCondition of a BlackList.
     */
    private boolean checkOverLap(double blockStartTime, double blockEndTime) {
        return (blockStartTime < endTime && endTime < blockEndTime) ||
                (blockStartTime < startTime && startTime < blockEndTime) ||
                (startTime <= blockStartTime && blockEndTime <= endTime);

    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

    /**
     * helper method for formatting.
     * @param time a double representing time.
     * @return a string that is in the form of HH:MM.
     */
    private String formatTime(Double time) {
        int hour = time.intValue();
        int min = (int) Math.round((time - time.intValue()) * 60);
        if (min == 0) {
            return String.format("%s:%s", hour, "00");
        }
        return String.format("%s:%s", hour, min);
    }

    @Override
    public String toString(){
        return "Time " + super.toString() + ": " + formatTime(startTime) + "-" + formatTime(endTime);
    }
}


