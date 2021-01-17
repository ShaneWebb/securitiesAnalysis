package io.local;

//TODO: Create a file reader class, which accepts a 
import datatypes.exceptions.ItemNotFoundException;
import io.console.SupportedArgs;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

//assigns variables accordingly. 
public class FileReader extends AbstractDataReader {


    public FileReader() {
        super();
    }

    @Override
    protected final void setArgs(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super.setArgs(parsedArgs);
        String files = (String) parsedArgs.get(SupportedArgs.files);
        delimitedSources = files.split(",");
    }

    protected final MapWrapper<Integer, MapWrapper<String, Object>> readSource(
            String file) throws IOException {

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
    
}
