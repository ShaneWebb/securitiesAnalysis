package process;

import io.local.ExternalDataReader;
import java.io.IOException;
import javautilwrappers.*;
import process.datatypes.ParsedData;
import view.chart.ChartWrapper;
import view.chart.ChartWrapperFactory;
import view.chartdata.ChartDataWrapper;
import view.chartdata.ChartDataWrapperFactory;

public class Plotter implements SupportedProcess {

    private final ExternalDataReader reader;

    public Plotter(ExternalDataReader reader) {
        this.reader = reader;
    }

    @Override
    public void execute(MapWrapper<String, Object> parsedArgs) throws IOException, IllegalArgumentException {

        
        ChartWrapper chart = ChartWrapperFactory.createFrom(parsedArgs);
        ChartDataWrapper chartData = ChartDataWrapperFactory.createFrom(parsedArgs);
        
        if (parsedArgs.get("files") == null) {
            ParsedData data = reader.readDB(parsedArgs);
            chartData.convertChartData(data);
        } else {
            String files = (String) parsedArgs.get("files");
            ParsedData data = reader.readFiles(files);
            chartData.convertChartData(data);
        }
        
        chart.generateVisual(chartData);
    }
    
}
