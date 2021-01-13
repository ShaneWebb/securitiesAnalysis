package view.chartdata;

import io.datatypes.ParsedData;
import java.io.IOException;
import org.jfree.data.general.Dataset;

public interface ChartDataWrapper {

    void convert(ParsedData data) 
            throws IOException, NumberFormatException;
    
    Dataset unwrap();
    
    void addSubDataToInternalCollection(ChartSubDataWrapper data);
    
    
}
