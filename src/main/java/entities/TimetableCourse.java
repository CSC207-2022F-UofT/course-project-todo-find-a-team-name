package entities;

import java.util.List;

/** A concrete implementation of Course that stores at most one tutorial section,
 * one practical section, and one lecture section.
 * Instance attributes are identical to those of Course.
 */

public class TimetableCourse extends Course{

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

        boolean hasTutorial = false;
        boolean hasPractical = false;
        boolean hasLecture = false;

        for (Section section : sections){
            if (section.isLecture()){
                if (hasLecture){
                    throw new InvalidSectionsException("lecture");
                }
                hasLecture = true;
            }
            else if (section.isTutorial()){
                if (hasTutorial){
                    throw new InvalidSectionsException("tutorial");
                }
                hasTutorial = true;
            }
            else if (section.isPractical()){
                if (hasPractical){
                    throw new InvalidSectionsException("practical");
                }
                hasPractical = true;
            }
        }
    }
}



