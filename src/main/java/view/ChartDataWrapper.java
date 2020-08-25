package view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class ChartDataWrapper {

    private TimeSeriesCollection internalTimeSeries;
    private static final int HEADER_LINE = 1;
    private static final int X_INDEX = 0;
    private String header, defaultFileDateFormat;
    private Date startDate, endDate;
    
    public ChartDataWrapper() {
        this.defaultFileDateFormat = "yyyy-MM-dd";
    }

    public TimeSeriesCollection getInternalTimeSeries() {
        return internalTimeSeries;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public void setDefaultFileDateFormat(String defaultFileDateFormat) {
        this.defaultFileDateFormat = defaultFileDateFormat;
    }
    
    public ChartDataWrapper convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) 
            throws IOException, NumberFormatException {
        ListWrapper<TimeSeries> chartData = new ArrayListWrapper<>();
        for (MapWrapper.Entry<String, MapWrapper<Integer, String>> file : parsedFiles.entrySet()) {

            try {
                TimeSeries series = new TimeSeries(file.getKey());
                chartData.add(series);

                MapWrapper<Integer, String> contents = file.getValue();
                String fileHeader = contents.get(HEADER_LINE);

                ListWrapper<String> delimitedHeaders
                        = new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));

                int colIndex = delimitedHeaders.indexOf(header);
                contents.remove(HEADER_LINE);

                for (String fileData : contents.values()) {
                    ListWrapper<String> delimitedData
                            = new ArrayListWrapper(Arrays.asList(fileData.split(",")));

                    double value = Double.valueOf(delimitedData.get(colIndex));
                    String dateStr = delimitedData.get(X_INDEX);

                    DateFormat df = new SimpleDateFormat(defaultFileDateFormat, Locale.ENGLISH);
                    Date parsedDate = df.parse(dateStr);

                    if (parsedDate.compareTo(startDate) >= 0
                            && parsedDate.compareTo(endDate) <= 0) {
                        series.add(new Day(parsedDate), value);
                    } else {
                        continue;
                    }
                }

                //System.out.println(entry.getValue());
            } catch (ItemNotFoundException | ParseException ex) {
                throw new IOException(ex);
            }
        }
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries series : chartData) {
            dataset.addSeries(series);
        }
        this.internalTimeSeries = dataset;
        return this;
    }
    
    
}
