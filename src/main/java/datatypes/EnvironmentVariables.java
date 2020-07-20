package datatypes;

import javautilwrappers.BasicMap;

public enum EnvironmentVariables {
    INSTANCE();
    
    private final String credentialLoadMethod;
    private final String credentialFileDirectory;
    private final BasicMap fileVariablePosition;
    
    //TODO: create loader to populate environment variables from file.
    EnvironmentVariables() {
        //Loader(
    }

    public String getCredentialLoadMethod() {
        return credentialLoadMethod;
    }

    public String getCredentialFileDirectory() {
        return credentialFileDirectory;
    }

}
