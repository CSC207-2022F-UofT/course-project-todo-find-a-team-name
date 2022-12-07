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

    /**
     * A helper method for formatting the Weekday input
     * @param dayList a list of integer representing Monday, Tuesday.... using int 0, 1, 2 ...
     * @return a List of String representing the weekdays
     */
    private List<String> formatDay(List<Integer> dayList) {
        ArrayList<String> formattedDayList = new ArrayList<>();
        for (int i: dayList) {
            if (i == 0) {
                formattedDayList.add("MO");
            }
            else if (i == 1){
                formattedDayList.add("TU");
            }
            else if (i == 2){
                formattedDayList.add("WE");
            }
            else if (i == 3){
                formattedDayList.add("TH");
            }
            else if (i == 4){
                formattedDayList.add("FR");
            }
        }
        return formattedDayList;
    }

    @Override
    public String toString(){
        return "Weekday " + super.toString() + ": " + formatDay(days);
    }


}
