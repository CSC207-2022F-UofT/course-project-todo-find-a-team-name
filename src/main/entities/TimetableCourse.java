package entities;

/** A concrete implementation of Course that stores at most one tutorial section,
 * one practical section, and one lecture section.
 *
 * Instance Attributes:
 * - tutorial: The tutorial section the user is attending for the course.
 * - practical:The practical section the user is attending for the course.
 * - lecture: The lecture section the user is attending for the course.
 * These instance attributes are initially null, representing that the user
 * has not selected a section of that type.
 * Attempting to construct a TimetableCourse with multiple sections of the
 * same type will result in an Exception being raised.
 */

public class TimetableCourse extends Course{
    private Section tutorial = null;
    private Section practical = null;
    private Section lecture = null;

    public TimetableCourse(List<Section> sections, String courseSession, String courseCode, String breadth){
        super(sections, courseSession, courseCode, breadth);
        for (Section section : sections){
            switch (section.getCode.slice(0, 3)){
                case "TUT":
                    if (this.tutorial != null){
                        throw new Exception("Tutorial section is already set.")
                    }
                    this.tutorial = section;
                case "PRA":
                    if (this.practical != null){
                        throw new Exception("Practical section is already set.")
                    }
                    this.practical = section;
                case "LEC":
                    if (this.tutorial != null){
                        throw new Exception("Tutorial section is already set.")
                    }
                    this.lecture = section;

            }

        }
    }


    public void setTutorial(Section section) {
        this.sections.remove(this.tutorial);
        this.tutorial = section;
        this.sections.add(section);
    }

    public void setPractical(Section section) {
        this.sections.remove(this.practical);
        this.practical = section;
        this.sections.add(section);
    }

    public void setLecture(Section section) {
        this.sections.remove(this.lecture);
        this.lecture = section;
        this.sections.add(section);
    }

    public Section getTutorial() {
        return this.tutorial;
    }

    public Section getPractical() {
        return this.practical;
    }

    public Section getLecture() {
        return this.lecture;
    }

}


