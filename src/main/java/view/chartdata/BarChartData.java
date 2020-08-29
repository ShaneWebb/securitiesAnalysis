package view.chartdata;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
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
        ListWrapper<MapWrapper<String, Object>> internalSubData
                = (ListWrapper<MapWrapper<String, Object>>) data.unwrap();

        RangeFinder finder = rangeFinderFactory(internalSubData);
        
        ListWrapper<MapWrapper<String, Object>> processedSubData = 
                new ArrayListWrapper<>();
        
        
        
        for(MapWrapper<String, Object> item: internalSubData) {
            finder.getRange(1);
        }
        
        for (MapWrapper<String, Object> item : processedSubData) {
            this.internalDataset.addValue(
                    (Double) item.get("value"),
                    (String) item.get("row"),
                    (String) item.get("col"));
        }

    }

    public RangeFinder rangeFinderFactory(ListWrapper<MapWrapper<String, Object>> internalSubData) {
        double max = Double.NEGATIVE_INFINITY, min = Double.POSITIVE_INFINITY;
        for(MapWrapper<String, Object> item: internalSubData) {
            double trialValue = (Double) item.get("value");
            if(trialValue > max) {
                max = trialValue;
            }
            if(trialValue < min) {
                min = trialValue;
            }
        }
        RangeFinder finder = new RangeFinder(min, max, bins);
        return finder;
    }

    @Override
    protected ChartSubDataWrapper ChartSubDataFactory(MapWrapper.Entry<String, MapWrapper<Integer, String>> file) {
        String fileName = file.getKey();
        return new BarChartSubData(fileName);
    }

    @Override
    protected MapWrapper<String, Object> parseSingleCsvLine(String csvLine, int colIndex) throws ParseException, NumberFormatException {
        ListWrapper<String> delimitedData = new ArrayListWrapper(Arrays.asList(csvLine.split(",")));
        double value = Double.valueOf(delimitedData.get(colIndex));
        Date parsedDate = createDate(delimitedData);

        MapWrapper<String, Object> trialSeriesData = new HashMapWrapper<>();
        trialSeriesData.put("date", parsedDate); //Important: Must provide this. 
        trialSeriesData.put("value", value);
        return trialSeriesData;
    }
    
}
