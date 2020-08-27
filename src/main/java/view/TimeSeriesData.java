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
import org.jfree.data.general.AbstractDataset;
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

        ListWrapper<TimeSeries> seriesList = new ArrayListWrapper<>();
        for (Object series : internalTimeSeriesCollection.getSeries()) {
            TimeSeries existingSeries = (TimeSeries) series;
            TimeSeries newSeries = MovingAverage.createMovingAverage(
                    existingSeries,
                    existingSeries.getKey().toString() + "MAvg",
                    period,
                    initIgnore);
            seriesList.add(newSeries);
        }

        for (TimeSeries series : seriesList) {
            internalTimeSeriesCollection.addSeries(series);
        }

    }

    @Override
    public AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException {

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

        for (TimeSeries series : chartData) {
            this.internalTimeSeriesCollection.addSeries(series);
        }

        return this;
    }

    @Override
    public AbstractDataset unwrap() {
        return this.internalTimeSeriesCollection;
    }

}
