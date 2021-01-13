package process;

import io.console.ArgumentParserWrapper;
import io.console.SupportedArgs;
import java.util.stream.Stream;
import javautilwrappers.EnumMapWrapper;
import javautilwrappers.HashMapWrapper;
import javautilwrappers.MapWrapper;
import org.junit.jupiter.api.AfterEach;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.STRICT_STUBS)
public class ProgramManagerTest {

    private AutoCloseable closeable;
    private ProgramManager instance;

    @Mock
    private SupportedProcess processOne, processTwo;

    @Mock
    private SupportedProcess placeholder;

    @BeforeEach
    public void setUp() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void tearDown() throws Exception {
        closeable.close();
    }

    public ProgramManagerTest() {
    }

    @Test //Must return a condensed, holistic report.
    public void testGetFullReport() {
        instance = new ProgramManager();
    }

    @Test
    public void testGetProgramActiveStatus() {
        instance = new ProgramManager();

        Boolean programIsActive;
        ProgramManager.setProgramActiveStatus(false);
        programIsActive = ProgramManager.getProgramActiveStatus();
        assertFalse(programIsActive);

        ProgramManager.setProgramActiveStatus(true);
        programIsActive = ProgramManager.getProgramActiveStatus();
        assertTrue(programIsActive);

    }

    @ParameterizedTest
    @MethodSource("provideCommandAndProcesses")
    public void testRunUserInputCommand(String commandArg, String testMode) 
            throws Exception {

        ArgumentParserWrapper localArgParser = new ArgumentParserWrapper("Test");
        localArgParser.addSubparserHelp("Test sub command help");

        ArgumentParserWrapper commandOne = localArgParser.addSubparser("CommandOne");
        commandOne.setDefault("func", processOne);
        ArgumentParserWrapper commandTwo = localArgParser.addSubparser("CommandTwo");
        commandTwo.setDefault("func", processTwo);

        MapWrapper<String, SupportedProcess> supportedProcesses = new HashMapWrapper<>();
        supportedProcesses.put("placeholder1", processOne);
        supportedProcesses.put("placeholder2", processTwo);

        instance = new ProgramManager(supportedProcesses, localArgParser);
        instance.runUserInputCommand(commandArg);
        
        MapWrapper<SupportedArgs, Object> parsedArgsOne = 
                new EnumMapWrapper<>(SupportedArgs.class);
        parsedArgsOne.put(SupportedArgs.func, processOne);
        
        MapWrapper<SupportedArgs, Object> parsedArgsTwo = 
                new EnumMapWrapper<>(SupportedArgs.class);
        parsedArgsTwo.put(SupportedArgs.func, processTwo);

        switch (testMode) {
            case "one":
                verify(processOne).execute(parsedArgsOne);
                verify(processTwo, never()).execute(null);
                break;
            case "two":
                verify(processOne, never()).execute(null);
                verify(processTwo).execute(parsedArgsTwo);
                break;
            case "none":
                verify(processOne, never()).execute(null);
                verify(processTwo, never()).execute(null);
        }

    }

    private static Stream<Arguments> provideCommandAndProcesses() {
        return Stream.of(
                Arguments.of("CommandOne", "one"),
                Arguments.of("CommandTwo", "two"),
                Arguments.of("Purposely!@#$ invalid command", "none"),
                Arguments.of("", "none"),
                Arguments.of(" ", "none"),
                Arguments.of(null, "none")
        );
    }

    @ParameterizedTest
    @MethodSource("provideCommandAndMap")
    public void testInbuiltCommands(String command, MapWrapper<SupportedArgs, Object> map) 
            throws Exception {

        MapWrapper<String, SupportedProcess> supportedProcesses = new HashMapWrapper<>();
        supportedProcesses.put("plotter", placeholder);
        supportedProcesses.put("stopper", placeholder);

        instance = new ProgramManager(supportedProcesses);
        instance.runUserInputCommand(command);
        map.put(SupportedArgs.func, placeholder);
        verify(placeholder).execute(map);

    }

