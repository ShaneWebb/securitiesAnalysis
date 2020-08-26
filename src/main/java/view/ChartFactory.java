package view;

import javautilwrappers.MapWrapper;
import process.DisplayTypeBinned;
import process.Visualizations;

public abstract class ChartFactory {

    public static AbstractChart createFrom(MapWrapper<String, Object> parsedArgs) {

        AbstractChart chart = new AbstractChart();
        
        Visualizations visualization = (Visualizations) parsedArgs.get("type");
        //showLinearTrend = (boolean) parsedArgs.get("lineartrend");
        
        chart.setHeader((String) parsedArgs.get("header"));
        chart.setxAxis((String) parsedArgs.get("xAxis"));
        chart.setVisualization((Visualizations) parsedArgs.get("type"));
        
        switch (visualization) {
            case BASIC:
                break;
            case MOVING_AVERAGE:
                int period = (int) parsedArgs.get("period");
                chart.setPeriod(period);
                int initIgnore = (int) parsedArgs.get("initToIgnore");
                chart.setPeriod(initIgnore);
                break;
            case BINNED:
                DisplayTypeBinned displayType = (DisplayTypeBinned) parsedArgs.get("displayType");
                chart.setDisplayType(displayType);
                int bins = (int) parsedArgs.get("bins");
                chart.setBins(bins);
                break;
        }
        
        return chart;
        
//        Visualizations visualization = (Visualizations) parsedArgs.get("type");
//        switch (visualization) {
//            case BASIC:
//                return new TimeSeriesChart(parsedArgs);
//            case MOVING_AVERAGE:
//                return new TimeSeriesChart(parsedArgs);
//            case BINNED:
//                DisplayTypeBinned displayType = (DisplayTypeBinned) parsedArgs.get("displayType");
//                switch (displayType) {
//                    case BAR:
//                        return new BarChart(parsedArgs);
//                    case PIE:
//                        return new PieChart(parsedArgs);
//                }
//                break;
//        }
//        return null;
    }

}
