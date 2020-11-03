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
import process.datatypes.ParsedDatabase;
import process.datatypes.ParsedFile;

//assigns variables accordingly. 
public class ExternalDataReader {

    private Charset charset;

    public ExternalDataReader() {
        this.charset = StandardCharsets.UTF_8;
    }

    public ExternalDataReader(String fileName) {
        this.charset = StandardCharsets.UTF_8;
    }

    public ParsedFile readFiles(String files) throws IOException {
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();
        for (String file : delimitedFiles) {
            parsedFiles.put(file, readFile(file));
        }
        return new ParsedFile(parsedFiles);
    }
    
    //TODO: Refactor to require parsedArgs.
    private MapWrapper<Integer, String> readFile(String filePath1) throws IOException {

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
        
        fileByLine.remove(1);
        return fileByLine;

    }
    
    public ParsedDatabase readDB(
            MapWrapper<String, Object> parsedArgs) throws IOException {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
