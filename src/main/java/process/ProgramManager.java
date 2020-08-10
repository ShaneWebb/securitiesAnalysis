package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import io.console.ArgumentParserWrapper;
import main.Supplier;

public class ProgramManager {

    public static ProgramManager createFrom(Supplier<ProgramManager> defaultFactory) {
        return defaultFactory.get();
    }

    private final EnvironmentVariables environmentVariables;
    private final SupportedProcess[] supportedProcessList;
    private final ArgumentParserWrapper argParser;
    private Report auditReport;

    private boolean programIsActive;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        //TODO: Define default set of supported processes.
        SupportedProcess[] supportedProcessList = {};

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcessList,
                    new ArgumentParserWrapper("Erasmus", "Main program help"));
        }
    }

    public ProgramManager(
            EnvironmentVariables environmentVariables,
            SupportedProcess[] supportedProcessList,
            ArgumentParserWrapper argParser) {

        this.environmentVariables = environmentVariables;
        this.supportedProcessList = supportedProcessList;
        this.argParser = argParser;
        this.programIsActive = true;
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

    //What to do and how to do it.
    public void runUserInputCommand(String inputCommand) {
        //TODO: Implement readConsole to more appropriately set program state.
        programIsActive = false;

        try {
            String[] inputCommandParsed = inputCommand.split(" ");
            SupportedProcess preparedProcess
                    = argParser.getPreparedProcess(inputCommandParsed);
            preparedProcess.execute();
        } catch (IllegalArgumentException e) {
            //TODO: Log perhaps?
        }

    }

    public void stopAllProcesses() {
        for (SupportedProcess process : supportedProcessList) {
            process.stopAllThreads();
        }
    }

}
