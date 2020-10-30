package view.chartdata;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import datatypes.exceptions.ItemNotFoundException;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;

public abstract class AbstractChartData implements ChartDataWrapper {

    protected String header;
    protected Date startDate, endDate;
    protected static final int HEADER_LINE = 1;
    protected static final int X_INDEX = 0;
    protected final String CLI_DATE_FORMAT, FILE_DATE_FORMAT;

    public AbstractChartData(MapWrapper<String, Object> parsedArgs) {
        this.FILE_DATE_FORMAT = "yyyy-MM-dd";
        this.CLI_DATE_FORMAT = "MM/dd/yyyy";
        this.header = (String) parsedArgs.get("header");

        DateFormat df = new SimpleDateFormat(CLI_DATE_FORMAT, Locale.ENGLISH);
        try {
            String startDateStr = (String) parsedArgs.get("startDate");
            this.startDate = df.parse(startDateStr);
            String endDateStr = (String) parsedArgs.get("endDate");
            this.endDate = df.parse(endDateStr);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

    @Override
    public final AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException {
        ListWrapper<ChartSubDataWrapper> chartData = new ArrayListWrapper<>();
        for (MapWrapper.Entry<String, MapWrapper<Integer, String>> file : parsedFiles.entrySet()) {
            try {
                ChartSubDataWrapper series = buildSeries(file);
                chartData.add(series);
            } catch (ItemNotFoundException | ParseException ex) {
                throw new IOException(ex);
            }
        }
        assembleData(chartData);
        return this;
    }

    //Must implement method for specific chart to parse a data line properly.
    protected abstract MapWrapper<String, Object> parseSingleCsvLine(String csvLine, int colIndex) throws ParseException, NumberFormatException;

    //Must create the correct sub data structure.
    protected abstract ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file);

    //May override to adjust selection criterion.
    protected void addToSeriesIfValid(MapWrapper<String, Object> trialData, ChartSubDataWrapper subData) {
        Date candidateDate = (Date) trialData.get("date");
        if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            subData.add(trialData);
        }
    }
    
    protected final void assembleData(ListWrapper<ChartSubDataWrapper> chartData) {
        for (ChartSubDataWrapper series : chartData) {
            this.addSubDataToInternalCollection(series);
        }
    }

    protected final Date createDate(ListWrapper<String> delimitedData) throws ParseException {
        String dateStr = delimitedData.get(X_INDEX);
        DateFormat df = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        Date parsedDate = df.parse(dateStr);
        return parsedDate;
    }

    private ChartSubDataWrapper buildSeries(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) throws ItemNotFoundException, ParseException, NumberFormatException {
        ChartSubDataWrapper series = ChartSubDataFactory(file);
        return populateSeries(file, series);
    }

    private ChartSubDataWrapper populateSeries(MapWrapper.Entry<String, MapWrapper<Integer, String>> file, ChartSubDataWrapper series) throws NumberFormatException, ItemNotFoundException, ParseException {
        MapWrapper<Integer, String> originalContents = new HashMapWrapper(file.getValue());
        int colIndex = findHeaderIndex(originalContents);

        MapWrapper<Integer, String> contentsCopy = new HashMapWrapper(originalContents);
        contentsCopy.remove(HEADER_LINE);
        for (String fileData : contentsCopy.values()) {
            MapWrapper<String, Object> line = parseSingleCsvLine(fileData, colIndex);
            if(line.get("date") == null) {
                throw new IllegalArgumentException("Overriden csv parser must provide a Date key value");
            }
            addToSeriesIfValid(line, series);
        }

        return series;
    }

    private int findHeaderIndex(MapWrapper<Integer, String> contents) throws ItemNotFoundException {
        String fileHeader = contents.get(HEADER_LINE);
        ListWrapper<String> delimitedHeaders
                = new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));
        int colIndex = delimitedHeaders.indexOf(header);
        return colIndex;
    }

}
