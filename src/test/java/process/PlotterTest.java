package process;

import io.console.SupportedArgs;
import io.local.ExternalDataReader;
import java.io.IOException;
import java.util.stream.Stream;
import javautilwrappers.EnumMapWrapper;
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
import process.datatypes.ParsedFile;

public class PlotterTest {

    private AutoCloseable closeable;

    @Mock
    private ExternalDataReader reader;
    
    class TestExternalDataReaderFactory implements ExternalDataReaderFactory {

        @Override
        public ExternalDataReader createFrom(MapWrapper<SupportedArgs, Object> parsedArgs) {
            return reader;
        }
        
    }
    
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
    public void testExecute(MapWrapper<SupportedArgs, Object> cliArgs,
            ParsedData data) throws Exception {

        basicExecute(data, cliArgs);
    }
    
    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testExecuteMAvg(MapWrapper<SupportedArgs, Object> cliArgs,
            ParsedData data) throws Exception {
        
        cliArgs.put(SupportedArgs.type, Visualizations.MOVING_AVERAGE);
        cliArgs.put(SupportedArgs.period, 1);
        cliArgs.put(SupportedArgs.initToIgnore, 1);
        
        basicExecute(data, cliArgs);
    }

    private void basicExecute(
            ParsedData data, 
            MapWrapper<SupportedArgs, Object> cliArgs) throws IOException {
        when(reader.read(cliArgs)).thenReturn((ParsedFile) data);
        //when(reader.readDB(cliArgs)).thenReturn(null);

        Plotter testPlotter = new Plotter(new TestExternalDataReaderFactory());
        try {
            testPlotter.execute(cliArgs);
            //Helper.pause(5);
        } catch (Exception e) {
            fail(e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideExecuteArgs")
    public void testEmptySeries(MapWrapper<SupportedArgs, Object> cliArgs,
            ParsedData csvData) 
            throws Exception {

        cliArgs.put(SupportedArgs.startDate, "1/1/2100");
        cliArgs.put(SupportedArgs.endDate, "1/1/2100");

        when(reader.read(cliArgs)).thenReturn((ParsedFile) csvData);

        Plotter testPlotter = new Plotter(new TestExternalDataReaderFactory());
        try {
            testPlotter.execute(cliArgs);
        } catch (Exception e) {
            fail(e);
        }

        //Helper.pause(5);
    }

    public static Stream<Arguments> provideExecuteArgs() {
        MapWrapper<SupportedArgs, Object> filecliArgs = 
                new EnumMapWrapper<>(SupportedArgs.class);
        filecliArgs.put(SupportedArgs.files, "A.csv,B.csv");
        filecliArgs.put(SupportedArgs.header, "volume");
        filecliArgs.put(SupportedArgs.startDate, "8/21/1981");
        filecliArgs.put(SupportedArgs.endDate, "1/1/2020");
        filecliArgs.put(SupportedArgs.lineartrend, true);
        filecliArgs.put(SupportedArgs.type, Visualizations.BASIC);

        MapWrapper<String, MapWrapper<Integer, String>> csvData = new HashMapWrapper<>();
        MapWrapper<Integer, String> aCsvData = new HashMapWrapper<>();
        MapWrapper<Integer, String> bCsvData = new HashMapWrapper<>();
        csvData.put("A.csv", aCsvData);
        csvData.put("B.csv", bCsvData);
        
        aCsvData.put(1, "2019-04-18,2874100");
        aCsvData.put(2, "2019-04-17,4472000");
        bCsvData.put(1, "2019-04-18,146800");
        bCsvData.put(2, "2019-04-17,245600");

        ParsedFile fileData = new ParsedFile(csvData);
        
        return Stream.of(
                Arguments.of(filecliArgs, fileData)
        );
    }
    
}
