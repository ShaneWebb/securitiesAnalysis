package io.local;

import io.console.SupportedArgs;
import io.datatypes.ParsedInfo;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public abstract class AbstractDataReader implements ExternalDataReader {

    protected static final String CLI_DATE_FORMAT = "MM/dd/yyyy";
    protected static final String FILE_DATE_FORMAT = "yyyy-MM-dd";

    protected String[] delimitedSources;
    protected String endDate;
    protected String header;
    protected String startDate;
    protected Charset charset;
    protected DateFormat dfCli;
    protected DateFormat dfFile;
    protected Date endDateF;
    protected Date startDateF;

    protected AbstractDataReader() {
        this.charset = StandardCharsets.UTF_8;
    }

    @Override
    public ParsedInfo read(MapWrapper<SupportedArgs, Object> parsedArgs)
            throws IOException {
        setArgs(parsedArgs);
        try {
            generateParsers();
        } catch (ParseException ex) {
            throw new IOException(ex);
        }
        return new ParsedInfo(readSources());
    }

    protected void setArgs(MapWrapper<SupportedArgs, Object> parsedArgs) {
        header = (String) parsedArgs.get(SupportedArgs.header);
        startDate = (String) parsedArgs.get(SupportedArgs.startDate);
        endDate = (String) parsedArgs.get(SupportedArgs.endDate);
    }

    protected MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>>
            readSources() throws IOException {
        MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> parsedSources = new HashMapWrapper<>();
        for (String source : delimitedSources) {
            final MapWrapper<Integer, MapWrapper<String, Object>> fileData
                    = readSource(source);
            parsedSources.put(source, fileData);
        }
        return parsedSources;
    }

    protected abstract MapWrapper<Integer, MapWrapper<String, Object>>
            readSource(String source) throws IOException;

    protected void generateParsers() throws ParseException {
        dfFile = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        dfCli = new SimpleDateFormat(CLI_DATE_FORMAT, Locale.ENGLISH);
        startDateF = dfCli.parse(startDate);
        endDateF = dfCli.parse(endDate);
    }
}
