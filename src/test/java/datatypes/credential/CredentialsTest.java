package datatypes.credential;

import datatypes.EnvironmentVariables;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class CredentialsTest {
    
    public CredentialsTest() {
    }


    @Test
    public void testPopulate() {
        String address, username, password;
        Credentials instance = new Credentials();
        instance.populate(EnvironmentVariables.INSTANCE);
        
        address = instance.getAddress();
        username = instance.getUsername();
        password = instance.getPassword();
        
        assertEquals("testaddress1234", address, "Must equal values in file!");
        assertEquals("testusername5678", username, "Must equal values in file!");
        assertEquals("testpassword", password, "Must equal values in file!");
        
    }
    
}
