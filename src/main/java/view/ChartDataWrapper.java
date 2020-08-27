
package view;

import java.io.IOException;
import javautilwrappers.MapWrapper;
import org.jfree.data.general.AbstractDataset;

public interface ChartDataWrapper {

    AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException;

    AbstractDataset unwrap();
}
