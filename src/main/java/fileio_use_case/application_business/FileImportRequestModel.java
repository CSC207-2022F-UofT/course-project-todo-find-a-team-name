package fileio_use_case.application_business;

/** Input data class that takes in string of a file's path **/
public class FileImportRequestModel {
    private final String filePath;

    public FileImportRequestModel(String filePath) {
        this.filePath = filePath;
    }
    public String getFilePath() {
        return this.filePath;
    }

}