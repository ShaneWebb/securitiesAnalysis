
package view.chartdata;

import javautilwrappers.MapWrapper;

public abstract class AbstractBinnedData extends AbstractChartData {
    protected int bins;

    public AbstractBinnedData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.bins = (int) parsedArgs.get("bins");
    }

}
