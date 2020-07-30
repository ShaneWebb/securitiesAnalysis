package learner.java;

import datatypes.exceptions.ExternalDataException;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ExceptionTest {
    
    @Test
    public void customExceptionTest() {
        try {
            throw new ExternalDataException("Test");
            // fail("This portion of code should be unreachable"); //Compiler detects this.
        }
        catch(ExternalDataException e) {
            assertEquals("Test", e.getMessage(), "Exception message must be Test");
        }
    }
    
}
