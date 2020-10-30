package process;

import io.console.*;
import io.local.BasicFileReader;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;

public class ProgramManager {

    private final MapWrapper<String, SupportedProcess> supportedProcesses;
    private final ArgumentParserWrapper argParser;

    private static boolean programIsActive = true;

    public ProgramManager() {
        SupportedProcess plotter = new Plotter(new BasicFileReader());
        SupportedProcess stopper = new Stopper();
        
        supportedProcesses = new HashMapWrapper<>();
        supportedProcesses.put("stopper", stopper);
        supportedProcesses.put("plotter", plotter);

        argParser = new ArgumentParserWrapper("Erasmus");
        argParser.addSubparserHelp("Sub command help");
        buildArgParser();
    }

    public ProgramManager(MapWrapper<String, SupportedProcess> supportedProcesses) {
        this.supportedProcesses = supportedProcesses;
        argParser = new ArgumentParserWrapper("Erasmus");
        argParser.addSubparserHelp("Sub command help");
        buildArgParser();
    }
    
    public ProgramManager(
            MapWrapper<String, SupportedProcess> supportedProcesses,
            ArgumentParserWrapper argParser) {
        this.supportedProcesses = supportedProcesses;
        this.argParser = argParser;
    }
    
    private void buildArgParser() {

        ArgumentParserWrapper stop = argParser.addSubparser("Stop");
        stop.setDefault("func", supportedProcesses.get("stopper"));

        ArgumentParserWrapper plot = argParser.addSubparser("Visualize");
        plot.setDefault("func", supportedProcesses.get("plotter"));
        
        MutuallyExclusiveGroupWrapper dataSource = plot.addMutuallyExclusiveGroup();
        dataSource.addArgument("--files")
                .type(String.class)
                .help("Comma separated list of CSV files.");
        
        dataSource.addArgument("--DB")
                .type(String.class)
                .help("Read from a database. Specify ticker(s)");
        
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

        plot.addArgument("--stochastic")
                .help("Make a prediction")
                .setDefault(false)
                .actionStoreTrue();

        ArgumentParserWrapper basic = plot.addSubparser("Basic");
        basic.setDefault("type", Visualizations.BASIC);

        ArgumentParserWrapper movAvg = plot.addSubparser("MovingAvg");
        movAvg.setDefault("type", Visualizations.MOVING_AVERAGE);
        movAvg.addArgument("--period")
                .help("Number of time periods for the average.")
                .type(Integer.class)
                .setDefault(1);

        movAvg.addArgument("--initToIgnore")
                .help("Number of initial time periods to ignore.")
                .type(Integer.class)
                .setDefault(1);

        ArgumentParserWrapper bin = plot.addSubparser("Bin");
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

    //What to do and how to do it.
    public void runUserInputCommand(String inputCommand) {
        try {
            String[] inputCommandParsed = inputCommand.split(" ");
            MapWrapper<String, Object> parsedArgs
                    = argParser.parseArgs(inputCommandParsed);
            SupportedProcess process
                    = (SupportedProcess) parsedArgs.get("func");
            process.execute(parsedArgs);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

}
