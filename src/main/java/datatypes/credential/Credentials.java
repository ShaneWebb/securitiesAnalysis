package datatypes.credential;

import datatypes.EnvironmentVariables;
import javautilwrappers.BasicMap;

public class Credentials {
    private String address;
    private String username;
    private String password;
    
    public Credentials(){
        
    }
    
    //TODO: Refactor to be more modular, instead of having a monolithic file.
    public void populate(EnvironmentVariables INSTANCE) {
        CredentialReader reader = new GetCredentialReaderFactory(INSTANCE).getCredentialReader();
        BasicMap<Integer, String> map = reader.readIntoMap();
        assignFieldsFromMap(map);
    }
    
    private void assignFieldsFromMap(BasicMap<Integer, String> tempMap) {
        //Order matters. 
        this.address = tempMap.get(1);
        this.username = tempMap.get(2);
        this.password = tempMap.get(3);
    }

    public String getAddress() {
        return address;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
    
}
