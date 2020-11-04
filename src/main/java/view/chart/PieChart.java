package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;
import view.chartdata.ChartDataWrapper;

public class PieChart extends AbstractBinnedChart {

    public PieChart(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public void generateVisual(ChartDataWrapper dataset) {

        DefaultCategoryDataset data = (DefaultCategoryDataset) dataset.unwrap();
        JFreeChart chart = org.jfree.chart.ChartFactory.createMultiplePieChart(
                visualization.toString(),
                data, // data
                TableOrder.BY_COLUMN, // orientation
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        super.displayInFrame(chart);
    }

}
