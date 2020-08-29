package view.chartdata;

import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class AbstractBinnedData extends AbstractChartData {

    protected final int bins;
    protected DefaultCategoryDataset internalDataset;
    protected boolean stochastic;

    public AbstractBinnedData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.bins = (int) parsedArgs.get("bins");
        this.stochastic = (boolean) parsedArgs.get("stochastic");
    }
    
    @Override
    public void addSubDataToInternalCollection(ChartSubDataWrapper data) {
        ListWrapper<MapWrapper<String, Object>> internalSubData
                = (ListWrapper<MapWrapper<String, Object>>) data.unwrap();

        RangeFinder finder = rangeFinderFactory(internalSubData);
        MapWrapper<String, Integer> rangesMap = new HashMapWrapper<>();
        for (MapWrapper<String, Object> item : internalSubData) {
            String range = finder.getRange((int) ((double) item.get("value")));
            if (range == null) {
                //The mathematical calculation will very rarely fail to bin
                //a value due to rounding error.
                continue;
            }
            Integer count = rangesMap.get(range);
            if (count == null) {
                rangesMap.put(range, 1);
            } else {
                rangesMap.put(range, ++count);
            }
        }
        
        Comparator<String> comparator = (String o1, String o2) -> {
            String[] o1delimited = o1.split(" ");
            String[] o2delimited = o2.split(" ");
            Integer i1 = Integer.parseInt(o1delimited[0]);
            Integer i2 = Integer.parseInt(o2delimited[0]);
            return i1.compareTo(i2);
        };
        
        SortedSet<String> keys = new TreeSet<>(comparator);
        keys.addAll(rangesMap.unwrap().keySet());

        ListWrapper<MapWrapper<String, Object>> processedSubData
                = new ArrayListWrapper<>();

        String fileName = ((AbstractBinnedSubData) data).getFileName();

        for (String key: keys) {
            MapWrapper<String, Object> tempMap = new HashMapWrapper<>();
            tempMap.put("row", key);
            tempMap.put("col", fileName);
            tempMap.put("value", rangesMap.get(key));
            processedSubData.add(tempMap);
        }
        
        for (MapWrapper<String, Object> item : processedSubData) {
            this.internalDataset.addValue(
                    (Integer) item.get("value"),
                    (String) item.get("row"),
                    (String) item.get("col"));
        }
    }
    
    public RangeFinder rangeFinderFactory(ListWrapper<MapWrapper<String, Object>> internalSubData) {
        double max = Double.NEGATIVE_INFINITY, min = Double.POSITIVE_INFINITY;
        for (MapWrapper<String, Object> item : internalSubData) {
            double trialValue = (Double) item.get("value");
            if (trialValue > max) {
                max = trialValue;
            }
            if (trialValue < min) {
                min = trialValue;
            }
        }
        RangeFinder finder = new RangeFinder(min, max, bins);
        return finder;
    }

    public static class RangeFinder {

        private final int lower, upper, interval, bins;
        private final NavigableMap<Integer, MapWrapper<String, Object>> lookupMap;

        public RangeFinder(double lower, double upper, int bins) {
            this.lookupMap = new TreeMap<>();
            this.interval = (int) (upper - lower) / bins;
            this.lower = (int) lower;
            this.upper = (int) upper;
            this.bins = bins;

            for (int i = 0; i < bins; ++i) {
                int tempLower = this.lower + (i) * interval;
                int tempUpper = tempLower + interval;
                String key = Integer.toString(tempLower)
                        + " - "
                        + Integer.toString(tempUpper);

                MapWrapper<String, Object> tempMap = new HashMapWrapper<>();
                tempMap.put("upper", tempUpper);
                tempMap.put("key", key);
                lookupMap.put(tempLower, tempMap);
            }
        }

        public String getRange(int val) {
            Map.Entry<Integer, MapWrapper<String, Object>> entry = lookupMap.floorEntry(val);
            if (entry == null) {
                return null;
            } else if (val <= (int) entry.getValue().get("upper")) {
                return (String) entry.getValue().get("key");
            } else {
                return null;
            }
        }

    }

}

