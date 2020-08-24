
package process;

import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.CollectionWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import main.Helper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Plotter implements SupportedProcess {
    
    private String files;
    private String header, startDate, endDate;
    private boolean showLinearTrend;
    private Visualizations visualization;
    private final BasicFileReader reader;
    private static final int HEADER_LINE = 1;

    public Plotter(BasicFileReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute() throws IOException {
        
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer,String>> parsedFiles 
                = new HashMapWrapper<>();
        
        for(String file:delimitedFiles) {
            parsedFiles.put(file, reader.read(file));
        }
        
        ListWrapper<TimeSeries> chartData = new ArrayListWrapper<>();
        
        for(MapWrapper.Entry<String, MapWrapper<Integer,String>> file : parsedFiles.entrySet()) {
            
            try {
                TimeSeries series = new TimeSeries(file.getKey());
                chartData.add(series);
                
                MapWrapper<Integer, String> contents = file.getValue();
                String fileHeader = contents.get(HEADER_LINE);
                
                ListWrapper<String> delimitedHeaders =
                        new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));
                
                int colIndex = delimitedHeaders.indexOf(header);
                contents.remove(HEADER_LINE);
                
                int tempyear = 2000;
                for(String fileData: contents.values()) {
                    ListWrapper<String> delimitedData = 
                            new ArrayListWrapper(Arrays.asList(fileData.split(",")));
                    
                    double value = Double.valueOf(delimitedData.get(colIndex));
                    series.add(new Month(1, tempyear++), value);
                }
                
                //System.out.println(entry.getValue());
            } catch (ItemNotFoundException ex) {
                throw new IOException(ex);
            }
        }
        
        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for(TimeSeries series: chartData) {
            dataset.addSeries(series);
        }
        
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Legal & General Unit Trust Prices", // title
                "Date", // x-axis label
                "Price Per Unit", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        
        ChartFrame frame = new ChartFrame("Test", chart);
        frame.pack();
        frame.setVisible(true);
        
        Helper.pause(5);

        switch (visualization) {
            case BASIC:
                break;
        }
        
    }

    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs) {
        files = (String) parsedArgs.get("files");
        header = (String) parsedArgs.get("header");
        startDate = (String) parsedArgs.get("startDate");
        endDate = (String) parsedArgs.get("endDate");
        showLinearTrend = (boolean) parsedArgs.get("lineartrend");
        visualization = (Visualizations) parsedArgs.get("type");
    }

//<editor-fold defaultstate="collapsed" desc="Unused">
    @Override
    public boolean runsOnStart() {
        return false;
    }
    
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
