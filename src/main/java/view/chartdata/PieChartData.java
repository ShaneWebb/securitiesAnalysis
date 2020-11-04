
package view.chartdata;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

public class PieChartData extends AbstractBinnedData {
    
    public PieChartData(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(parsedArgs);
        this.internalDataset = new DefaultCategoryDataset();
    }
    
    public PieChartData(MapWrapper<SupportedArgs, Object> parsedArgs, DefaultCategoryDataset internalDataset) {
        super(parsedArgs);
        this.internalDataset = internalDataset;
    }

    @Override
    public Dataset unwrap() {
        return this.internalDataset;
    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        String fileName = file.getKey();
        return new PieChartSubData(fileName);
    }

}
