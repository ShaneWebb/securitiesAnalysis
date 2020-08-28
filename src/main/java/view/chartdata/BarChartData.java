
package view.chartdata;

import java.text.ParseException;
import java.util.Date;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.Dataset;

public class BarChartData extends AbstractBinnedData {

    public BarChartData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.internalDataset = new DefaultCategoryDataset();
    }
    
    public BarChartData(
            MapWrapper<String, Object> parsedArgs,
            DefaultCategoryDataset internalDataset) {
        super(parsedArgs);
        this.internalDataset = internalDataset;
    }

    @Override
    public Dataset unwrap() {
        return this.internalDataset;
    }

    @Override
    public void addSubDataToInternalCollection(ChartSubDataWrapper data) {
        ListWrapper<MapWrapper<String, Object>> internalSubData = 
                (ListWrapper<MapWrapper<String, Object>>) data.unwrap();

        for(MapWrapper<String, Object> item: internalSubData) {
            this.internalDataset.addValue(
                    (Double) item.get("value"),
                    (String) item.get("row"),
                    (String) item.get("col"));
        }

    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    @Override
    protected MapWrapper<String, Object> parseSingleCsvLine(String fileData, int colIndex) throws ParseException, NumberFormatException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override //Must check start and end date, along with 
    protected void addToSeriesIfValid(MapWrapper<String, Object> seriesData, ChartSubDataWrapper series) {
        Date candidateDate = (Date) seriesData.get("date");
        if (candidateDate.compareTo(startDate) >= 0 && candidateDate.compareTo(endDate) <= 0) {
            series.add(seriesData);
        }
    }


}
