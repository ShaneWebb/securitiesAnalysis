
package view;

import java.io.IOException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartData extends AbstractBinnedData {

    private final DefaultPieDataset internalPieDataSet;
    
    PieChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.internalPieDataSet = new DefaultPieDataset();
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
