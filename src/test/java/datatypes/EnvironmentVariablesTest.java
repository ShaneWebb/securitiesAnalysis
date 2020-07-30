package datatypes;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class EnvironmentVariablesTest {
    
    public EnvironmentVariablesTest() {
    }

    @Test
    public void testValues() {
        EnvironmentVariables[] expResult = {EnvironmentVariables.INSTANCE};
        EnvironmentVariables[] result = EnvironmentVariables.values();
        assertArrayEquals(expResult, result, "Only one value - INSTANCE - may be present.");
    }

    @Test
    public void testGetCredentialLoadMethod() {
        EnvironmentVariables instance = EnvironmentVariables.INSTANCE;
        String expResult = "file1234";
        String result = instance.getCredentialLoadMethod();
        assertEquals(expResult, result, "For testing purposes, should be \"file1234\".");
    }

    @Test
    public void testGetCredentialFileDirectory() {
        EnvironmentVariables instance = EnvironmentVariables.INSTANCE;
        String expResult = "credentials.txt";
        String result = instance.getCredentialFileDirectory();
        assertEquals(expResult, result, "For testing purposes, should be \"credentials.txt\".");
    }
    
}
