package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.Visualizations;
import view.chartdata.ChartDataWrapper;

public class TimeSeriesChart extends AbstractChart {

    private final String xAxis;
    private final boolean showLinearTrend;

    public TimeSeriesChart(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(
                (String) parsedArgs.get(SupportedArgs.header),
                (Visualizations) parsedArgs.get(SupportedArgs.type));
        
        this.showLinearTrend = (boolean) parsedArgs.get(SupportedArgs.lineartrend);
        this.xAxis = (String) parsedArgs.get(SupportedArgs.xAxis);
    }

    @Override
    public void generateVisual(ChartDataWrapper dataset) {
        TimeSeriesCollection data =  (TimeSeriesCollection) dataset.unwrap();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                visualization.toString(), // title
                xAxis, // x-axis label
                header, // y-axis label
                data, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        super.displayInFrame(chart);
    }

}
