package io.local;

//TODO: Create a file reader class, which accepts a 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javautilwrappers.BasicMap;

//assigns variables accordingly. 
public class BasicFileReader {
    
    Path filePath;
    Charset charset;
    
    public BasicFileReader(String fileName) {
        this.filePath = Paths.get(fileName);
        this.charset = StandardCharsets.UTF_8;
    }
    
    public BasicMap<Integer, String> read() throws IOException {
        
        BasicMap<Integer, String> fileByLine = new BasicMap<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(filePath, charset)) {
            int i = 1;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileByLine.put(i, line);
                ++i;
            }
        }
        catch (IOException ex) {
            throw ex;
        }

        return fileByLine;
        
    }
    
}
