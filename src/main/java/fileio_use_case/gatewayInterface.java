package fileio_use_case;

import java.io.IOException;

public interface gatewayInterface {
    /** Converts JSON file to string
     * @return String
     */
    String fileToString(String filePath) throws IOException;
}
