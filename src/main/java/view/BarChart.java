
package view;

import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;

public class BarChart extends AbstractBinnedChart {

    BarChart(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public void generateVisual(AbstractChartData dataset) {
        
    }

}
