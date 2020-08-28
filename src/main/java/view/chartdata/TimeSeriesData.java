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
        assembleData(seriesList);
    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        ChartSubDataWrapper series = new TimeSeriesSubData(file.getKey());
        return series;
    }

    @Override
    protected MapWrapper<String, Object> parseSingleCsvLine(String csvLine, int colIndex) throws ParseException, NumberFormatException {
        ListWrapper<String> delimitedData = new ArrayListWrapper(Arrays.asList(csvLine.split(",")));
        double value = Double.valueOf(delimitedData.get(colIndex));
        Date parsedDate = createDate(delimitedData);
        
        MapWrapper<String, Object> trialSeriesData = new HashMapWrapper<>();
        trialSeriesData.put("date", parsedDate); //Important: Must provide this. 
        trialSeriesData.put("day", new Day(parsedDate));
        trialSeriesData.put("value", value);
        
        return trialSeriesData;
    }

    @Override
    public Dataset unwrap() {
        return this.internalTimeSeriesCollection;
    }

    @Override
    public void addSubDataToInternalCollection(ChartSubDataWrapper data) {
        this.internalTimeSeriesCollection.addSeries((TimeSeries)data.unwrap());
    }

    @Override //Any data within the start and end will be plotted.
    protected void addToSeriesIfValid(MapWrapper<String, Object> seriesData, ChartSubDataWrapper series) {
        Date candidateDate = (Date) seriesData.get("date");
        if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            series.add(seriesData);
        }
    }

}
