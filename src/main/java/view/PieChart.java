package view;

import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.Visualizations;

public class PieChart extends AbstractBinnedChart {

    public PieChart(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public void generateVisual(AbstractChartData dataset) {

    }

}
