package fileio_use_case;

/** Input data class that takes in string of a file's path **/
public class fileImportRequestModel {
    private final String filePath;

    public fileImportRequestModel(String filePath) {
        this.filePath = filePath;
    }
    public String getFilePath() {
        return this.filePath;
    }

}