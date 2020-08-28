package view.chart;

import javautilwrappers.MapWrapper;
import process.Visualizations;

public abstract class AbstractBinnedChart extends AbstractChart {

    protected int bins;

    public AbstractBinnedChart(MapWrapper<String, Object> parsedArgs) {
        super(
                (String) parsedArgs.get("header"),
                (Visualizations) parsedArgs.get("type"));

        this.bins = (int) parsedArgs.get("bins");
    }

}
