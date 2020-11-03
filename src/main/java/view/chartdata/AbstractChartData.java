package view.chartdata;

import datatypes.exceptions.ItemNotFoundException;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import process.datatypes.ParsedData;
import process.datatypes.ParsedFile;

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
    public final AbstractChartData convertChartData(
            ParsedData data)
            throws IOException, NumberFormatException {

        MapWrapper<String, MapWrapper<Integer, String>> fileMap;
        if (data instanceof ParsedFile) {
            fileMap = data.getData();
        } else {
            return null;
        }

        ListWrapper<ChartSubDataWrapper> chartData = new ArrayListWrapper<>();
        for (MapWrapper.Entry<String, MapWrapper<Integer, String>> file : fileMap.entrySet()) {
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
    protected abstract MapWrapper<String, Object> parseSingleCsvLine(
            String csvLine) 
            throws ParseException, NumberFormatException;

    //Must create the correct sub data structure.
    protected abstract ChartSubDataWrapper ChartSubDataFactory(
            MapWrapper.Entry<String, MapWrapper<Integer, String>> file);

    protected final void assembleData(
            ListWrapper<ChartSubDataWrapper> chartData) {
        for (ChartSubDataWrapper series : chartData) {
            this.addSubDataToInternalCollection(series);
        }
    }

    protected final Date createDate(
            ListWrapper<String> delimitedData) 
            throws ParseException {
        String dateStr = delimitedData.get(X_INDEX);
        DateFormat df = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        Date parsedDate = df.parse(dateStr);
        return parsedDate;
    }

    private ChartSubDataWrapper buildSeries(
            MapWrapper.Entry<String, MapWrapper<Integer, String>> file)
            throws ItemNotFoundException, ParseException, NumberFormatException {
        ChartSubDataWrapper series = ChartSubDataFactory(file);
        return populateSeries(file, series);
    }

    private ChartSubDataWrapper populateSeries(
            MapWrapper.Entry<String, MapWrapper<Integer, String>> file,
            ChartSubDataWrapper series) 
            throws NumberFormatException, ItemNotFoundException, ParseException {

        MapWrapper<Integer, String> contents = 
                new HashMapWrapper(file.getValue());
        for (String fileData : contents.values()) {
            MapWrapper<String, Object> line = parseSingleCsvLine(fileData);
            if (line.get("date") == null) {
                final String exceptionMessage = 
                        "Overridden csv parser must provide a Date key value";
                throw new IllegalArgumentException(exceptionMessage);
            }
            //Date candidateDate = (Date) trialData.get("date");
            //if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            series.add(line);
            //}
        }

        return series;
    }

}
