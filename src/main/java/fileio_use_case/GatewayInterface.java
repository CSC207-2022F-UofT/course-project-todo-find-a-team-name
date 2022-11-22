package fileio_use_case;

import java.io.IOException;

public interface GatewayInterface {
    /** Converts JSON file to string
     * @return String
     */
    String fileToString() throws IOException;
}
