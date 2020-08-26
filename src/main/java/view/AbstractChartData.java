package view;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.MapWrapper;

public abstract class AbstractChartData implements ChartDataWrapper {

    
    protected String header, defaultFileDateFormat;
    protected Date startDate, endDate;
    protected static final int HEADER_LINE = 1;
    protected static final int X_INDEX = 0;
    protected static final String CLI_DATE_FORMAT = "MM/dd/yyyy";
    
    public AbstractChartData(MapWrapper<String, Object> parsedArgs) {
        this.defaultFileDateFormat = "yyyy-MM-dd";
        this.header = (String) parsedArgs.get("header");

        DateFormat df = new SimpleDateFormat(CLI_DATE_FORMAT, Locale.ENGLISH);
        try {
            String startDateStr = (String) parsedArgs.get("startDate");
            this.startDate = df.parse(startDateStr);
            String endDateStr = (String) parsedArgs.get("endDate");
            this.endDate = df.parse(endDateStr);
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

}
