package entities;

import java.util.ArrayList;
/**
 * An entity representing an TimeConstraint.
 *
 * Instance Attributes:
 * - startTime: A Limit for startTime.
 * - endTime: A Limit for EndTime.
 * - super(isBlackList): a boolean showing b/w lists.
 *
 */
public class TimeIntervalConstraint extends Constraint{
    private final double startTime;
    private final double endTime;

    public TimeIntervalConstraint(int startTime, int endTime, boolean isBlackList){
        super(isBlackList);
        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * startTime limit and endTime limit.
     *
     * @param course the course to be modified with filtered sections.
     */
    @Override
    public void filter(CalendarCourse course) {
        ArrayList<Section> copy = new ArrayList<>(course.getSections());
        for (Section section : copy) {
            if (this.evalRemoveSectionCondition(this.evalBlackListFilterCondition(section))){
                course.removeSection(section);
            }
        }
    }

    /**
     * a helper method that loops through the blocks of a section to evaluate the whether the section should be
     * removed if the timeConstraint is a blacklist.
     *
     * @param section a Section Entity
     * @return a boolean indicating the RemoveCondition of a BlackList.
     */
    private boolean evalBlackListFilterCondition(Section section) {
        ArrayList<Block> blocks = new ArrayList<>(section.getBlocks());
        for (Block block : blocks){
            if (block.getStartTime() < startTime || block.getEndTime() > endTime){
                return true;
            }
        }
        return false;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getEndTime() {
        return endTime;
    }

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
        return "Time" + super.toString() + ": " + formatTime(startTime) + "-" + formatTime(endTime);
    }
}
