package datatypes;

import io.local.BasicFileReader;
import java.io.IOException;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public enum EnvironmentVariables {
    INSTANCE();
    
    private String credentialLoadMethod;
    private String credentialFileDirectory;

    public void loadFromFile(String fileName) {
        MapWrapper<Integer, String> tempMap = new HashMapWrapper<>();
        try {
            BasicFileReader reader = new BasicFileReader();
            tempMap = reader.read(fileName);
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
