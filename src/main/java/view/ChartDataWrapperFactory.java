package view;

import javautilwrappers.MapWrapper;
import process.DisplayTypeBinned;
import process.Visualizations;

public class ChartDataWrapperFactory {
    
    public static ChartDataWrapper createFrom(MapWrapper<String, Object> parsedArgs) {
        
        Visualizations visualization = (Visualizations) parsedArgs.get("type");
        switch (visualization) {
            case BASIC:
                return new TimeSeriesData(parsedArgs);
            case MOVING_AVERAGE:
                return new TimeSeriesData(parsedArgs);
            case BINNED:
                DisplayTypeBinned displayType = (DisplayTypeBinned) parsedArgs.get("displayType");
                switch (displayType) {
                    case BAR:
                        return new BarChartData(parsedArgs);
                    case PIE:
                        return new PieChartData(parsedArgs);
                }
                break;
        }
        return null;
    }

}
