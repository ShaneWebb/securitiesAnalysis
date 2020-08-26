package view;

import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.Visualizations;

public class TimeSeriesChart extends AbstractChart {

    private String xAxis;
    private int initIgnore, period;
    private boolean showMovingAvg, showLinearTrend;

    public TimeSeriesChart(MapWrapper<String, Object> parsedArgs) {
        super(
                (String) parsedArgs.get("header"),
                (Visualizations) parsedArgs.get("type"));

        this.showMovingAvg = false;
        this.showLinearTrend = (boolean) parsedArgs.get("lineartrend");
        this.xAxis = (String) parsedArgs.get("xAxis");

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
        TimeSeriesCollection data = ((TimeSeriesData) dataset).getInternalTimeSeries();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(visualization.toString(), // title
                xAxis, // x-axis label
                header, // y-axis label
                data, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        ChartFrame frame = new ChartFrame(visualization.toString(), chart);
        frame.pack();
        frame.setVisible(true);
    }
}
