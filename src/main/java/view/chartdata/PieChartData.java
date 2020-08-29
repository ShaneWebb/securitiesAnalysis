
package view.chartdata;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartData extends AbstractBinnedData {

    private final DefaultPieDataset internalPieDataSet;
    
    PieChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.internalPieDataSet = new DefaultPieDataset();
    }

    @Override
    public Dataset unwrap() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addSubDataToInternalCollection(ChartSubDataWrapper data) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected MapWrapper<String, Object> parseSingleCsvLine(String fileData, int colIndex) throws ParseException, NumberFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void addToSeriesIfValid(MapWrapper<String, Object> trialData, ChartSubDataWrapper series) {
        Date candidateDate = (Date) trialData.get("date");
        if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            series.add(trialData);
        }
    }

}
