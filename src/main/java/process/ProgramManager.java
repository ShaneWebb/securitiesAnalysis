package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import main.Supplier;

public class ProgramManager {

    public static ProgramManager createFrom(Supplier<ProgramManager> defaultFactory) {
        return defaultFactory.get();
    }

    private final EnvironmentVariables environmentVariables;
    private final SupportedProcess[] supportedProcessList;
    private Report auditReport;
    private boolean programIsActive;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        //TODO: Define default set of supported processes.
        SupportedProcess[] supportedProcessList = {
            new SupportedProcess()
        };

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcessList);
        }
    }

    public ProgramManager(
            EnvironmentVariables environmentVariables,
            SupportedProcess[] supportedProcessList) {

        this.environmentVariables = environmentVariables;
        this.supportedProcessList = supportedProcessList;
    }

    public synchronized void setProgramActiveStatus(boolean programIsActive) {
        this.programIsActive = programIsActive;
    }

    public synchronized boolean getProgramActiveStatus() {
        return this.programIsActive;
    }

    public void setAuditReport(Report auditReport) {
        this.auditReport = auditReport;
    }

    public void startAllProcesses() {
        for (SupportedProcess process : supportedProcessList) {
            if (process.runsOnStart()) {
                process.setAuditReport(auditReport);
                process.createThread();
            }
        }
    }

    public Report getFullReport() {
        Report fullReport = new Report(FullReportFields.class);
        return fullReport;
    }

    public void acceptUserInput() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void stopAllProcesses() {
        for(SupportedProcess process: supportedProcessList){
            process.stopThreads();
        }
    }

}
