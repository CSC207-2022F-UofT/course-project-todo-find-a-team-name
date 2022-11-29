package fileio_use_case;

import java.io.IOException;

public interface FileImportInputBoundary {
    /** Returns string representation of JSON file
     * @param filePath - FileImportRequestModel, which stores a string of file path to JSON file
     * @return String
     */
    String fileToString(FileImportRequestModel filePath) throws IOException;
}
