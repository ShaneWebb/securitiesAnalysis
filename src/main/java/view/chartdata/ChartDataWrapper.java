package view.chartdata;

import java.io.IOException;
import org.jfree.data.general.Dataset;
import process.datatypes.ParsedData;

public interface ChartDataWrapper {

    AbstractChartData convertChartData(ParsedData data) throws IOException, NumberFormatException;;
    
    Dataset unwrap();
    
    void addSubDataToInternalCollection(ChartSubDataWrapper data);
    
    
}
