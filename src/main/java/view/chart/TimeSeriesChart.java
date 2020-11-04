package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.Visualizations;
import view.chartdata.ChartDataWrapper;
import view.chartdata.TimeSeriesData;

public class TimeSeriesChart extends AbstractChart {

    private String xAxis;
    private int initIgnore, period;
    private boolean showMovingAvg, showLinearTrend;

    public TimeSeriesChart(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(
                (String) parsedArgs.get(SupportedArgs.header),
                (Visualizations) parsedArgs.get(SupportedArgs.type));

        this.showMovingAvg = false;
        this.showLinearTrend = (boolean) parsedArgs.get(SupportedArgs.lineartrend);
        this.xAxis = (String) parsedArgs.get(SupportedArgs.xAxis);

    }

    public void setInitIgnore(int initIgnore) {
        this.initIgnore = initIgnore;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setShowMovingAvg(boolean showMovingAvg) {
        this.showMovingAvg = showMovingAvg;
    }

    public void setShowLinearTrend(boolean showLinearTrend) {
        this.showLinearTrend = showLinearTrend;
    }

    @Override
    public void generateVisual(ChartDataWrapper dataset) {
        if (showMovingAvg) {
            ((TimeSeriesData) dataset).addMovingAverage(period, initIgnore);
        }
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
