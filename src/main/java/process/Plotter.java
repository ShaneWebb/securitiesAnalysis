
package process;

import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javautilwrappers.ArrayListWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.ListWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.data.time.TimeSeries;

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
        
        MapWrapper<String, TimeSeries> chartData = new HashMapWrapper<>();
        
        for(MapWrapper.Entry<String, MapWrapper<Integer,String>> file : parsedFiles.entrySet()) {
            
            try {
                TimeSeries series = new TimeSeries(file.getKey());
                chartData.put(file.getKey(), series);
                
                MapWrapper<Integer, String> contents = file.getValue();
                String fileHeader = contents.get(HEADER_LINE);
                
                ListWrapper<String> delimitedHeaders =
                        new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));
                
                int colIndex = delimitedHeaders.indexOf(header);
                
                for(MapWrapper.Entry<Integer, String> fileData: contents.entrySet()) {

                    
                    //series.add(item);
                }
                //System.out.println(entry.getValue());
            } catch (ItemNotFoundException ex) {
                throw new IOException(ex);
            }
        }

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
