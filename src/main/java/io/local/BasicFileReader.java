package io.local;

//TODO: Create a file reader class, which accepts a 
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

//assigns variables accordingly. 
public class BasicFileReader {

    private Charset charset;

    public BasicFileReader() {
        this.charset = StandardCharsets.UTF_8;
    }

    public BasicFileReader(String fileName) {
        this.charset = StandardCharsets.UTF_8;
    }

    public MapWrapper<Integer, String> read(String filePath1) throws IOException {

        MapWrapper<Integer, String> fileByLine = new HashMapWrapper<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath1), charset)) {
            int i = 1;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                fileByLine.put(i, line);
                ++i;
            }
        } catch (IOException ex) {
            throw ex;
        }

        return fileByLine;

    }

}
