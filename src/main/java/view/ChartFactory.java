package view;

import javautilwrappers.MapWrapper;
import process.DisplayTypeBinned;
import process.Visualizations;

public class ChartFactory {

    public static ChartWrapper createFrom(MapWrapper<String, Object> parsedArgs) {

        Visualizations visualization = (Visualizations) parsedArgs.get("type");
        switch (visualization) {
            case BASIC:
                return new TimeSeriesChart(parsedArgs);
            case MOVING_AVERAGE:
                TimeSeriesChart chart = new TimeSeriesChart(parsedArgs);
                chart.setPeriod((int) parsedArgs.get("period"));
                chart.setPeriod((int) parsedArgs.get("initToIgnore"));
                chart.setShowMovingAvg(true);
                return chart;
            case BINNED:
                DisplayTypeBinned displayType = (DisplayTypeBinned) parsedArgs.get("displayType");
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
