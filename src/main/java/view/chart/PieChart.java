package view.chart;

import view.chartdata.ChartDataWrapper;
import javautilwrappers.MapWrapper;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

public class PieChart extends AbstractBinnedChart {

    public PieChart(MapWrapper<String, Object> parsedArgs) {
        super(parsedArgs);
    }

    @Override
    public void generateVisual(ChartDataWrapper dataset) {

        DefaultPieDataset data = (DefaultPieDataset) dataset.unwrap();
        JFreeChart chart = org.jfree.chart.ChartFactory.createPieChart(
                visualization.toString(),
                data, // data
                true, // create legend?
                true, // generate tooltips?
                false // generate URLs?
        );
        super.displayInFrame(chart);
    }

}
