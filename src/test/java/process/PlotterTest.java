package process;

import io.local.BasicFileReader;
import java.io.IOException;
import static java.util.Arrays.stream;
import java.util.stream.Stream;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.ItemNotFoundException;
import javautilwrappers.MapWrapper;
import main.Helper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import view.AbstractChartData;
import view.AbstractChart;

public class PlotterTest {

    private AutoCloseable closeable;

    @Mock
    private BasicFileReader reader;
    
    public PlotterTest() {
    }

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecute(MapWrapper<String, Object> cliArgs,
            MapWrapper<Integer, String> aCsvData,
            MapWrapper<Integer, String> bCsvData) throws Exception {

        basicExecute(aCsvData, bCsvData, cliArgs);
    }
    
    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecuteMAvg(MapWrapper<String, Object> cliArgs,
            MapWrapper<Integer, String> aCsvData,
            MapWrapper<Integer, String> bCsvData) throws Exception {
        
        cliArgs.put("type", Visualizations.MOVING_AVERAGE);
        cliArgs.put("period", 1);
        cliArgs.put("initToIgnore", 1);
        
        basicExecute(aCsvData, bCsvData, cliArgs);
    }

    private void basicExecute(
            MapWrapper<Integer, String> aCsvData, 
            MapWrapper<Integer, String> bCsvData, 
            MapWrapper<String, Object> cliArgs) throws IOException {
        when(reader.read("A.csv")).thenReturn(aCsvData);
        when(reader.read("B.csv")).thenReturn(bCsvData);

        Plotter testPlotter = new Plotter(reader);
        try {
            testPlotter.setArgs(cliArgs);
            testPlotter.execute();
            //Helper.pause(5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecuteExceptions(MapWrapper<String, Object> cliArgs,
            MapWrapper<Integer, String> aCsvData,
            MapWrapper<Integer, String> bCsvData) throws Exception {

        when(reader.read("A.csv")).thenReturn(aCsvData);
        when(reader.read("B.csv")).thenReturn(bCsvData);

        Plotter testPlotter = new Plotter(reader);

        MapWrapper<String, Object> cliArgsInvalid = new HashMapWrapper(cliArgs);
        cliArgsInvalid.put("header", "IDoNotExist");

        IOException ioException = assertThrows(IOException.class,
                () -> {
                    testPlotter.setArgs(cliArgsInvalid);
                    testPlotter.execute();
                }
        );

        assertEquals(ItemNotFoundException.class, ioException.getCause().getClass());

    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testEmptySeries(MapWrapper<String, Object> cliArgs,
            MapWrapper<Integer, String> aCsvData,
            MapWrapper<Integer, String> bCsvData) throws Exception {

        cliArgs.put("startDate", "1/1/2100");
        cliArgs.put("endDate", "1/1/2100");

        when(reader.read("A.csv")).thenReturn(aCsvData);
        when(reader.read("B.csv")).thenReturn(bCsvData);

        Plotter testPlotter = new Plotter(reader);
        try {
            testPlotter.setArgs(cliArgs);
            testPlotter.execute();
        } catch (Exception e) {
            fail(e);
        }

        //Helper.pause(5);
    }

    public static Stream<Arguments> provideExecuteArgs() {
        MapWrapper<String, Object> cliArgs = new HashMapWrapper<>();
        cliArgs.put("files", "A.csv,B.csv");
        cliArgs.put("header", "volume");
        cliArgs.put("startDate", "8/21/1981");
        cliArgs.put("endDate", "1/1/2020");
        cliArgs.put("lineartrend", true);
        cliArgs.put("type", Visualizations.BASIC);

        MapWrapper<Integer, String> aCsvData = new HashMapWrapper<>();
        MapWrapper<Integer, String> bCsvData = new HashMapWrapper<>();
        aCsvData.put(1, "date,volume,open,close,high,low,adjclose");
        aCsvData.put(2, "2019-04-18,2874100,75.73000336,76.16999817,76.54000092,75.30999756,76.16999817");
        aCsvData.put(3, "2019-04-17,4472000,78.15000153,75.43000031,78.31999969,74.45999908,75.43000031");
        bCsvData.put(1, "date,volume,open,close,high,low,adjclose");
        bCsvData.put(2, "2019-04-18,146800,53.86000061,53.93999863,54.24000168,53.72999954,53.93999863");
        bCsvData.put(3, "2019-04-17,245600,54.27000046,53.95000076,54.54000092,53.20999908,53.95000076");

        return Stream.of(
                Arguments.of(cliArgs, aCsvData, bCsvData)
        );
    }

}
