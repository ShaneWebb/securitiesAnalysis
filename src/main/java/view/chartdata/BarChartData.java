
package view.chartdata;

import java.io.IOException;
import java.text.ParseException;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.Dataset;

public class BarChartData extends AbstractBinnedData {

    BarChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public Dataset unwrap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addSubData(ChartSubDataWrapper data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected MapWrapper<String, Object> parseSingleCsvLine(String fileData, int colIndex) throws ParseException, NumberFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }


}
