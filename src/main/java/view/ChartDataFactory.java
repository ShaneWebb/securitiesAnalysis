package view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.MapWrapper;
import process.DisplayTypeBinned;
import process.Visualizations;

public class ChartDataFactory {
    
    private static final String defaultCliDateFormat = "MM/dd/yyyy";

    public static AbstractChartData createFrom(MapWrapper<String, Object> parsedArgs) {
        
        AbstractChartData chartData = new AbstractChartData();
        chartData.setHeader((String) parsedArgs.get("header"));
        DateFormat df = new SimpleDateFormat(defaultCliDateFormat, Locale.ENGLISH);
        try {
            String startDateStr = (String) parsedArgs.get("startDate");
            Date startDate = df.parse(startDateStr);
            chartData.setStartDate(startDate);
            
            String endDateStr = (String) parsedArgs.get("endDate");
            Date endDate = df.parse(endDateStr);
            chartData.setEndDate(endDate);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
        
        return chartData;
        
    }

}
