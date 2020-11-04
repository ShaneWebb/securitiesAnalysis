package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import process.DisplayTypeBinned;
import process.Visualizations;

public class ChartWrapperFactory {

    public static ChartWrapper createFrom(MapWrapper<SupportedArgs, Object> parsedArgs) {

        Visualizations visualization = (Visualizations) parsedArgs.get(SupportedArgs.type);
        switch (visualization) {
            case BASIC:
                return new TimeSeriesChart(parsedArgs);
            case MOVING_AVERAGE:
                return new MovingAverageChart(parsedArgs);
            case BINNED:
                DisplayTypeBinned displayType = (DisplayTypeBinned) parsedArgs.get(SupportedArgs.displayType);
                switch (displayType) {
                    case BAR:
                        return new BarChart(parsedArgs);
                    case PIE:
                        return new PieChart(parsedArgs);
                }
        }
        return null;
    }

}
