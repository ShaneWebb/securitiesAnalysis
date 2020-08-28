
package view.chartdata;

import javautilwrappers.MapWrapper;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;

public class TimeSeriesSubData implements ChartSubDataWrapper {

    private final TimeSeries internalSeries;
    
    public TimeSeriesSubData(TimeSeries internalSeries) {
        this.internalSeries = internalSeries;
    }
    
    public TimeSeriesSubData(String key) {
        this.internalSeries = new TimeSeries(key);
    }
    
    @Override
    public void add(MapWrapper data) {
        internalSeries.add((Day) data.get("day"), (double) data.get("value"));
    }
    
    @Override
    public TimeSeries unwrap() {
        return this.internalSeries;
    }
    
}
