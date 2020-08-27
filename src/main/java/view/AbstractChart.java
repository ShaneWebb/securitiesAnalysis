package view;

import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import process.Visualizations;


public abstract class AbstractChart implements ChartWrapper {

    protected String header;
    protected Visualizations visualization;
    
    public AbstractChart(String header, Visualizations visualization) {
        this.header = header;
        this.visualization = visualization;
    }
    
    protected void displayInFrame(JFreeChart chart) {
        ChartFrame frame = new ChartFrame(visualization.toString(), chart);
        frame.pack();
        frame.setVisible(true);
    }

}
