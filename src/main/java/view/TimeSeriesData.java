package view;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.MovingAverage;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class TimeSeriesData extends AbstractChartData {

    private final TimeSeriesCollection internalTimeSeriesCollection;

    public TimeSeriesData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.internalTimeSeriesCollection = new TimeSeriesCollection();
    }

    public void addMovingAverage(int period, int initIgnore) {
        if (internalTimeSeriesCollection.getSeriesCount() == 0) {
            return;
        }

        ListWrapper<ChartSubDataWrapper> seriesList = new ArrayListWrapper<>();
        for (Object series : internalTimeSeriesCollection.getSeries()) {
            TimeSeries existingSeries = (TimeSeries) series;
            TimeSeries newSeries = MovingAverage.createMovingAverage(
                    existingSeries,
                    existingSeries.getKey().toString() + "MAvg",
                    period,
                    initIgnore);
            seriesList.add(new TimeSeriesSubData(newSeries));
        }
        assembleSeries(seriesList);
    }

    @Override
    public AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException {

        ListWrapper<ChartSubDataWrapper> chartData = new ArrayListWrapper<>();
        for (MapWrapper.Entry<String, MapWrapper<Integer, String>> file : parsedFiles.entrySet()) {
            try {
                ChartSubDataWrapper series = buildSeries(file);
                chartData.add(series);
            } catch (ItemNotFoundException | ParseException ex) {
                throw new IOException(ex);
            }
        }
        assembleSeries(chartData);
        return this;
    }

    private ChartSubDataWrapper buildSeries(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) throws ItemNotFoundException, ParseException, NumberFormatException {
        
        TimeSeries series = new TimeSeries(file.getKey());
        MapWrapper<Integer, String> originalContents = new HashMapWrapper(file.getValue());
        int colIndex = findHeaderIndex(originalContents);
        
        MapWrapper<Integer, String> contentsCopy = new HashMapWrapper(originalContents);
        contentsCopy.remove(HEADER_LINE);
        for (String fileData : contentsCopy.values()) {
            MapWrapper<String, Object> seriesData = buildCandidateData(fileData, colIndex);
            addToSeriesIfValid(seriesData, series);
        }
        
        return new TimeSeriesSubData(series);
    }

    private MapWrapper<String, Object> buildCandidateData(String fileData, int colIndex) throws ParseException, NumberFormatException {
        ListWrapper<String> delimitedData = new ArrayListWrapper(Arrays.asList(fileData.split(",")));
        double value = Double.valueOf(delimitedData.get(colIndex));
        Date parsedDate = createDate(delimitedData);
        
        MapWrapper<String, Object> seriesData1 = new HashMapWrapper<>();
        seriesData1.put("date", parsedDate);
        seriesData1.put("day", new Day(parsedDate));
        seriesData1.put("value", value);
        
        return seriesData1;
    }

    private void addToSeriesIfValid(MapWrapper<String, Object> seriesData, TimeSeries series) {
        Date candidateDate = (Date) seriesData.get("date");
        if (candidateDate.compareTo(startDate) >= 0
                && candidateDate.compareTo(endDate) <= 0) {
            series.add((Day) seriesData.get("day"), (double) seriesData.get("value"));
        }
    }


    private int findHeaderIndex(MapWrapper<Integer, String> contents) throws ItemNotFoundException {
        String fileHeader = contents.get(HEADER_LINE);
        ListWrapper<String> delimitedHeaders
                = new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));
        int colIndex = delimitedHeaders.indexOf(header);
        return colIndex;
    }

    private Date createDate(ListWrapper<String> delimitedData) throws ParseException {
        String dateStr = delimitedData.get(X_INDEX);
        DateFormat df = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        Date parsedDate = df.parse(dateStr);
        return parsedDate;
    }

    private void assembleSeries(ListWrapper<ChartSubDataWrapper> chartData) {
        for (ChartSubDataWrapper series : chartData) {
            this.internalTimeSeriesCollection.addSeries((TimeSeries)series.unwrap());
        }
    }

    @Override
    public Dataset unwrap() {
        return this.internalTimeSeriesCollection;
    }

}
