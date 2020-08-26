package process;

import datatypes.Report;
import datatypes.EnvironmentVariables;
import io.console.ArgParseWrapper;
import io.local.BasicFileReader;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import main.Supplier;
import view.AbstractChartData;
import view.AbstractChart;

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
    private final MapWrapper<String, SupportedProcess> supportedProcesses;
    private final ArgParseWrapper argParser;
    private Report auditReport;

    private static boolean programIsActive = false;

    public static class DefaultFactory implements Supplier<ProgramManager> {

        private final MapWrapper<String, SupportedProcess> supportedProcesses;
        private final ArgParseWrapper argParser;

        public DefaultFactory() {

            supportedProcesses = new HashMapWrapper<>();
            argParser = new ArgParseWrapper("Erasmus");

            SupportedProcess plotter = new Plotter(new BasicFileReader(),
                    new AbstractChart(),
                    new AbstractChartData());
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
            MapWrapper<String, SupportedProcess> supportedProcessList,
            ArgParseWrapper argParser) {

        this.environmentVariables = environmentVariables;
        this.supportedProcesses = supportedProcessList;
        this.argParser = argParser;
        ProgramManager.programIsActive = true;

        argParser.addSubparserHelp("Sub command help");

        ArgParseWrapper stop = argParser.addParser("Stop", "Terminate Erasmus");
        stop.setDefault("func", supportedProcesses.get("stopper"));

        ArgParseWrapper plot = argParser.addParser("Visualize", "Visualize Data");
        plot.setDefault("func", supportedProcesses.get("plotter"));
        plot.addArgument("files")
                .help("Comma separated list of CSV files.");

        plot.addArgument("header")
                .help("Data to plot");

        plot.addArgument("startDate")
                .help("Start date to plot");

        plot.addArgument("endDate")
                .help("End date to plot");

        plot.addArgument("--xAxis")
                .help("Label for the x-axis")
                .setDefault("Date");

        plot.addArgument("--lineartrend")
                .help("Plot linear trendlines")
                .setDefault(false)
                .actionStoreTrue();

        ArgParseWrapper basic = plot.addParser("Basic", "As is plot");
        basic.setDefault("type", Visualizations.BASIC);

        ArgParseWrapper movAvg = plot.addParser("MovingAvg", "Moving Average");
        movAvg.setDefault("type", Visualizations.MOVING_AVERAGE);
        movAvg.addArgument("--period")
                .help("Number of time periods for the average.")
                .type(Integer.class)
                .setDefault(1);
        
        movAvg.addArgument("--initToIgnore")
                .help("Number of initial time periods to ignore.")
                .type(Integer.class)
                .setDefault(1);

        ArgParseWrapper bin = plot.addParser("Bin", "Price binning");
        bin.setDefault("type", Visualizations.BINNED);
        bin.addArgument("displayType")
                .help("Way to display the binned data.")
                .type(DisplayTypeBinned.class);
        bin.addArgument("bins")
                .help("Number of bins to split the data into.")
                .type(Integer.class);

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
            HashMapWrapper<String, Object> parsedArgs
                    = argParser.parseArgs(inputCommandParsed);
            SupportedProcess process
                    = (SupportedProcess) parsedArgs.get("func");
            process.setArgs(parsedArgs);
            process.execute();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void stopAllProcesses() {
        for (SupportedProcess process : supportedProcesses.values()) {
            process.stopAllThreads();
        }
    }

}
