package view;

import process.Visualizations;


public abstract class AbstractChart implements ChartWrapper {

    protected String header;
    protected Visualizations visualization;
    
    public AbstractChart(String header, Visualizations visualization) {
        this.header = header;
        this.visualization = visualization;
    }

}
