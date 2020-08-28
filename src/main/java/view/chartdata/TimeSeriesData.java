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
        
        MapWrapper<String, Object> seriesData = new HashMapWrapper<>();
        seriesData.put("date", parsedDate); //Important: Must provide this. 
        seriesData.put("day", new Day(parsedDate));
        seriesData.put("value", value);
        
        return seriesData;
    }

    @Override
    public Dataset unwrap() {
        return this.internalTimeSeriesCollection;
    }

    @Override
    public void addSubData(ChartSubDataWrapper data) {
        this.internalTimeSeriesCollection.addSeries((TimeSeries)data.unwrap());
    }

}
