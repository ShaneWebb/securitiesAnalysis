package io.local;

//TODO: Create a file reader class, which accepts a 
import datatypes.exceptions.ItemNotFoundException;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import javautilwrappers.ArrayListWrapper;
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

    public ParsedFile readFiles(MapWrapper<String, Object> parsedArgs)
            throws IOException {
        
        String files = (String) parsedArgs.get("files");
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();
        for (String file : delimitedFiles) {
            parsedFiles.put(file, readFile(file, (String) parsedArgs.get("header")));
        }
        return new ParsedFile(parsedFiles);
    }
    
    //TODO: Refactor to require parsedArgs.
    private MapWrapper<Integer, String> readFile(String filePath1, String header)
            throws IOException {

        MapWrapper<Integer, String> fileByLine = new HashMapWrapper<>();
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(filePath1), charset)) {
            
            int index;
            String firstLine = bufferedReader.readLine();
            if (firstLine != null) {
                index = new ArrayListWrapper(Arrays.asList(firstLine.split(","))).indexOf(header);
            } else {
                throw new IOException("File is empty!");
            }
            
            int i = 1;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                String value = splitLine[index];
                String date = splitLine[0];
                fileByLine.put(i, date + "," + value);
                ++i;
            }
        } catch (IOException | ItemNotFoundException ex) {
            throw new IOException(ex);
        }

        return fileByLine;

    }
    
    public ParsedDatabase readDB(
            MapWrapper<String, Object> parsedArgs) throws IOException {
        throw new UnsupportedOperationException("Not implemented yet!");
    }

}
