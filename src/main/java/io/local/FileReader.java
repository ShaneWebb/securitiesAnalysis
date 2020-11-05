package io.local;

//TODO: Create a file reader class, which accepts a 
import datatypes.exceptions.ItemNotFoundException;
import io.console.SupportedArgs;
import io.datatypes.ParsedFile;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

//assigns variables accordingly. 
public class FileReader implements ExternalDataReader {

    private Charset charset;
    private static final String FILE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String CLI_DATE_FORMAT = "MM/dd/yyyy";
    private String endDate;
    private String startDate;
    private String header;
    private String[] delimitedFiles;

    public FileReader() {
        this.charset = StandardCharsets.UTF_8;
    }

    public FileReader(String fileName) {
        this.charset = StandardCharsets.UTF_8;
    }

    @Override
    public ParsedFile read(MapWrapper<SupportedArgs, Object> parsedArgs)
            throws IOException {

        setArgs(parsedArgs);
        return new ParsedFile(readAll());
    }

    private MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> readAll()
            throws IOException {
        MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>>
                parsedFiles = new HashMapWrapper<>();
        for (String filePath : delimitedFiles) {
            final MapWrapper<Integer, MapWrapper<String, Object>> fileData
                    = readFile(filePath);
            parsedFiles.put(filePath, fileData);
        }
        return parsedFiles;
    }

    private void setArgs(MapWrapper<SupportedArgs, Object> parsedArgs) {
        String files = (String) parsedArgs.get(SupportedArgs.files);
        delimitedFiles = files.split(",");
        header = (String) parsedArgs.get(SupportedArgs.header);
        startDate = (String) parsedArgs.get(SupportedArgs.startDate);
        endDate = (String) parsedArgs.get(SupportedArgs.endDate);
    }

    private MapWrapper<Integer, MapWrapper<String, Object>> readFile(
            String file)
            throws IOException {

        MapWrapper<Integer, MapWrapper<String, Object>> fileByLine;
        try (BufferedReader bufferedReader = Files.newBufferedReader(Paths.get(file), charset)) {
            fileByLine = bufferedRead(bufferedReader);
        } catch (IOException | ItemNotFoundException | ParseException ex) {
            throw new IOException(ex);
        }
        return fileByLine;
    }

    private MapWrapper<Integer, MapWrapper<String, Object>> bufferedRead(
            final BufferedReader bufferedReader) 
            throws ItemNotFoundException, IOException, NumberFormatException, ParseException {
        
        generateParsers();
        
        String firstLine = bufferedReader.readLine();
        
        int index = findHeaderIndex(firstLine);
        return readLines(bufferedReader, index);
    }

    private MapWrapper<Integer, MapWrapper<String, Object>> readLines(
            final BufferedReader bufferedReader, int index)
            throws IOException, ParseException, NumberFormatException {
        int i = 1; String line;
        MapWrapper<Integer, MapWrapper<String, Object>> fileByLine = new HashMapWrapper<>();
        while ((line = bufferedReader.readLine()) != null) {
            String[] splitLine = line.split(",");
            double value = Double.valueOf(splitLine[index]);
            String date = splitLine[0];
            Date parsedDate = dfFile.parse(date);
            
            if (parsedDate.compareTo(startDateF) >= 0 && parsedDate.compareTo(endDateF) <= 0) {
                MapWrapper<String, Object> parsedLine = new HashMapWrapper<>();
                parsedLine.put("date", parsedDate);
                parsedLine.put("value", value);
                fileByLine.put(i, parsedLine);
                ++i;
            }
        }
        return fileByLine;
    }

    private int findHeaderIndex(String firstLine)
            throws IOException, ItemNotFoundException {
        int index;
        if (firstLine != null) {
            index = new ArrayListWrapper(Arrays.asList(firstLine.split(","))).indexOf(header);
        } else {
            throw new IOException("File is empty!");
        }
        return index;
    }

    private void generateParsers() throws ParseException {
        dfFile = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        dfCli = new SimpleDateFormat(CLI_DATE_FORMAT, Locale.ENGLISH);
        startDateF = dfCli.parse(startDate);
        endDateF = dfCli.parse(endDate);
    }
    
    private Date endDateF;
    private Date startDateF;
    private DateFormat dfCli;
    private DateFormat dfFile;


}
