package process;

import datatypes.Report;
import io.local.BasicFileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;
import javautilwrappers.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class Plotter implements SupportedProcess {

    private String files, header, defaultCliDateFormat, defaultFileDateFormat;
    private Date startDate, endDate;
    private boolean showLinearTrend;
    private Visualizations visualization;
    private final BasicFileReader reader;

    private static final int HEADER_LINE = 1;
    private static final int X_INDEX = 0;

    public Plotter(BasicFileReader reader) {
        this.defaultFileDateFormat = "yyyy-MM-dd";
        this.defaultCliDateFormat = "MM/dd/yyyy";
        this.reader = reader;

    }

    @Override
    public void execute() throws IOException {

        String[] delimitedFiles = files.split(",");
        MapWrapper<String, MapWrapper<Integer, String>> parsedFiles
                = new HashMapWrapper<>();

        for (String file : delimitedFiles) {
            parsedFiles.put(file, reader.read(file));
        }

        ListWrapper<TimeSeries> chartData = new ArrayListWrapper<>();

        for (MapWrapper.Entry<String, MapWrapper<Integer, String>> file : parsedFiles.entrySet()) {

            try {
                TimeSeries series = new TimeSeries(file.getKey());
                chartData.add(series);

                MapWrapper<Integer, String> contents = file.getValue();
                String fileHeader = contents.get(HEADER_LINE);

                ListWrapper<String> delimitedHeaders
                        = new ArrayListWrapper(Arrays.asList(fileHeader.split(",")));

                int colIndex = delimitedHeaders.indexOf(header);
                contents.remove(HEADER_LINE);

                for (String fileData : contents.values()) {
                    ListWrapper<String> delimitedData
                            = new ArrayListWrapper(Arrays.asList(fileData.split(",")));

                    double value = Double.valueOf(delimitedData.get(colIndex));
                    String dateStr = delimitedData.get(X_INDEX);

                    DateFormat df = new SimpleDateFormat(defaultFileDateFormat, Locale.ENGLISH);
                    Date parsedDate = df.parse(dateStr);

                    if (parsedDate.compareTo(startDate) >= 0
                            && parsedDate.compareTo(endDate) <= 0) {
                        series.add(new Day(parsedDate), value);
                    } else {
                        continue;
                    }
                }

                //System.out.println(entry.getValue());
            } catch (ItemNotFoundException | ParseException ex) {
                throw new IOException(ex);
            }
        }

        TimeSeriesCollection dataset = new TimeSeriesCollection();
        for (TimeSeries series : chartData) {
            dataset.addSeries(series);
        }


        switch (visualization) {
            case BASIC:
                break;
            case MOVING_AVERAGE:
                break;
            case BINNED:
                break;
        }

        generateVisual(dataset);
        
    }


    private void generateVisual(TimeSeriesCollection dataset) {
        JFreeChart chart = ChartFactory.createTimeSeriesChart(
                "Legal & General Unit Trust Prices", // title
                "Date", // x-axis label
                "Price Per Unit", // y-axis label
                dataset, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );

        ChartFrame frame = new ChartFrame("Test", chart);
        frame.pack();
        frame.setVisible(true);

    }

    @Override
    public void setArgs(MapWrapper<String, Object> parsedArgs)
            throws IllegalArgumentException {
        files = (String) parsedArgs.get("files");
        header = (String) parsedArgs.get("header");
        showLinearTrend = (boolean) parsedArgs.get("lineartrend");
        visualization = (Visualizations) parsedArgs.get("type");

        DateFormat df = new SimpleDateFormat(defaultCliDateFormat, Locale.ENGLISH);
        try {
            startDate = df.parse((String) parsedArgs.get("startDate"));
            endDate = df.parse((String) parsedArgs.get("endDate"));
        } catch (ParseException ex) {
            throw new IllegalArgumentException(ex);
        }
    }

//<editor-fold defaultstate="collapsed" desc="Unused">
    @Override
    public boolean runsOnStart() {
        return false;
    }

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
