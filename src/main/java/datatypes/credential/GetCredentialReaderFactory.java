package datatypes.credential;

import datatypes.EnvironmentVariables;

public class GetCredentialReaderFactory {

    String loadMethodStr;
    SupportedLoadMethods loadMethod;
    
    GetCredentialReaderFactory(EnvironmentVariables INSTANCE) {
        try {
            setLoadMethodFromEnvironmentVariable(INSTANCE);
        } 
        catch (IllegalArgumentException ex) {
            System.out.println("Illegal Argument Exception");
            fallbackSetLoadMethod();
        }
    }

    private void fallbackSetLoadMethod() {
        //TODO: Log the presence of an invalid environment variable.
        loadMethod = SupportedLoadMethods.FILE;
    }

    private void setLoadMethodFromEnvironmentVariable(EnvironmentVariables INSTANCE) {
        loadMethodStr = INSTANCE.getCredentialLoadMethod();
        loadMethod = SupportedLoadMethods.valueOf(loadMethodStr.toUpperCase());
    }
    
    public CredentialReader getCredentialReader(){
       
        switch(loadMethod){
            case FILE:
                return new CredentialFileReader();
            case CONSOLE:
                return new CredentialConsoleReader();
            default:
                return new CredentialFileReader();
        }
        
    }
    
}
