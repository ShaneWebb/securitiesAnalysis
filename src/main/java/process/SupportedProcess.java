package process;

import datatypes.Report;
import io.console.ArgumentParserWrapper;
import javautilwrappers.BasicMap;

public interface SupportedProcess {
  
    public boolean runsOnStart();

    public void setAuditReport(Report auditReport);

    public void createThread();

    public void stopAllThreads();

    public void execute();

    public void setArgs(BasicMap<String, Object> parsedArgs);
    
}
