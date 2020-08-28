package view.chartdata;

import view.chartdata.ChartSubDataWrapper;
import view.chartdata.AbstractChartData;
import java.io.IOException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.Dataset;

public interface ChartDataWrapper {

    AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException;

    Dataset unwrap();
    
    void addSubData(ChartSubDataWrapper data);
    
    
}
