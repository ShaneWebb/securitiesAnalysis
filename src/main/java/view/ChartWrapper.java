package view;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.TimeSeriesCollection;
import process.Visualizations;

public class ChartWrapper {

    private String xAxis, header;
    private Visualizations visualization;
    
    public ChartWrapper() {
        
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
    
    public void generateVisual(ChartDataWrapper dataset) {
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
}
