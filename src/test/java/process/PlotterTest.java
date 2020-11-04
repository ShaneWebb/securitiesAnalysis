package process;

import io.console.SupportedArgs;
import io.local.ExternalDataReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;
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

    private static final String FILE_DATE_FORMAT = "yyyy-MM-dd";
    private static final String CLI_DATE_FORMAT = "MM/dd/yyyy";

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

    public static Stream<Arguments> provideExecuteArgs() throws Exception {
        MapWrapper<SupportedArgs, Object> filecliArgs
                = new EnumMapWrapper<>(SupportedArgs.class);
        filecliArgs.put(SupportedArgs.files, "A.csv,B.csv");
        filecliArgs.put(SupportedArgs.header, "volume");
        filecliArgs.put(SupportedArgs.startDate, "8/21/1981");
        filecliArgs.put(SupportedArgs.endDate, "1/1/2020");
        filecliArgs.put(SupportedArgs.lineartrend, true);
        filecliArgs.put(SupportedArgs.type, Visualizations.BASIC);

        MapWrapper<String, MapWrapper<Integer, MapWrapper<String, Object>>> csvData = new HashMapWrapper<>();
        MapWrapper<Integer, MapWrapper<String, Object>> aCsvData = new HashMapWrapper<>();
        MapWrapper<Integer, MapWrapper<String, Object>> bCsvData = new HashMapWrapper<>();
        csvData.put("A.csv", aCsvData);
        csvData.put("B.csv", bCsvData);

        DateFormat dfFile = new SimpleDateFormat(FILE_DATE_FORMAT, Locale.ENGLISH);
        
        MapWrapper<String, Object> aLine1 = new HashMapWrapper<>();
        aLine1.put("date", dfFile.parse("2019-04-18"));
        aLine1.put("value", 2874100.0);
                
        MapWrapper<String, Object> aLine2 = new HashMapWrapper<>();
        aLine2.put("date", dfFile.parse("2019-04-17"));
        aLine2.put("value", 4472000.0);
                
        MapWrapper<String, Object> bLine1 = new HashMapWrapper<>();
        bLine1.put("date", dfFile.parse("2019-04-18"));
        bLine1.put("value", 146800.0);
                
        MapWrapper<String, Object> bLine2 = new HashMapWrapper<>();
        bLine2.put("date", dfFile.parse("2019-04-17"));
        bLine2.put("value", 245600.0);
        
        aCsvData.put(1, aLine1);
        aCsvData.put(2, aLine2);
        bCsvData.put(1, bLine1);
        bCsvData.put(2, bLine2);

        ParsedFile fileData = new ParsedFile(csvData);

        return Stream.of(
                Arguments.of(filecliArgs, fileData)
        );
    }

}
