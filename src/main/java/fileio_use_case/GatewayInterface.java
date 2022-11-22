package fileio_use_case;

import java.io.IOException;

public interface GatewayInterface {
    /**
     * @return String
     */
    String fileToString() throws IOException;
}
