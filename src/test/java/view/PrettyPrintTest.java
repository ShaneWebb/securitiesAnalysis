package view;

import org.junit.jupiter.api.Test;
import static org.junit.Assert.*;
import org.junit.jupiter.api.DisplayName;

public class PrettyPrintTest {
    
    public PrettyPrintTest() {
    }

    @Test
    @DisplayName("PrettyPrinter Builder test")
    public void testPrettyPrinterBuilder() {
        
        PrettyPrint myTestPrint = new PrettyPrint.Builder()
                    .border("*")
                    .filler(",")
                    .build();
        
        assertNotNull("The builder should create the object.", myTestPrint);
    }
    
}
