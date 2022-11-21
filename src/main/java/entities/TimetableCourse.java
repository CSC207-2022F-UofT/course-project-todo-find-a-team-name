package entities;

import java.util.List;

/** A concrete implementation of Course that stores at most one tutorial section,
 * one practical section, and one lecture section.
 * Instance attributes are identical to those of Course.
 */

public class TimetableCourse extends Course{

    private Section lecture = null;
    private Section tutorial = null;
    private Section practical = null;

    /** TimetableCourse's default constructor.
     * Attempting to construct a TimetableCourse with multiple sections of the
     * same type will result in an InvalidSectionsException being raised.
     * All parameters of the constructors are otherwise identical to that of Course.
     * @param title The course's title.
     * @param sections The chosen sections of the course.
     * @param courseSession The course session (first or second).
     * @param courseCode The course's code.
     * @param breadth The course's breadth category (can be an empty string).
     * @throws InvalidSectionsException This exception is thrown when sections
     * contains more than one section of any single type (eg: LEC0101 and LEC0102).
     */
    public TimetableCourse(String title, List<Section> sections, String courseSession, String courseCode, String breadth)
            throws InvalidSectionsException{
        super(title, sections, courseSession, courseCode, breadth);

        for (Section section : sections){
            if (section.isLecture()){
                if (lecture != null){
                    throw new InvalidSectionsException("lecture");
                }
                lecture = section;
            }
            else if (section.isTutorial()){
                if (tutorial != null){
                    throw new InvalidSectionsException("tutorial");
                }
                tutorial = section;
            }
            else if (section.isPractical()){
                if (practical != null){
                    throw new InvalidSectionsException("practical");
                }
                practical = section;
            }
        }
    }

    /**
     * @param section the Section to be set as a Practical. This code does not check whether the code itself is a
     *                practical or not, and requires any other code to check prior to calling it.
     */
    public void setPractical(Section section) {
        if (practical != null){
            this.sections.remove(practical);
        }
        this.practical = section;
        this.sections.add(section);
    }

    /**
     * @param section the Section to be set as a Lecture. This code does not check whether the code itself is a
     *                lecture or not, and requires any other code to check prior to calling it.
     */
    public void setLecture(Section section) {
        if (lecture != null){
            this.sections.remove(lecture);
        }
        this.lecture = section;
        this.sections.add(section);
    }

    /**
     * @param section the Section to be set as a Tutorial. This code does not check whether the code itself is a
     *                tutorial or not, and requires any other code to check prior to calling it.
     */
    public void setTutorial(Section section) {
        if (tutorial != null){
            this.sections.remove(tutorial);
        }
        this.tutorial = section;
        this.sections.add(section);
    }

    /**
     * @param section the Section to be added to the course. Any section type that this section belongs to will be
     *                overridden by this newly added section.
     */
    public void setSection(Section section) {
        if (section.isLecture()){
            setLecture(section);
        }
        else if (section.isTutorial()){
            setTutorial(section);
        }
        else if (section.isPractical()){
            setPractical(section);
        }
        else {
            this.sections.add(section);
        }
    }

    /**
     * @return the course's lecture section.
     */
    public Section getLecture(){
        return this.lecture;
    }

    /**
     * @return the course's tutorial section.
     */
    public Section getTutorial(){
        return this.tutorial;
    }

    /**
     * @return the course's practical section.
     */
    public Section getPractical(){
        return this.practical;
    }
}



