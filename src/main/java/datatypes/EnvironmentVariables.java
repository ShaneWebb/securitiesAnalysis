package datatypes;

import io.local.BasicFileReader;
import java.io.IOException;
import javautilwrappers.BasicMap;

public enum EnvironmentVariables {
    INSTANCE();
    
    private String credentialLoadMethod;
    private String credentialFileDirectory;

    public void loadFromFile(String fileName) {
        BasicMap<Integer, String> tempMap = new BasicMap<>();
        try {
            BasicFileReader reader = new BasicFileReader(fileName);
            tempMap = reader.read();
        } 
        catch (IOException ex) {
            //TODO: Properly shut down program - failure to load env variables
            //should be fatal.
        }
        
        this.credentialLoadMethod = tempMap.get(1);
        this.credentialFileDirectory = tempMap.get(2);
    }

    public String getCredentialLoadMethod() {
        return credentialLoadMethod;
    }

    public String getCredentialFileDirectory() {
        return credentialFileDirectory;
    }

}
