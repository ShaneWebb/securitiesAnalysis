
package view.chartdata;

import java.util.Date;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class AbstractBinnedData extends AbstractChartData {
    protected int bins;
    protected DefaultCategoryDataset internalDataset;

    public AbstractBinnedData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.bins = (int) parsedArgs.get("bins");
    }

    @Override
    protected void addToSeriesIfValid(MapWrapper<String, Object> seriesData, ChartSubDataWrapper series) {
        Date candidateDate = (Date) seriesData.get("date");
        if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            series.add(seriesData);
        }
    }

}
