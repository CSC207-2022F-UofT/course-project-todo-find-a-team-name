package entities;



import java.util.ArrayList;
import java.util.List;
/** An entity representing a RoomConstraint.
 *
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
     */
    @Override
    public void filter(CalendarCourse course) {
        ArrayList<Section> copy = new ArrayList<>(course.getSections());
        for (Section section : copy) {
            if (this.evalRemoveSectionCondition(this.evalBlackListFilterCondition(section))) {
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
        for (Block block : section.getBlocks()){
            if (rooms.contains(block.getRoom())){
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return super.toString() + ": " + rooms;
    }
}

