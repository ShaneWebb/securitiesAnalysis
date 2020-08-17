package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import io.console.ArgumentParserWrapper;
import io.console.SubparserWrapper;
import javautilwrappers.BasicMap;
import main.Supplier;

public class ProgramManager {

    public static ProgramManager createFrom(Supplier<ProgramManager> defaultFactory) {
        return defaultFactory.get();
    }

    /**
     * TODO: Refactor to only be dependent on ArgumentParserWrapper. The startup
     * procedure should instead call a command, not processes directly.
     *
     */
    private final EnvironmentVariables environmentVariables;
    private final BasicMap<String, SupportedProcess> supportedProcesses;
    private final ArgumentParserWrapper argParser;
    private Report auditReport;

    private static boolean programIsActive = false;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        private final BasicMap<String, SupportedProcess> supportedProcesses;
        private final ArgumentParserWrapper argParser;

        public DefaultFactory() {
            SupportedProcess plotter = new Plotter();
            SupportedProcess stopper = new Stopper();
            
            supportedProcesses = new BasicMap<>();
            supportedProcesses.put("stopper", stopper);
            supportedProcesses.put("plotter", plotter);

            argParser = new ArgumentParserWrapper("Erasmus", "Main program help");
            
            SubparserWrapper stop = argParser.addParser("Stop", "Terminate Erasmus");
            stop.setDefault("func", stopper);

            SubparserWrapper plot = argParser.addParser("Plot", "Graph Data");
            plot.setDefault("func", plotter);
        }

        @Override
        public ProgramManager get() {
            return new ProgramManager(
                    EnvironmentVariables.INSTANCE,
                    supportedProcesses,
                    argParser
            );
        }
    }

    public ProgramManager(
            EnvironmentVariables environmentVariables,
            BasicMap<String, SupportedProcess> supportedProcessList,
            ArgumentParserWrapper argParser) {

        this.environmentVariables = environmentVariables;
        this.supportedProcesses = supportedProcessList;
        this.argParser = argParser;
        ProgramManager.programIsActive = true;
    }

    public static synchronized void setProgramActiveStatus(boolean programIsActive) {
        ProgramManager.programIsActive = programIsActive;
    }

    public static synchronized boolean getProgramActiveStatus() {
        return ProgramManager.programIsActive;
    }

    public void setAuditReport(Report auditReport) {
        this.auditReport = auditReport;
    }

    public void startAllProcesses() {
        for (SupportedProcess process : supportedProcesses.values()) {
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
        try {
            String[] inputCommandParsed = inputCommand.split(" ");
            BasicMap<String, Object> parsedArgs
                    = argParser.parseArgs(inputCommandParsed);
            SupportedProcess process
                    = (SupportedProcess) parsedArgs.get("func");
            process.setArgs(parsedArgs);
            process.execute();
        } catch (IllegalArgumentException | NullPointerException e) {
            //Does not need to do anything for now.
        }

    }

    public void stopAllProcesses() {
        for (SupportedProcess process : supportedProcesses.values()) {
            process.stopAllThreads();
        }
    }

}
