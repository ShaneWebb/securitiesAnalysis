
package view.chartdata;

import java.text.ParseException;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

public class PieChartData extends AbstractBinnedData {
    
    public PieChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.internalDataset = new DefaultCategoryDataset();
    }
    
    public PieChartData(MapWrapper<String, Object> parsedArgs, DefaultCategoryDataset internalDataset) {
        super(parsedArgs);
        this.internalDataset = internalDataset;
    }

    @Override
    public Dataset unwrap() {
        return this.internalDataset;
    }

    @Override
    protected void postProcessData(MapWrapper<String, Object> item, String csvLine) throws ParseException, NumberFormatException {

    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        String fileName = file.getKey();
        return new PieChartSubData(fileName);
    }

}
