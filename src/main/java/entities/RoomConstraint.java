package entities;

import java.util.ArrayList;
import java.util.List;
/** An entity representing a RoomConstraint.
 * Instance Attributes:
 * - rooms: A list of all rooms in the Constraint domain.
 * - super(isBlackList): a boolean showing b/w lists.
 *
 */
public class RoomConstraint extends Constraint{
    private final List<String> rooms;

    public RoomConstraint(List<String> rooms, boolean isBlackList){
        super(isBlackList);
        this.rooms = rooms;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * type of constraint.
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
     * @return a boolean indicating the RemoveCondition of a BlackList Constraint.
     */
    private boolean evalBlackListRemoveCondition(Section section) {
        for (Block block : section.getBlocks()){
            if (rooms.contains(block.getRoom())){
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
     * @return a boolean indicating the RemoveCondition of a Whitelist Constraint.
     */
    private boolean evalWhiteListRemoveCondition(Section section) {
        for (Block block : section.getBlocks()){
            if (! rooms.contains(block.getRoom())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return "Room " + super.toString() + ": " + rooms;
    }
}

