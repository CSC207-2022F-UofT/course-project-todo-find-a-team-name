package fileio_use_case;

import java.io.IOException;

public interface GatewayInterface {
    /**
     * @param file Path to JSON file
     * @return String
     */
    String fileToString(String file) throws IOException;
}
