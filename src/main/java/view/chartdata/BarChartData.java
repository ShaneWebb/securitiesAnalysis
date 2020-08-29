package view.chartdata;

import java.text.ParseException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.SortedSet;
import java.util.TreeSet;
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
