
package view;

import java.io.IOException;
import javautilwrappers.MapWrapper;

public interface ChartDataWrapper {

    AbstractChartData convertChartData(MapWrapper<String, MapWrapper<Integer, String>> parsedFiles) throws IOException, NumberFormatException;

}
