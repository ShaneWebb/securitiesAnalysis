package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import process.Visualizations;

public abstract class AbstractBinnedChart extends AbstractChart {

    protected int bins;

    public AbstractBinnedChart(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(
                (String) parsedArgs.get(SupportedArgs.header),
                (Visualizations) parsedArgs.get(SupportedArgs.type));

        this.bins = (int) parsedArgs.get(SupportedArgs.bins);
    }

}
