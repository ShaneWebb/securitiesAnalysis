package process;

import datatypes.Report;
import io.console.ArgumentParserWrapper;

public interface SupportedProcess {
  
    public boolean runsOnStart();

    public void setAuditReport(Report auditReport);

    public void createThread();

    public void stopAllThreads();

    public void execute();
    
}
