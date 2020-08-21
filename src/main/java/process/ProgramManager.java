package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import io.console.ArgParseWrapper;
import javautilwrappers.BasicMap;
import main.Supplier;

public class ProgramManager {

    public static ProgramManager createFrom(Supplier<ProgramManager> factory) {
        ProgramManager temp = factory.get();
        return temp;
    }

    /**
     * TODO: Refactor to only be dependent on ArgumentParserWrapper. The startup
     * procedure should instead call a command, not processes directly.
     *
     */
    private final EnvironmentVariables environmentVariables;
    private final BasicMap<String, SupportedProcess> supportedProcesses;
    private final ArgParseWrapper argParser;
    private Report auditReport;

    private static boolean programIsActive = false;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        private final BasicMap<String, SupportedProcess> supportedProcesses;
        private final ArgParseWrapper argParser;

        public DefaultFactory() {            
            
            supportedProcesses = new BasicMap<>();
            argParser = new ArgParseWrapper("Erasmus");
            
            SupportedProcess plotter = new Plotter();
            SupportedProcess stopper = new Stopper();
            supportedProcesses.put("stopper", stopper);
            supportedProcesses.put("plotter", plotter);

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
            ArgParseWrapper argParser) {

        this.environmentVariables = environmentVariables;
        this.supportedProcesses = supportedProcessList;
        this.argParser = argParser;
        ProgramManager.programIsActive = true;
        
        argParser.addSubparsers("Sub command help");
        
        ArgParseWrapper stop = argParser.addParser("Stop", "Terminate Erasmus");
        stop.setDefault("func", supportedProcesses.get("stopper"));

        ArgParseWrapper plot = argParser.addParser("Visualize", "Visualize Data");
        plot.setDefault("func", supportedProcesses.get("plotter"));
        
//        ArgParseWrapper basic = plot.addParser("Basic", "As is plot");
//        
//        ArgParseWrapper movAvg = plot.addParser("MovingAvg", "Moving Average");
//        
//        ArgParseWrapper bin = plot.addParser("Bin", "Price binning");
        
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
