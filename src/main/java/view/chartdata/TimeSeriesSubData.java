
package view.chartdata;

import java.util.Date;
import java.util.Iterator;
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
        internalSeries.add(new Day((Date) data.get("date")), (double) data.get("value"));
    }
    
    @Override
    public TimeSeries unwrap() {
        return this.internalSeries;
    }

    @Override
    public Iterator iterator() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
