package entities;

import java.util.ArrayList;
import java.util.List;
/**
 * An entity representing an TimeConstraint.
 * Instance Attributes:
 * - days: A List of days.
 * - super(isBlackList): a boolean showing b/w lists.
 * Note: every weekday from Monday to Friday is represented by an integer from 0 to 4.
 */
public class WeekdayConstraint extends Constraint{
    private final List<Integer> days;

    public WeekdayConstraint(List<Integer> days, boolean isBlackList) {
        super(isBlackList);
        this.days = days;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * given weekday.
     *
     * @param course the course to be modified with filtered sections.
     */
    @Override
    public void filter(CalendarCourse course) {
        ArrayList<Section> copy = new ArrayList<>(course.getSections());
        if (this.isBlackList()) {
            for (Section section : copy) {
                if (this.evalBlackListRemoveCondition(section)) {
                    course.removeSection(section);
                }
            }
        } else {
            for (Section section : copy) {
                if (this.evalWhiteListRemoveCondition(section)) {
                    course.removeSection(section);
                }
            }
        }
    }

    /**
     * a helper method that loop through the blocks of a section to evaluate the whether the section should be
     * removed if the Weekday Constraint is a blacklist.
     *
     * @param section a section entity
     * @return a boolean indicating the RemoveCondition of a BlackList.
     */
    private boolean evalBlackListRemoveCondition(Section section) {
        for (Block block : section.getBlocks()){
            if (days.contains(block.getDay())){
                return true;
            }
        }
        return false;
    }

    /**
     * a helper method that loop through the blocks of a section to evaluate the whether the section should be
     * removed if the Weekday Constraint is a whitelist.
     *
     * @param section a section entity
     * @return a boolean indicating the RemoveCondition of a WhiteList.
     */
    private boolean evalWhiteListRemoveCondition(Section section) {
        for (Block block : section.getBlocks()){
            if (! days.contains(block.getDay())){
                return true;
            }
        }
        return false;
    }


    public List<Integer> getWeekdays() {
        return days;
    }

    @Override
    public String toString(){
        return "Weekday " + super.toString() + ": " + days;
    }


}
