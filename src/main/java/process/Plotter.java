package process;

import view.ChartDataFactory;
import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.*;
import view.AbstractChartData;
import view.AbstractChart;
import view.ChartDataWrapper;
import view.ChartFactory;
import view.ChartWrapper;

public class Plotter implements SupportedProcess {

    private String files;
    private String defaultCliDateFormat;
    private boolean showLinearTrend;
    private Visualizations visualization;
    private final BasicFileReader reader;
    private final AbstractChart chart;
    private final AbstractChartData chartData;

    public Plotter(BasicFileReader reader, AbstractChart chart, AbstractChartData chartData) {
        this.defaultCliDateFormat = "MM/dd/yyyy";
        this.reader = reader;
        this.chart = chart;
        this.chartData = chartData;
    }

    public void setDefaultCliDateFormat(String defaultCliDateFormat) {
        this.defaultCliDateFormat = defaultCliDateFormat;
    }

    @Override
    public void execute() throws IOException {

        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles;
        parsedFiles = readFiles(files);
        chartData.convertChartData(parsedFiles);
        chart.generateVisual(chartData);
    }

    private MapWrapper<String, MapWrapper<Integer, String>> readFiles(String files) throws IOException {
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();
        for (String file : delimitedFiles) {
            parsedFiles.put(file, reader.read(file));
        }
        return parsedFiles;
    }

    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs)
            throws IllegalArgumentException {
        
        
//        ChartWrapper tempChart = ChartFactory.createFrom(parsedArgs);
//        ChartDataWrapper tempChartData = ChartDataFactory.createFrom(parsedArgs);
                
        visualization = (Visualizations) parsedArgs.get("type");
        files = (String) parsedArgs.get("files");
        showLinearTrend = (boolean) parsedArgs.get("lineartrend");
        
        chart.setHeader((String) parsedArgs.get("header"));
        chart.setxAxis((String) parsedArgs.get("xAxis"));
        chart.setVisualization((Visualizations) parsedArgs.get("type"));
        
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
        
    }

    @Override
    public boolean runsOnStart() {
        return false;
    }

//<editor-fold defaultstate="collapsed" desc="Unused">
    @Override
    public void setAuditReport(Report auditReport) {

    }

    @Override
    public void createThread() {

    }

    @Override
    public void stopAllThreads() {

    }
//</editor-fold>
}
