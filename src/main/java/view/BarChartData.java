
package view;

import java.io.IOException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;

public class BarChartData extends AbstractBinnedData {

    BarChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public AbstractDataset unwrap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
