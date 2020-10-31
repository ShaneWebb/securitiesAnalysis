package process;

import datatypes.exceptions.ItemNotFoundException;
import io.local.ExternalDataReader;
import java.io.IOException;
import java.util.stream.Stream;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import process.datatypes.ParsedData;
import process.datatypes.ParsedDatabase;
import process.datatypes.ParsedFile;

public class PlotterTest {

    private AutoCloseable closeable;

    @Mock
    private ExternalDataReader reader;
    
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
            ParsedData csvData) throws Exception {

        basicExecute(csvData, cliArgs);
    }
    
    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecuteMAvg(MapWrapper<String, Object> cliArgs,
            ParsedData csvData) throws Exception {
        
        cliArgs.put("type", Visualizations.MOVING_AVERAGE);
        cliArgs.put("period", 1);
        cliArgs.put("initToIgnore", 1);
        
        basicExecute(csvData, cliArgs);
    }

    private void basicExecute(
            ParsedData csvData, 
            MapWrapper<String, Object> cliArgs) throws IOException {
        when(reader.readFiles("A.csv,B.csv")).thenReturn((ParsedFile) csvData);
        when(reader.readDB(cliArgs)).thenReturn(null);

        Plotter testPlotter = new Plotter(reader);
        try {
            testPlotter.execute(cliArgs);
            //Helper.pause(5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecuteExceptions(MapWrapper<String, Object> cliArgs,
            MapWrapper<String, MapWrapper<Integer, String>> csvData) throws Exception {

        when(reader.readFiles("A.csv,B.csv")).thenReturn((ParsedFile) csvData);

        Plotter testPlotter = new Plotter(reader);

        MapWrapper<String, Object> cliArgsInvalid = new HashMapWrapper(cliArgs);
        cliArgsInvalid.put("header", "IDoNotExist");

        IOException ioException = assertThrows(IOException.class,
                () -> {
                    testPlotter.execute(cliArgsInvalid);
                }
        );

        assertEquals(ItemNotFoundException.class, ioException.getCause().getClass());

    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testEmptySeries(MapWrapper<String, Object> cliArgs,
            MapWrapper<String, MapWrapper<Integer, String>> csvData) 
            throws Exception {

        cliArgs.put("startDate", "1/1/2100");
        cliArgs.put("endDate", "1/1/2100");

        when(reader.readFiles("A.csv,B.csv")).thenReturn((ParsedFile) csvData);

        Plotter testPlotter = new Plotter(reader);
        try {
            testPlotter.execute(cliArgs);
        } catch (Exception e) {
            fail(e);
        }

        //Helper.pause(5);
    }

    public static Stream<Arguments> provideExecuteArgs() {
        MapWrapper<String, Object> filecliArgs = new HashMapWrapper<>();
        filecliArgs.put("files", "A.csv,B.csv");
        filecliArgs.put("header", "volume");
        filecliArgs.put("startDate", "8/21/1981");
        filecliArgs.put("endDate", "1/1/2020");
        filecliArgs.put("lineartrend", true);
        filecliArgs.put("type", Visualizations.BASIC);
        
        MapWrapper<String, Object> dbCliArgs = new HashMapWrapper<>();
        dbCliArgs.put("type", Visualizations.BASIC);
        dbCliArgs.put("DB", "A,B");
        dbCliArgs.put("files", null);
        dbCliArgs.put("header", "volume");
        dbCliArgs.put("startDate", "8/21/1981");
        dbCliArgs.put("endDate", "1/1/2020");
        dbCliArgs.put("xAxis", "Date");
        dbCliArgs.put("lineartrend", true);
        dbCliArgs.put("stochastic", false);

        MapWrapper<String, MapWrapper<Integer, String>> csvData = new HashMapWrapper<>();
        MapWrapper<Integer, String> aCsvData = new HashMapWrapper<>();
        MapWrapper<Integer, String> bCsvData = new HashMapWrapper<>();
        csvData.put("A.csv", aCsvData);
        csvData.put("B.csv", bCsvData);
        
        aCsvData.put(1, "date,volume,open,close,high,low,adjclose");
        aCsvData.put(2, "2019-04-18,2874100,75.73000336,76.16999817,76.54000092,75.30999756,76.16999817");
        aCsvData.put(3, "2019-04-17,4472000,78.15000153,75.43000031,78.31999969,74.45999908,75.43000031");
        bCsvData.put(1, "date,volume,open,close,high,low,adjclose");
        bCsvData.put(2, "2019-04-18,146800,53.86000061,53.93999863,54.24000168,53.72999954,53.93999863");
        bCsvData.put(3, "2019-04-17,245600,54.27000046,53.95000076,54.54000092,53.20999908,53.95000076");

        ParsedDatabase dbData = new ParsedDatabase(null);
        
        return Stream.of(
                Arguments.of(filecliArgs, csvData),
                Arguments.of(dbCliArgs, dbData)
        );
    }
    
}
