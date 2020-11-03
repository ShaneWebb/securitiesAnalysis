package io.local;

//TODO: Create a file reader class, which accepts a 
import datatypes.exceptions.ItemNotFoundException;
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
import process.datatypes.ParsedFile;

//assigns variables accordingly. 
public class FileReader implements ExternalDataReader {

    private Charset charset;
    private static final String FILE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String CLI_DATE_FORMAT = "MM/dd/yyyy";

    public FileReader() {
        this.charset = StandardCharsets.UTF_8;
    }

    public FileReader(String fileName) {
        this.charset = StandardCharsets.UTF_8;
    }

    @Override
    public ParsedFile read(MapWrapper<String, Object> parsedArgs)
            throws IOException {

        String files = (String) parsedArgs.get("files");
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();
        for (String file : delimitedFiles) {
            final MapWrapper<Integer, String> fileData
                    = readFile(file, (String) parsedArgs.get("header"),
                            (String) parsedArgs.get("startDate"),
                            (String) parsedArgs.get("endDate"));

            parsedFiles.put(file, fileData);
        }
        return new ParsedFile(parsedFiles);
    }

    private MapWrapper<Integer, String> readFile(
            String filePath1, String header,
            String startDateStr, String endDateStr)
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

            DateFormat dfFile = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
            DateFormat dfCli = new SimpleDateFormat(CLI_DATE_FORMAT, Locale.ENGLISH);

            Date startDate = dfCli.parse(startDateStr);
            Date endDate = dfCli.parse(endDateStr);

            int i = 1;
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                String[] splitLine = line.split(",");
                String value = splitLine[index];
                String date = splitLine[0];
                Date parsedDate = dfFile.parse(date);
                if (parsedDate.compareTo(startDate) >= 0 && parsedDate.compareTo(endDate) <= 0) {
                    fileByLine.put(i, date + "," + value);
                    ++i;
                }

            }
        } catch (IOException | ItemNotFoundException | ParseException ex) {
            throw new IOException(ex);
        }

        return fileByLine;

    }


}
