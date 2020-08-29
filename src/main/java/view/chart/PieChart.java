package view.chart;

import view.chartdata.ChartDataWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.util.TableOrder;
import org.jfree.data.category.DefaultCategoryDataset;

public class PieChart extends AbstractBinnedChart {

    public PieChart(MapWrapper<String, Object> parsedArgs) {
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
