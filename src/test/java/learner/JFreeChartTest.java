package learner;

import main.Helper;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

// Learner tests to see how JFreeChart works on the surface.
public class JFreeChartTest {

    public JFreeChartTest() {

    }

    @Test
    @Disabled("I am a learner, and take too long to run.")
    public void makePieChartTest() {
        makeExamplePieChart();
    }
    
    @Test
    @Disabled
    public void jfreeChartBlockingTest() {
        System.out.println("Printing some text before making the chart.");
        makeExamplePieChart();
        System.out.println("The chart should still be visible after the call.");
        
        int mediumPauseInSeconds = 1;
        Helper.pause(mediumPauseInSeconds);
    }


    private void makeExamplePieChart() {
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("Category 1", 43.2);
        dataset.setValue("Category 2", 27.9);
        dataset.setValue("Category 3", 79.5);

        JFreeChart chart = ChartFactory.createPieChart(
                "Sample Pie Chart",
                dataset,
                true, // legend?
                true, // tooltips?
                false // URLs?
        );

        ChartFrame frame = new ChartFrame("Test", chart);
        frame.pack();
        frame.setVisible(true);
        
        int shortPauseInSeconds = 5;
        Helper.pause(shortPauseInSeconds);
    }
}
