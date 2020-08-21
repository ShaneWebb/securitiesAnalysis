package process;

import datatypes.Report;
import io.console.ArgParseWrapper;
import javautilwrappers.BasicHashMap;

public interface SupportedProcess {
  
    public boolean runsOnStart();

    public void setAuditReport(Report auditReport);

    public void createThread();

    public void stopAllThreads();

    public void execute();

    public void setArgs(BasicHashMap<String, Object> parsedArgs);
    
}
