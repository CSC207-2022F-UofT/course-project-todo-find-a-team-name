package entities;

import java.util.ArrayList;
import java.util.List;
/** An entity representing an InstructorConstraint.
 * Instance Attributes:
 * - instructor: A list of all instructorNames in the Constraint domain.
 * - super(isBlackList): a boolean showing b/w lists.
 *
 */
public class InstructorConstraint extends Constraint{
    private final List<String> instructorNames;

    public InstructorConstraint(List<String> instructorNames, boolean isBlackList){
        super(isBlackList);
        this.instructorNames = instructorNames;
    }

    /**
     * filter and modify the sections instance variable of a Course Object based on the
     * instructor user look forward to have or not have for the course.
     *
     * @param course a Course Object with section instance variable.
     */
    @Override
    public boolean filter(CalendarCourse course) {
        List<Section> copy = new ArrayList<>(course.getSections());
        boolean hasTutorial = course.hasTutorial();
        boolean hasLecture = course.hasLecture();
        boolean hasPractical = course.hasPractical();
        if (this.isBlackList()) {
            for (Section section : copy) {
                if (instructorNames.contains(section.getInstructorName())) {
                    course.removeSection(section);
                }
                if (hasTutorial != course.hasTutorial() || hasLecture != course.hasLecture() || hasPractical != course.hasPractical()) {
                    return false;
            }
        }
        } else {
            for (Section section : copy) {
                if (! instructorNames.contains(section.getInstructorName())) {
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
     * getter method
     * @return a list of String instructor's names
     */
    public List<String> getInstructorName() {
        return this.instructorNames;
    }

    @Override
    public String toString(){
        return "Instructor "+ super.toString() + ": "+ instructorNames;
    }

}
