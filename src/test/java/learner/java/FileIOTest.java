package learner.java;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javautilwrappers.BasicHashMap;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class FileIOTest {
    
    FileIOTest() {
    }
    
    @ Test
    @ Disabled
    public void getWorkingDirectory(){
        System.out.println(new File(".").getAbsolutePath());
        assertTrue(true);
    }
    
    @ Test
    // @ Disabled
    public void bufferedReaderTest() {
        
        Path filePath = Paths.get("./src/test/testfiles/bufferedreader.txt");
        Charset charset = StandardCharsets.UTF_8;
        BasicHashMap<Integer, String> fileByLine = new BasicHashMap<>();
        
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
            
            int i = 1;
            String line;
            
            while ((line = bufferedReader.readLine()) != null) {
                fileByLine.put(i, line);
                ++i;
            }
        } catch (IOException ex) {
            fail("I/O exception: " + ex);
        }
        
        assertEquals("Hello", fileByLine.get(1), "First line must be Hello");
        assertEquals("World",fileByLine.get(2), "First line must be World");
        
    }
    
}
