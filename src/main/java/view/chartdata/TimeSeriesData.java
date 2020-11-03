package view.chartdata;

import javautilwrappers.ArrayListWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.Dataset;
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
    public Dataset unwrap() {
        return this.internalTimeSeriesCollection;
    }

    @Override
    public void addSubDataToInternalCollection(ChartSubDataWrapper data) {
        this.internalTimeSeriesCollection.addSeries((TimeSeries)data.unwrap());
    }


}