    public static Stream<Arguments> provideCommandAndMap() {

        MapWrapper<SupportedArgs, Object> map0 = new EnumMapWrapper<>(SupportedArgs.class);
        map0.put(SupportedArgs.files, "A.csv,B.csv");
        map0.put(SupportedArgs.header, "volume");
        map0.put(SupportedArgs.startDate, "8/21/1981");
        map0.put(SupportedArgs.endDate, "1/1/2020");
        map0.put(SupportedArgs.xAxis, "Date");
        map0.put(SupportedArgs.lineartrend, false);
        map0.put(SupportedArgs.stochastic, false);
        map0.put(SupportedArgs.DB, null);

        MapWrapper<SupportedArgs, Object> map1 = new EnumMapWrapper<>(map0);
        map1.put(SupportedArgs.type, Visualizations.BASIC);
        map1.put(SupportedArgs.xAxis, "Date");
        map1.put(SupportedArgs.lineartrend, true);

        MapWrapper<SupportedArgs, Object> map2 = new EnumMapWrapper<>(map0);
        map2.put(SupportedArgs.type, Visualizations.BASIC);
        map2.put(SupportedArgs.xAxis, "Time");
        map2.put(SupportedArgs.lineartrend, false);

        MapWrapper<SupportedArgs, Object> map3 = new EnumMapWrapper<>(map0);
        map3.put(SupportedArgs.type, Visualizations.MOVING_AVERAGE);
        map3.put(SupportedArgs.period, 1);
        map3.put(SupportedArgs.initToIgnore, 1);

        MapWrapper<SupportedArgs, Object> map4 = new EnumMapWrapper<>(map0);
        map4.put(SupportedArgs.type, Visualizations.MOVING_AVERAGE);
        map4.put(SupportedArgs.period, 20);
        map4.put(SupportedArgs.initToIgnore, 20);

        MapWrapper<SupportedArgs, Object> map5 = new EnumMapWrapper<>(map0);
        map5.put(SupportedArgs.type, Visualizations.BINNED);
        map5.put(SupportedArgs.displayType, DisplayTypeBinned.BAR);
        map5.put(SupportedArgs.bins, 10);

        MapWrapper<SupportedArgs, Object> map6 = new EnumMapWrapper<>(map0);
        map6.put(SupportedArgs.type, Visualizations.BINNED);
        map6.put(SupportedArgs.displayType, DisplayTypeBinned.PIE);
        map6.put(SupportedArgs.bins, 10);

        MapWrapper<SupportedArgs, Object> map7 = new EnumMapWrapper<>(map0);
        map7.put(SupportedArgs.type, Visualizations.BASIC);
        map7.put(SupportedArgs.stochastic, true);

        MapWrapper<SupportedArgs, Object> map8 = new EnumMapWrapper<>(SupportedArgs.class);
        map8.put(SupportedArgs.type, Visualizations.BASIC);
        map8.put(SupportedArgs.DB, "A,B");
        map8.put(SupportedArgs.files, null);
        map8.put(SupportedArgs.header, "volume");
        map8.put(SupportedArgs.startDate, "8/21/1981");
        map8.put(SupportedArgs.endDate, "1/1/2020");
        map8.put(SupportedArgs.xAxis, "Date");
        map8.put(SupportedArgs.lineartrend, true);
        map8.put(SupportedArgs.stochastic, false);

        return Stream.of(
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 --lineartrend Basic", map1),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 --xAxis Time Basic", map2),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 MovingAvg", map3),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 MovingAvg --period 20 --initToIgnore 20", map4),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 Bin BAR 10", map5),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 Bin PIE 10", map6),
                Arguments.of("Visualize --files A.csv,B.csv volume 8/21/1981 1/1/2020 --stochastic Basic", map7),
                Arguments.of("Visualize --DB A,B volume 8/21/1981 1/1/2020 --lineartrend Basic", map8)
        //Arguments.of("Import ./data", map7)
        //Arguments.of("Visualize --Portfolio 8/21/1981 1/1/2020", map7)
        //Arguments.of("Gather", map7)
        //Arguments.of("Audit", map7)
        //Arguments.of("Execute", map7)
        );
    }

    @Test
    public void mutuallyExclusiveArgumentTest() throws Exception {
        MapWrapper<String, SupportedProcess> supportedProcesses = new HashMapWrapper<>();
        supportedProcesses.put("plotter", placeholder);
        supportedProcesses.put("stopper", placeholder);

        instance = new ProgramManager(supportedProcesses);
        instance.runUserInputCommand("Visualize --DB A,B --files A.csv,B.csv");
        verify(placeholder, never()).execute(null);
    }

}
