package process;

import io.console.SupportedArgs;
import io.datatypes.ParsedData;
import io.local.ExternalDataReader;
import java.io.IOException;
import javautilwrappers.*;
import view.chart.ChartWrapper;
import view.chart.ChartWrapperFactory;
import view.chartdata.ChartDataWrapper;
import view.chartdata.ChartDataWrapperFactory;

public class Plotter implements SupportedProcess {

    private final ExternalDataReaderFactory factory;

    public Plotter(ExternalDataReaderFactory factory) {
        this.factory = factory;
    }

    @Override
    public void execute(MapWrapper<SupportedArgs, Object> parsedArgs) 
            throws IOException, IllegalArgumentException {

        ChartWrapper chart = ChartWrapperFactory.createFrom(parsedArgs);
        ChartDataWrapper chartData = ChartDataWrapperFactory.createFrom(parsedArgs);
        
        ExternalDataReader reader = factory.createFrom(parsedArgs);
        ParsedData data = reader.read(parsedArgs);
        
        chartData.convert(data);
        chart.generateVisual(chartData);
    }

}
