package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.DisplayTypeBinned;
import process.Visualizations;

public class AbstractChart implements ChartWrapper {

    private String xAxis, header;
    private int period, initIgnore, bins;
    private Visualizations visualization;
    private DisplayTypeBinned displayType;

    public AbstractChart() {

    }

    public void setVisualization(Visualizations visualization) {
        this.visualization = visualization;
    }

    public void setxAxis(String xAxis) {
        this.xAxis = xAxis;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public void setPeriod(int period) {
        this.period = period;
    }

    public void setInitIgnore(int initIgnore) {
        this.initIgnore = initIgnore;
    }

    @Override
    public void generateVisual(AbstractChartData dataset) {
        switch (visualization) {
            case BASIC:
                break;
            case MOVING_AVERAGE:
                dataset.addMovingAverage(period, initIgnore);
                break;
            case BINNED:
                break;
        }

        TimeSeriesCollection data = dataset.getInternalTimeSeries();
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                visualization.toString(), // title
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

    public void setDisplayType(DisplayTypeBinned displayType) {
        this.displayType = displayType;
    }

    public void setBins(int bins) {
        this.bins = bins;
    }
}
