package view.chartdata;

import java.util.Date;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.category.DefaultCategoryDataset;

public abstract class AbstractBinnedData extends AbstractChartData {

    protected final int bins;
    protected DefaultCategoryDataset internalDataset;

    public AbstractBinnedData(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
        this.bins = (int) parsedArgs.get("bins");
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

