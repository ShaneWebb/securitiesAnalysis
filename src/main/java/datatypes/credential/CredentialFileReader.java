package datatypes.credential;

import datatypes.EnvironmentVariables;
import io.local.BasicFileReader;
import java.io.IOException;
import javautilwrappers.BasicMap;

public class CredentialFileReader implements CredentialReader {
    
    String fileName;
    
    public CredentialFileReader() {
        this.fileName = EnvironmentVariables.INSTANCE.getCredentialFileDirectory();
    }

    @Override
    public BasicMap<Integer, String> readIntoMap() {
        try {
            return getMapFromFileReader();
        } catch (IOException ex) {
            return getMapFromFallBackReader();
        }
    }

    private BasicMap<Integer, String> getMapFromFallBackReader() {
        CredentialReader fallbackReader = new CredentialConsoleReader();
        return fallbackReader.readIntoMap();
    }

    private BasicMap<Integer, String> getMapFromFileReader() throws IOException {
        BasicFileReader reader = new BasicFileReader(fileName);
        BasicMap<Integer, String> tempMap = reader.read();
        return tempMap;
    }
    
}
