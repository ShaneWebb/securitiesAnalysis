package view.chart;

import io.console.SupportedArgs;
import javautilwrappers.MapWrapper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import view.chartdata.ChartDataWrapper;

public class BarChart extends AbstractBinnedChart {

    public BarChart(MapWrapper<SupportedArgs, Object> parsedArgs) {
        super(parsedArgs);
    }
    
    @Override
    public void generateVisual(ChartDataWrapper dataset) {
        DefaultCategoryDataset data = (DefaultCategoryDataset) dataset.unwrap();
        JFreeChart chart = ChartFactory.createBarChart(
                visualization.toString(), // chart title
                "Bin", // domain axis label
                header, // range axis label
                data, // data
                PlotOrientation.VERTICAL, // orientation
                true, // include legend
                true, // tooltips?
                false // URLs?
        );
        super.displayInFrame(chart);
    }

}
