
package process;

import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public class Plotter implements SupportedProcess {
    
    private String files;
    private String startDate, endDate;
    private boolean showLinearTrend;
    private Visualizations visualization;
    private final BasicFileReader reader;

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
        
        for(MapWrapper.Entry<String, MapWrapper<Integer,String>> entry : parsedFiles.entrySet()) {
            System.out.println(entry.getValue());
        }

        switch (visualization) {
            case BASIC:
                break;
        }
        
    }

    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs) {
        files = (String) parsedArgs.get("files");
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
