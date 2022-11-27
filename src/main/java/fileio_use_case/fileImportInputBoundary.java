package fileio_use_case;

import java.io.IOException;

public interface fileImportInputBoundary {
    /** Returns string representation of JSON file
     * @param filePath - fileImportRequestModel, which stores a string of file path to JSON file
     * @return String
     */
    String fileToString(fileImportRequestModel filePath) throws IOException;
}
