package process;

import datatypes.Report;
import javautilwrappers.MapWrapper;

//TODO: Refactor into multiple interfaces, since not all processes need
//all of these features.
public interface SupportedProcess {
  
    public boolean runsOnStart();

    public void setAuditReport(Report auditReport);

    public void createThread();

    public void stopAllThreads();

    public void execute() throws Exception;

    public void setArgs(MapWrapper<String, Object> parsedArgs);
    
}
