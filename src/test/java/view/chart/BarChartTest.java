package view.chart;

import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import main.Helper;
import org.jfree.data.category.DefaultCategoryDataset;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import process.DisplayTypeBinned;
import process.Visualizations;
import view.chartdata.BarChartData;

public class BarChartTest {

    private final DefaultCategoryDataset dataset;
    private final MapWrapper<String, Object> map0;
    private final ChartWrapper chart;
    private final BarChartData data;

    public BarChartTest() {
        dataset = new DefaultCategoryDataset();
        dataset.addValue(20.0, "Row 1", "Column 1");
        dataset.addValue(50.0, "Row 1", "Column 2");
        dataset.addValue(20.0, "Row 1", "Column 3");
        dataset.addValue(2.0, "Row 2", "Column 1");
        dataset.addValue(3.0, "Row 2", "Column 2");
        dataset.addValue(2.0, "Row 2", "Column 3");

        map0 = new HashMapWrapper<>();
        map0.put("files", "A.csv,B.csv");
        map0.put("header", "volume");
        map0.put("startDate", "8/21/1981");
        map0.put("endDate", "1/1/2020");
        map0.put("xAxis", "Date");
        map0.put("lineartrend", false);
        map0.put("stochastic", false);
        map0.put("type", Visualizations.BINNED);
        map0.put("displayType", DisplayTypeBinned.BAR);
        map0.put("bins", 10);

        chart = new BarChart(map0);
        data = new BarChartData(map0, dataset);
    }

    @Test
    @Disabled("Takes too long")
    public void testGenerateVisual() {
        chart.generateVisual(data);
        Helper.pause(10);
    }

}
