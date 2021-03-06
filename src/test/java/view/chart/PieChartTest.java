package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.EnumMapWrapper;
import javautilwrappers.MapWrapper;
import main.Helper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import process.DisplayTypeBinned;
import process.Visualizations;
import view.chartdata.PieChartData;

public class PieChartTest {
    
    private final DefaultCategoryDataset dataset;
    private final MapWrapper<SupportedArgs, Object> map0;
    private final ChartWrapper chart;
    private final PieChartData data;
    
    public PieChartTest() {
        dataset = new DefaultCategoryDataset();
        dataset.addValue(20.0, "Row 1", "Column 1");
        dataset.addValue(50.0, "Row 1", "Column 2");
        dataset.addValue(20.0, "Row 1", "Column 3");
        dataset.addValue(2.0, "Row 2", "Column 1");
        dataset.addValue(3.0, "Row 2", "Column 2");
        dataset.addValue(2.0, "Row 2", "Column 3");

        map0 = new EnumMapWrapper<>(SupportedArgs.class);
        map0.put(SupportedArgs.files, "A.csv,B.csv");
        map0.put(SupportedArgs.header, "volume");
        map0.put(SupportedArgs.startDate, "8/21/1981");
        map0.put(SupportedArgs.endDate, "1/1/2020");
        map0.put(SupportedArgs.xAxis, "Date");
        map0.put(SupportedArgs.lineartrend, false);
        map0.put(SupportedArgs.stochastic, false);
        map0.put(SupportedArgs.type, Visualizations.BINNED);
        map0.put(SupportedArgs.displayType, DisplayTypeBinned.BAR);
        map0.put(SupportedArgs.bins, 10);

        chart = new PieChart(map0);
        data = new PieChartData(map0, dataset);
        
    }

    @Test
    @Disabled
    public void testGenerateVisual() {
        chart.generateVisual(data);
        final int pauseInSecs = 10;
        Helper.pause(pauseInSecs);
    }

}
