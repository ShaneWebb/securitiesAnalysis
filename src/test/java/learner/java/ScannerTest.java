package learner.java;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScannerTest {

    public ScannerTest() {
    }

    public class InputOutput {

        public String getInput() {
            Scanner sc = new Scanner(System.in);
            return sc.nextLine();
        }
    }

    @Test
    public void scannerBasicTest() {
        InputOutput inputOutput = new InputOutput();

        String input = "add 5";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        
        assertEquals("add 5", inputOutput.getInput());
    }

}
