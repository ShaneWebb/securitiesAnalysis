package process;

import view.ChartDataFactory;
import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import javautilwrappers.*;
import view.ChartDataWrapper;
import view.ChartFactory;
import view.ChartWrapper;

public class Plotter implements SupportedProcess {

    private String files;
    private final BasicFileReader reader;
    private ChartWrapper chart;
    private ChartDataWrapper chartData;

    public Plotter(BasicFileReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute() throws IOException {

        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles;
        parsedFiles = readFiles(files);
        chartData.convertChartData(parsedFiles);
        chart.generateVisual(chartData);
    }

    private MapWrapper<String, MapWrapper<Integer, String>> readFiles(String files) throws IOException {
        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();
        for (String file : delimitedFiles) {
            parsedFiles.put(file, reader.read(file));
        }
        return parsedFiles;
    }

    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs)
            throws IllegalArgumentException {

        files = (String) parsedArgs.get("files");
        chart = ChartFactory.createFrom(parsedArgs);
        chartData = ChartDataFactory.createFrom(parsedArgs);

    }

    @Override
    public boolean runsOnStart() {
        return false;
    }

//<editor-fold defaultstate="collapsed" desc="Unused">
    @Override
    public void setAuditReport(Report auditReport) {

    }

    @Override
    public void createThread() {

    }

    @Override
    public void stopAllThreads() {

    }
//</editor-fold>
}
