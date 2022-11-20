package entities;

public class InvalidSectionsException extends Exception {
    public InvalidSectionsException(String sectionType) {
        super("More than one " + sectionType + " was listed for this course.");
    }
}
